package com.productivity.myapplication.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
fun LoginScreen(navController: NavController,
                state: LogInState,
                events: (LogInEvent)-> Unit){

    val snackbarHostState  = remember { SnackbarHostState() }
    val coroutineScope : CoroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = state.doesUserExist, block = {
        when(state.doesUserExist){
            null -> {}
            true -> {
                navController.navigate(AppRoutes.DashboardScreen.route)
            }
            false -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("User does not exists")
                }
            }
        }
    } )


   Scaffold(
       snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
   ) {paddingValues ->
       Column(modifier = Modifier.padding(paddingValues)) {

           Column(
               Modifier
                   .fillMaxSize()
                   .padding(20.dp)){

               NormalTextComponent(value = stringResource(id = R.string.login))
               HeadingTextComponent(value = stringResource(id = R.string.welcome))
               Spacer(modifier = Modifier.height(60.dp))

               MyTextFieldComponent(
                   labelValue = stringResource(id = R.string.username),
                   painterResource(R.drawable.profile),
                   onValueChanged ={username -> events(LogInEvent.UsernameChanged(username))},
                   value = state.username, errorOrNot = state.usernameError
               )
               Spacer(modifier = Modifier.height(5.dp))
               MyTextFieldComponent(
                   labelValue = stringResource(id = R.string.email),
                   painterResource(R.drawable.email),
                   onValueChanged ={email -> events(LogInEvent.EmailChanged(email))},
                   value = state.email, errorOrNot = state.emailError
               )
               Spacer(modifier = Modifier.height(5.dp))
               PasswordTextFieldComponent(
                   labelValue = stringResource(id = R.string.password),
                   painterResource = painterResource(id = R.drawable.lock),
                   onPasswordChanged ={password -> events(LogInEvent.PasswordChanged(password))},
                   value = state.password, errorOrNot = state.usernameError
               )
               Spacer(modifier = Modifier.height(40.dp))

               ButtonComponent(value = stringResource(id = R.string.login)){
                   events(
                       LogInEvent.Login(
                           username = state.username,
                           password = state.password,
                           email = state.email
                       )
                   )


                   when(state.doesUserExist){
                       null -> {}
                       true -> {
                           navController.navigate(AppRoutes.DashboardScreen.route)
                       }
                       false -> {
                           coroutineScope.launch {
                               snackbarHostState.showSnackbar("User does not exists")
                           }
                       }
                   }


               }
               DividerTextComponent()

               ClickableLoginTextComponent(tryingToLogin = false) {
                   navController.navigate(route = AppRoutes.SignUpScreen.route)
               }
           }
       }

   }





}