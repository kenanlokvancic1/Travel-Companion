package com.productivity.myapplication.screens.profile

sealed interface ProfileEvent {
    data class UsernameChanged(val v: String): ProfileEvent
    data class PasswordChanged(val v: String): ProfileEvent
    data class EmailChanged(val v: String): ProfileEvent
    object updateUser: ProfileEvent
    object deleteUser: ProfileEvent
    object initializeUserData: ProfileEvent
    object showEditDialog: ProfileEvent
    object confirmDelete: ProfileEvent
    object logOut: ProfileEvent
    object showGoToSignUpDialog : ProfileEvent
}