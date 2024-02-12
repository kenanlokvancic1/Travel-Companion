package com.productivity.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.productivity.myapplication.ui.theme.TravelCompanionTheme
import com.productivity.myapplication.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApp{
				AppNavigation()
			}

		}
	}
}

@Composable
fun MyApp(content: @Composable () -> Unit){
	TravelCompanionTheme {
		content()
	}

}