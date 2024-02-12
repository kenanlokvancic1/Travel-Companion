package com.productivity.myapplication.screens.login

data class LogInState(
	val username: String = "",
	val password: String = "",
	val email: String = "",
	val doesUserExist: Boolean? = null,
	val doWeHaveTheUser: String = "",
	val usernameError : Boolean = false,
	val passwordError :  Boolean =false,
	val emailError : Boolean = false
)
