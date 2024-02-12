package com.productivity.myapplication.screens.signup

data class SignUpState(val username:String = "", val password: String = "", val email: String = "", val doesUserExist: Boolean? = null, val anyUserExists:Boolean = false, val isUserLoggedIn:Boolean = false,
	val usernameError : Boolean = false,
	val passwordError: Boolean = false,
	val emailError : Boolean = false)

