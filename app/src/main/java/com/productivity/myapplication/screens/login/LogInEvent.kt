package com.productivity.myapplication.screens.login

sealed interface LogInEvent{
    data class UsernameChanged(val v: String): LogInEvent
    data class PasswordChanged(val v: String): LogInEvent
    data class EmailChanged(val v: String): LogInEvent
    data class Login(val username: String, val password: String, val email: String): LogInEvent

}