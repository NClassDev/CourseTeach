package com.example.loginclean.domain.model.usecase

import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.utilis.Constants
import com.example.loginclean.utilis.Constants.CREATED_AT
import com.example.loginclean.utilis.Constants.EMAIL
import com.example.loginclean.utilis.Constants.NAME
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val user: FirebaseFirestore,
) {

    suspend fun signUpWithEmailAndPassword(email: String, pass: String) = flow {
        try {
            emit(ResourceFirebase.Loading)
            auth.createUserWithEmailAndPassword(email, pass).await().also {
                emit(ResourceFirebase.Success(it))
            }
        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    suspend fun createUserInFirestore(email: String, name: String) = flow {
        try {
            emit(ResourceFirebase.Loading)
            user.collection("user").document(email).set(
                hashMapOf(NAME to name, EMAIL to email, CREATED_AT to FieldValue.serverTimestamp())
            ).await().also {
                emit(ResourceFirebase.Success(it))
            }

        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }
}