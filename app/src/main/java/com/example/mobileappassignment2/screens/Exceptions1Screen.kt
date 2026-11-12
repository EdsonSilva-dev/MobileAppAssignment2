
package com.example.mobileappassignment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun Exceptions1Screen(onBack: () -> Unit) {

    // Light state
    var isLightOn by remember { mutableStateOf(false) }
    var lightMessage by remember { mutableStateOf("") }

    // Number input
    var numberInput by remember { mutableStateOf("") }
    var numberMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Exceptions 1",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // LIGHT VALIDATION

        Text("Light Status")

        Text(
            text = if (isLightOn) "Light is ON" else "Light is OFF"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                isLightOn = !isLightOn
                lightMessage = ""
            }
        ) {
            Text("Turn Light On / Off")
        }

        Button(
            onClick = {
                try {
                    check(isLightOn) {
                        "The light is not turned on!"
                    }

                    lightMessage = "Light is ON. Validation passed."

                } catch (e: IllegalStateException) {
                    lightMessage = "Error: ${e.message}"
                }
            }
        ) {
            Text("Check Light")
        }

        Text(lightMessage)

        Spacer(modifier = Modifier.height(32.dp))

        // NUMBER VALIDATION

        Text("Number Validation")

        OutlinedTextField(
            value = numberInput,
            onValueChange = { numberInput = it },
            label = { Text("Enter a number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                try {
                    val number = numberInput.toInt()

                    require(number >= 0) {
                        "Number cannot be negative!"
                    }

                    numberMessage = "Valid number: $number"

                } catch (e: NumberFormatException) {
                    numberMessage = "Error: Enter a valid integer."

                } catch (e: IllegalArgumentException) {
                    numberMessage = "Error: ${e.message}"
                }
            }
        ) {
            Text("Validate Number")
        }

        Text(numberMessage)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(onClick = onBack) {
            Text("Back to Menu")
        }
    }
}
