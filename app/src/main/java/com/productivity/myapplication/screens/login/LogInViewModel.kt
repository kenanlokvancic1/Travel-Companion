package com.productivity.myapplication.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productivity.myapplication.database.UserQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val user: UserQueries): ViewModel(){
    private val _state: MutableStateFlow<LogInState> = MutableStateFlow(LogInState())
    val state = _state.asStateFlow()

    fun onEvent(e: LogInEvent){
        when(e){
            is LogInEvent.UsernameChanged -> {
                _state.update {
                    it.copy(username= e.v)
                }
            }

            is LogInEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = e.v)
                }
            }
            is LogInEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = e.v)
                }
            }

            is LogInEvent.Login ->{
                viewModelScope.launch(Dispatchers.IO) {
                    if(state.value.username.isEmpty()){
                        _state.update { it.copy(usernameError = true) }
                    }else{

                        _state.update { it.copy(usernameError = false) }
                    }

                    if(state.value.password.isEmpty()){
                        _state.update { it.copy(passwordError = true) }
                    }else{

                        _state.update { it.copy(passwordError = false) }
                    }

                    if(state.value.email.isEmpty()){
                        _state.update { it.copy(emailError = true) }
                    }else{

                        _state.update { it.copy(emailError = false) }
                    }

                    if(!state.value.usernameError &&
                        !state.value.passwordError &&
                        !state.value.emailError){

                        val doesUserExist = user.checkUserExists(uname = state.value.username, pass = state.value.password, mail = state.value.email)
                        _state.update {
                            it.copy(doesUserExist = doesUserExist, doWeHaveTheUser = if (doesUserExist)"we have it" else "we do not have it")
                        }
                    }

                }
            }

        }
    }


}