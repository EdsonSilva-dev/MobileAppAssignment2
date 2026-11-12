
package com.example.mobileappassignment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import com.google.firebase.auth.FirebaseAuth

@Composable
fun BaaSScreen(onBack: () -> Unit) {

    // Firebase Authentication instance
    val auth = remember {
        FirebaseAuth.getInstance()
    }

    // Form fields
    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var isRegistering by rememberSaveable {
        mutableStateOf(false)
    }

    var message by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    // Current Firebase user
    var user by remember {
        mutableStateOf(auth.currentUser)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Firebase Authentication",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (user == null) {

            Text(
                text = if (isRegistering)
                    "Create an Account"
                else
                    "Login"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Register or Login button
            Button(
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    val enteredEmail = email.trim()

                    if (enteredEmail.isEmpty() || password.isEmpty()) {
                        message = "Please enter email and password."

                    } else {

                        isLoading = true
                        message = ""

                        if (isRegistering) {

                            // CREATE ACCOUNT
                            auth.createUserWithEmailAndPassword(
                                enteredEmail,
                                password
                            ).addOnCompleteListener { task ->

                                isLoading = false

                                if (task.isSuccessful) {
                                    user = auth.currentUser
                                    password = ""
                                    message = "Account created successfully!"
                                } else {
                                    message = task.exception?.localizedMessage
                                        ?: "Registration failed."
                                }
                            }

                        } else {

                            // LOGIN
                            auth.signInWithEmailAndPassword(
                                enteredEmail,
                                password
                            ).addOnCompleteListener { task ->

                                isLoading = false

                                if (task.isSuccessful) {
                                    user = auth.currentUser
                                    password = ""
                                    message = "Login successful!"
                                } else {
                                    message = task.exception?.localizedMessage
                                        ?: "Login failed."
                                }
                            }
                        }
                    }
                }
            ) {
                Text(
                    if (isRegistering)
                        "Register"
                    else
                        "Login"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Switch between Login and Register
            TextButton(
                enabled = !isLoading,
                onClick = {
                    isRegistering = !isRegistering
                    message = ""
                    password = ""
                }
            ) {
                Text(
                    if (isRegistering)
                        "Already have an account? Login"
                    else
                        "Don't have an account? Register"
                )
            }

        } else {

            // User is authenticated
            Text("You are logged in!")

            Spacer(modifier = Modifier.height(16.dp))

            Text("Email: ${user?.email}")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    auth.signOut()
                    user = auth.currentUser
                    password = ""
                    message = "Logged out successfully."
                }
            ) {
                Text("Logout")
            }
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(message)
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = onBack
        ) {
            Text("Back to Menu")
        }
    }
}
