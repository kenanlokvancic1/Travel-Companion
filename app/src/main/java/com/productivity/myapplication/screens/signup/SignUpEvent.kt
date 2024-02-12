package com.productivity.myapplication.screens.signup

sealed interface SignUpEvent{
    data class UsernameChanged(val v: String): SignUpEvent
    data class PasswordChanged(val v: String): SignUpEvent
    data class EmailChanged(val v: String): SignUpEvent
    object insertUser: SignUpEvent
    data class Signup(val username: String, val password: String, val email: String): SignUpEvent
    object checkIfAnyUserLoggedIn: SignUpEvent
    object checkIfAnyUserExists: SignUpEvent

}