
package com.example.mobileappassignment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StateScreen(onBack: () -> Unit) {

    // This counter does not survive configuration changes
    var normalCounter by remember {
        mutableIntStateOf(0)
    }

    // This counter survives configuration changes
    var savedCounter by rememberSaveable {
        mutableIntStateOf(0)
    }

    // State Hoisting: passing state and events to a child
    CounterContent(
        normalCounter = normalCounter,
        savedCounter = savedCounter,
        onIncrement = {
            normalCounter++
            savedCounter++
        },
        onBack = onBack
    )
}

@Composable
fun CounterContent(
    normalCounter: Int,
    savedCounter: Int,
    onIncrement: () -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "State Demonstration",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Normal Counter (remember)")

        Text(
            text = normalCounter.toString(),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Saved Counter (rememberSaveable)")

        Text(
            text = savedCounter.toString(),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onIncrement) {
            Text("Increment Both Counters")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Rotate the device to test state changes."
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(onClick = onBack) {
            Text("Back to Menu")
        }
    }
}
