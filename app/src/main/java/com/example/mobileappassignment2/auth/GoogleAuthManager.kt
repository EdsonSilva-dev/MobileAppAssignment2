
package com.example.mobileappassignment2.auth

import android.app.Activity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.mobileappassignment2.R
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthManager(private val activity: Activity) {

    private val auth = FirebaseAuth.getInstance()

    private val credentialManager =
        CredentialManager.create(activity)

    fun currentUser() = auth.currentUser

    suspend fun signIn(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            // Create Google sign-in request
            val googleOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(
                    activity.getString(R.string.default_web_client_id)
                )
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleOption)
                .build()

            // Open Google account selection
            val result = credentialManager.getCredential(
                context = activity,
                request = request
            )

            val credential = result.credential

            if (
                credential is CustomCredential &&
                credential.type ==
                GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleCredential =
                    GoogleIdTokenCredential.createFrom(
                        credential.data
                    )

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(
                        googleCredential.idToken,
                        null
                    )

                // Authenticate with Firebase
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            onError(
                                task.exception?.localizedMessage
                                    ?: "Firebase login failed."
                            )
                        }
                    }

            } else {
                onError("Invalid Google credential.")
            }

        } catch (e: androidx.credentials.exceptions.GetCredentialCancellationException) {
            onError("Google sign-in cancelled.")

        } catch (e: Exception) {
            onError(e.localizedMessage ?: "Google sign-in failed.")
        }
    }

    suspend fun signOut() {
        auth.signOut()

        // Clear Credential Manager session
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }
}
