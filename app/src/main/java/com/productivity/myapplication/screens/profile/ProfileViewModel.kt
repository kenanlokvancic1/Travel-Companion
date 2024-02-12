package com.productivity.myapplication.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productivity.myapplication.database.User
import com.productivity.myapplication.database.UserQueries

import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val user: UserQueries): ViewModel() {
    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init{

        viewModelScope.launch(Dispatchers.IO)
        {

                user.getAllUsers().collect{changes->
                    when(changes){
                        is UpdatedResults -> {
                            if(changes.list.isNotEmpty()){

                                _state.update {
                                    it.copy(
                                        username = changes.list.first().username,
                                        password = changes.list.first().password,
                                        email = changes.list.first().email
                                    )
                                }
                            }

                        }
                        is InitialResults -> {


                            if(changes.list.isNotEmpty()){

                                _state.update {
                                    it.copy(
                                        username = changes.list.first().username,
                                        password = changes.list.first().password,
                                        email = changes.list.first().email
                                    )
                                }
                            }
                        }
                    }
                }

        }
    }

    fun onEvent(e: ProfileEvent){
        when(e){
            is ProfileEvent.EmailChanged -> {
                _state.update {
                    it.copy(newEmail= e.v)
                }
            }
            is ProfileEvent.PasswordChanged -> {
                _state.update {
                    it.copy(newPassword = e.v)
                }

            }
            is ProfileEvent.UsernameChanged -> {
                _state.update {
                    it.copy(newUsername = e.v)
                }
            }
            ProfileEvent.deleteUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if(state.value.newUsername.isEmpty()){
                        user.getAllUsers().collect{changes : ResultsChange<User>->
                            when(changes){
                                is InitialResults -> {
                                    if(changes.list.isNotEmpty()){
                                        user.deleteUser(changes.list.first()._id)
                                    }
                                }
                                is UpdatedResults -> {

                                    if(changes.list.isNotEmpty()){
                                        user.deleteUser(changes.list.first()._id)
                                    }
                                }
                            }
                        }
                    }else{
                    }
                }
            }
            ProfileEvent.updateUser -> {
                viewModelScope.launch(Dispatchers.IO) {

                    if(state.value.username.isEmpty()){
                        _state.update{it.copy(newUsernameError = true)}
                    }else{
                        _state.update{it.copy(newUsernameError =false)}
                    }

                    if(state.value.password.isEmpty()){
                        _state.update{it.copy(newPasswordError = true)}
                    }else{
                        _state.update{it.copy(newPasswordError =false)}
                    }

                    if(state.value.email.isEmpty()){
                        _state.update{it.copy(newEmailError = true)}
                    }else{
                        _state.update{it.copy(newEmailError =false)}
                    }
                    if(!state.value.newUsernameError &&
                        !state.value.newPasswordError &&
                        !state.value.newEmailError){

                        user.updateUserProfile(uname = state.value.username, mail = state.value.newEmail, pass = state.value.newPassword, newuname = state.value.newUsername)
                    }
                }
            }

            ProfileEvent.initializeUserData -> {

                viewModelScope.launch(Dispatchers.IO)
                {

                    user.getAllUsers().collect{changes->
                        when(changes){
                            is UpdatedResults -> {
                                if(changes.list.isNotEmpty()){

                                    _state.update {
                                        it.copy(
                                            username = changes.list.first().username,
                                            password = changes.list.first().password,
                                            email = changes.list.first().email
                                        )
                                    }
                                }

                            }
                            is InitialResults -> {


                                if(changes.list.isNotEmpty()){

                                    _state.update {
                                        it.copy(
                                            username = changes.list.first().username,
                                            password = changes.list.first().password,
                                            email = changes.list.first().email
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }

            ProfileEvent.confirmDelete -> {
                _state.update { it.copy(confirmDelete = !state.value.confirmDelete) }
            }

            ProfileEvent.showEditDialog -> {





                viewModelScope.launch(Dispatchers.IO)
                {

                    user.getAllUsers().collect{changes->
                        when(changes){
                            is UpdatedResults -> {
                                if(changes.list.isNotEmpty()){

                                    _state.update {
                                        it.copy(
                                            username = changes.list.first().username,
                                            password = changes.list.first().password,
                                            newUsername = changes.list.first().username,
                                            newEmail = changes.list.first().email,
                                            newPassword = changes.list.first().password,
                                            email = changes.list.first().email
                                        )
                                    }
                                }

                            }
                            is InitialResults -> {


                                if(changes.list.isNotEmpty()){

                                    _state.update {
                                        it.copy(
                                            username = changes.list.first().username,
                                            password = changes.list.first().password,
                                            newUsername = changes.list.first().username,
                                            newEmail = changes.list.first().email,
                                            newPassword = changes.list.first().password,
                                            email = changes.list.first().email
                                        )
                                    }
                                }
                            }
                        }
                    }

                }

                _state.update { it.copy(showTheDialogue = !state.value.showTheDialogue,
                   ) }
            }

            ProfileEvent.logOut -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if(state.value.newUsername.isEmpty()){
                        user.updateLogInStatus(uname = state.value.username, loginstat = false)
                    }else{
                        user.updateLogInStatus(uname = state.value.newUsername, loginstat = false)
                    }
                }
            }


            ProfileEvent.showGoToSignUpDialog -> {
                _state.update {
                    it.copy(
                        showGotoSignUpDialog = !state.value.showGotoSignUpDialog
                    )
                }
            }
        }
    }
}