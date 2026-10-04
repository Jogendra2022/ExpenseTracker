package com.myexpense.expensetracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth =
        FirebaseAuth.getInstance()

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun clearError() {
        errorMessage = null
    }


    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (email.isBlank() || password.isBlank()) {

            errorMessage =
                "Please enter email and password."

            return
        }

        isLoading = true
        errorMessage = null

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->

            isLoading = false

            if (task.isSuccessful) {

                onSuccess()

            } else {

                errorMessage =
                    getFirebaseError(
                        task.exception?.message
                    )
            }
        }
    }


    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (email.isBlank()) {

            errorMessage =
                "Please enter your email."

            return
        }

        if (password.length < 6) {

            errorMessage =
                "Password must contain at least 6 characters."

            return
        }

        isLoading = true
        errorMessage = null

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->

            isLoading = false

            if (task.isSuccessful) {

                onSuccess()

            } else {

                errorMessage =
                    getFirebaseError(
                        task.exception?.message
                    )
            }
        }
    }


    fun resetPassword(
        email: String,
        onSuccess: () -> Unit
    ) {

        if (email.isBlank()) {

            errorMessage =
                "Please enter your email."

            return
        }

        isLoading = true
        errorMessage = null

        auth.sendPasswordResetEmail(
            email
        ).addOnCompleteListener { task ->

            isLoading = false

            if (task.isSuccessful) {

                onSuccess()

            } else {

                errorMessage =
                    getFirebaseError(
                        task.exception?.message
                    )
            }
        }
    }


    private fun getFirebaseError(
        message: String?
    ): String {

        val text =
            message ?: ""

        return when {

            text.contains(
                "INVALID_LOGIN_CREDENTIALS",
                ignoreCase = true
            ) ->
                "Invalid email or password."

            text.contains(
                "INVALID_EMAIL",
                ignoreCase = true
            ) ->
                "Please enter a valid email."

            text.contains(
                "EMAIL_EXISTS",
                ignoreCase = true
            ) ->
                "An account with this email already exists."

            text.contains(
                "WEAK_PASSWORD",
                ignoreCase = true
            ) ->
                "Password is too weak."

            text.contains(
                "network",
                ignoreCase = true
            ) ->
                "Please check your internet connection."

            else ->
                text.ifBlank {
                    "Something went wrong. Please try again."
                }
        }
    }
}