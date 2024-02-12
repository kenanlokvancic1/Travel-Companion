package com.productivity.myapplication.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.productivity.myapplication.R
import com.productivity.myapplication.components.ButtonComponent
import com.productivity.myapplication.components.ClickableLoginTextComponent
import com.productivity.myapplication.components.DividerTextComponent
import com.productivity.myapplication.components.HeadingTextComponent
import com.productivity.myapplication.components.MyTextFieldComponent
import com.productivity.myapplication.components.NormalTextComponent
import com.productivity.myapplication.components.PasswordTextFieldComponent
import com.productivity.myapplication.navigation.AppRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    navController: NavController,
    state: SignUpState,
    events: (SignUpEvent)-> Unit
) {
        val snackbarHostState  = remember {SnackbarHostState()}
    val coroutineScope : CoroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = state.doesUserExist, block = {
        when(state.doesUserExist){
            null -> {}
            true -> {

                coroutineScope.launch {
                    snackbarHostState.showSnackbar("User already exists")
                }
            }
            false -> {

                events(SignUpEvent.insertUser)
                navController.navigate(AppRoutes.DashboardScreen.route)
            }
        }
    })

Scaffold (
    snackbarHost = { androidx.compose.material3.SnackbarHost(hostState = snackbarHostState) }
){paddingValues->
Column(
    modifier = Modifier.padding(paddingValues)
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        NormalTextComponent(value = stringResource(id = R.string.hello))
        HeadingTextComponent(value = stringResource(id = R.string.create_account))
        Spacer(modifier = Modifier.height(20.dp))
        MyTextFieldComponent(labelValue = if(state.usernameError) "Username can not be empty" else stringResource(id = R.string.username),
            painterResource(id = R.drawable.profile),
            onValueChanged = { username -> events(SignUpEvent.UsernameChanged(username)) },
            value = state.username, errorOrNot = state.usernameError)
        MyTextFieldComponent(labelValue = if(state.emailError) " Email can not be empty" else stringResource(id = R.string.email),
            painterResource(id = R.drawable.email),
            onValueChanged = { email -> events(SignUpEvent.EmailChanged(email)) },
            value = state.email, errorOrNot = state.emailError
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordTextFieldComponent(labelValue = if(state.passwordError) "Password can not be empty" else stringResource(id = R.string.password),
            painterResource(id = R.drawable.lock),
            onPasswordChanged = { password -> events(SignUpEvent.PasswordChanged(password)) },
            value = state.password, errorOrNot = state.passwordError
        )
        Spacer(modifier = Modifier.height(70.dp))
        ButtonComponent(value = stringResource(id = R.string.register)) {
                events(
                    SignUpEvent.Signup(
                        username = state.username,
                        password = state.password,
                        email = state.email
                    )
                )

            when(state.doesUserExist){
                null -> {}
                true -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("User Already Exists")
                    }
                }
                false -> {
                    events(SignUpEvent.insertUser)
                    navController.navigate(AppRoutes.DashboardScreen.route)
                }
            }


        }


        Spacer(modifier = Modifier.height(40.dp))
        DividerTextComponent()
        ClickableLoginTextComponent(tryingToLogin = true) {
            // Navigate to the registration screen using the navController
            navController.navigate(AppRoutes.LogInScreen.route)
        }

    }
}

}

}