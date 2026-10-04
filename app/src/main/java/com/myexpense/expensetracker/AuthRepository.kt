package com.myexpense.expensetracker

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun login(
        email: String,
        password: String,
        onResult: (Result<Unit>) -> Unit
    ) {

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                onResult(
                    Result.success(Unit)
                )

            } else {

                onResult(
                    Result.failure(
                        task.exception
                            ?: Exception("Login failed")
                    )
                )
            }
        }
    }

    fun register(
        email: String,
        password: String,
        onResult: (Result<Unit>) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                onResult(
                    Result.success(Unit)
                )

            } else {

                onResult(
                    Result.failure(
                        task.exception
                            ?: Exception("Registration failed")
                    )
                )
            }
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}