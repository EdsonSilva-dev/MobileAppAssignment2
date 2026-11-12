
package com.example.mobileappassignment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Mobile Apps II",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Assignment 1")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onNavigate("state") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("State")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onNavigate("baas") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("BaaS - Firebase")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onNavigate("exceptions1") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Exceptions 1")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onNavigate("exceptions2") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Exceptions 2")
        }
    }
}
