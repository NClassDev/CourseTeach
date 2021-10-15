package com.example.loginclean.domain.model.usecase

import android.app.Application
import android.content.Context
import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.utilis.Constants.CREATED_AT
import com.example.loginclean.utilis.Constants.EMAIL
import com.example.loginclean.utilis.Constants.ERROR_MESSAGE
import com.example.loginclean.utilis.Constants.NAME
import com.example.loginclean.utilis.Constants.USERS_REF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    @Named(USERS_REF) private val usersRef: CollectionReference,

) {

    suspend fun signInWithEmailAndPassword(email:String, password: String) = flow {
        try {
            emit(ResourceFirebase.Loading)
            auth.signInWithEmailAndPassword(email, password).await().also {
                emit(ResourceFirebase.Success(it))
            }
        }catch (e:Exception){
            emit(ResourceFirebase.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    suspend fun createUserInFirestore() = flow{
        try {
            emit(ResourceFirebase.Loading)
            auth.currentUser?.apply {
                usersRef.document(uid).set(mapOf(
                    NAME to displayName,
                    EMAIL to email,
                    CREATED_AT to FieldValue.serverTimestamp()
                )).await().also {
                    emit(ResourceFirebase.Success(it))
                }
            }

        }catch (e: Exception){
            emit(ResourceFirebase.Failure(e.message ?: ERROR_MESSAGE))
        }
    }
}