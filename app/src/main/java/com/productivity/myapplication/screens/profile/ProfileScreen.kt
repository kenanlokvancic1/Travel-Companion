package com.productivity.myapplication.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.productivity.myapplication.R
import com.productivity.myapplication.components.ButtonComponent
import com.productivity.myapplication.components.MyTextFieldComponent
import com.productivity.myapplication.components.PasswordTextFieldComponent
import com.productivity.myapplication.navigation.AppRoutes
import com.productivity.myapplication.screens.signup.SignUpEvent
import com.productivity.myapplication.screens.travelplan.TravelPlanEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    state:ProfileState,
    events: (ProfileEvent)-> Unit


) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp)
                .padding(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White, // Card background color
                contentColor = Color.Black  // Card content color, e.g. text
            ),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top, // Align to the top
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if(state.showTheDialogue){
                    Dialog(onDismissRequest = {
                        events(
                            ProfileEvent.showEditDialog
                        )
                    }) {
                        Card() {
                            Column(modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                MyTextFieldComponent(labelValue = stringResource(id = R.string.username),
                                    painterResource(id = R.drawable.profile),
                                    onValueChanged = { username -> events(ProfileEvent.UsernameChanged(username)) },
                                    value = state.newUsername, errorOrNot = state.newUsernameError)
                                MyTextFieldComponent(labelValue = stringResource(id = R.string.email),
                                    painterResource(id = R.drawable.email),
                                    onValueChanged = { email -> events(ProfileEvent.EmailChanged(email)) },
                                    value = state.newEmail, errorOrNot = state.newEmailError
                                )
                                PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password),
                                    painterResource(id = R.drawable.lock),
                                    onPasswordChanged = { password -> events(ProfileEvent.PasswordChanged(password)) },
                                    value = state.newPassword, errorOrNot = state.newPasswordError
                                )

                                Button(onClick = {
                                    events(ProfileEvent.updateUser)
                                    events(ProfileEvent.showEditDialog)
                                }) {
                                    androidx.compose.material.Text(text = "Save")

                                }
                            }
                        }

                    }
                }

                if(state.confirmDelete){
                    Dialog(onDismissRequest = {
                        events(
                            ProfileEvent.confirmDelete
                        )
                    }) {
                        Card(){
                            Column(verticalArrangement = Arrangement.spacedBy(30.dp), modifier = Modifier.padding(10.dp)) {
                                Text(text = "Permanently Delete Your Account?", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)){

                                    OutlinedButton(onClick = {
                                        events(
                                            ProfileEvent.deleteUser
                                        )
                                        navController.navigate(AppRoutes.SignUpScreen.route)
                                    }) {
                                        Text(text = "Yes")
                                    }
                                    Spacer(modifier = Modifier.width(70.dp))
                                    OutlinedButton(onClick = {
                                        events(
                                            ProfileEvent.confirmDelete
                                        )
                                    }) {
                                        Text(text = "No")
                                    }


                                }
                            }
                        }

                    }
                }


                CreateImageProfile()
                Divider(
                    modifier = Modifier.padding(4.dp),
                    thickness = 2.dp,
                    color = Color.LightGray,
                )

                CreateInfo()
                Spacer(modifier = Modifier.height(40.dp))

                Column(modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ButtonComponent(value = "Edit Profile") {
                        events(
                            ProfileEvent.showEditDialog
                        )
                    }
                    ButtonComponent(value = "Delete Profile") {
                        events(
                            ProfileEvent.confirmDelete
                        )
                    }
                    ButtonComponent(value = "Log Out") {
                        navController.navigate(AppRoutes.LogInScreen.route)
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateInfo() {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Edit Settings",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(3.dp),
            style = TextStyle(fontSize = 18.sp)
        )



    }
}

@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        shadowElevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile image",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )
    }
}