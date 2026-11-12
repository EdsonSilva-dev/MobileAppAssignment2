
package com.example.mobileappassignment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.mobileappassignment2.exceptions.WordLengthException
import com.example.mobileappassignment2.exceptions.VeryLongWordException

@Composable
fun Exceptions2Screen(onBack: () -> Unit) {

    var wordInput by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Exceptions 2",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Custom Exception - Word Length Validator")

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = wordInput,
            onValueChange = {
                wordInput = it
                resultMessage = ""
            },
            label = { Text("Enter a word") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                try {
                    if (wordInput.length > 10) {
                        throw VeryLongWordException(
                            "The word must not exceed 10 characters."
                        )
                    }

                    resultMessage = "Valid word: $wordInput"
                    isError = false

                } catch (e: WordLengthException) {
                    resultMessage = "Error: ${e.message}"
                    isError = true
                }
            }
        ) {
            Text("Validate Word")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = resultMessage,
            color = if (isError)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(onClick = onBack) {
            Text("Back to Menu")
        }
    }
}
