package com.productivity.myapplication.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productivity.myapplication.database.UserQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val user: UserQueries): ViewModel() {
    private val _state: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun onEvent(e: SignUpEvent){
        when(e){
            is SignUpEvent.UsernameChanged -> {
                _state.update {
                    it.copy(username= e.v)
                }
            }

            SignUpEvent.insertUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    user.addUser(uname = state.value.username, pass = state.value.password, mail = state.value.email)
                }
            }

            is SignUpEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = e.v)
                }
            }
            is SignUpEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = e.v)
                }
            }

            is SignUpEvent.Signup ->{
                viewModelScope.launch(Dispatchers.IO) {
                    if(state.value.username.isEmpty()){
                        _state.update{it.copy(usernameError = true)}
                    }else{
                        _state.update{it.copy(usernameError =false)}
                    }

                    if(state.value.password.isEmpty()){
                        _state.update{it.copy(passwordError = true)}
                    }else{
                        _state.update{it.copy(passwordError =false)}
                    }

                    if(state.value.email.isEmpty()){
                        _state.update{it.copy(emailError = true)}
                    }else{
                        _state.update{it.copy(emailError =false)}
                    }
                    if(!state.value.usernameError &&
                       !state.value.passwordError &&
                        !state.value.emailError){

                        val doesUserExist = user.checkUserExists(uname = state.value.username, pass = state.value.password, mail = state.value.email)
                        _state.update {
                            it.copy(doesUserExist = doesUserExist)
                        }
                    }

                }
            }

            SignUpEvent.checkIfAnyUserLoggedIn -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(isUserLoggedIn = user.getLoggedInUser().isNotEmpty())
                    }
                }
            }

            SignUpEvent.checkIfAnyUserExists -> {
                viewModelScope.launch(Dispatchers.IO) {
                    user.getAllUsers().collect{changes->
                        when(changes){
                            is InitialResults -> {
                                _state.update {
                                    it.copy(anyUserExists = changes.list.isNotEmpty())
                                }
                            }
                            is UpdatedResults -> {
                                _state.update {
                                    it.copy(anyUserExists = changes.list.isNotEmpty())
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}