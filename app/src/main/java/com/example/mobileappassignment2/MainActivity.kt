
package com.example.mobileappassignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize

import com.example.mobileappassignment2.screens.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var currentScreen by rememberSaveable {
                mutableStateOf("home")
            }

            // Handle the Android Back button
            BackHandler(enabled = currentScreen != "home") {
                currentScreen = "home"
            }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (currentScreen) {

                        "home" -> HomeScreen(
                            onNavigate = { screen ->
                                currentScreen = screen
                            }
                        )

                        "state" -> StateScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "baas" -> BaaSScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "exceptions1" -> Exceptions1Screen(
                            onBack = { currentScreen = "home" }
                        )

                        "exceptions2" -> Exceptions2Screen(
                            onBack = { currentScreen = "home" }
                        )

                        else -> HomeScreen(
                            onNavigate = { screen ->
                                currentScreen = screen
                            }
                        )
                    }
                }
            }
        }
    }
}
