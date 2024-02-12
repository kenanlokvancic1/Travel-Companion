package com.productivity.myapplication.screens.profile

data class ProfileState(val username:String = "", val password: String = "", val email: String = "", val confirmDelete: Boolean = false, val showTheDialogue:Boolean = false,
                        val newUsername:String = "", val newPassword: String = "", val newEmail: String = "",
                        val newUsernameError : Boolean = false,
                        val newPasswordError : Boolean = false,
                        val newEmailError : Boolean =false,
                        val showGotoSignUpDialog : Boolean= false)