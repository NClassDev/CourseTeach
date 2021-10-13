package com.example.loginclean.di

import com.example.loginclean.utilis.Constants.CURSOS_REF
import com.example.loginclean.utilis.Constants.USERS_REF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Named(USERS_REF)
    fun provideUsersRef(rootRef: FirebaseFirestore): CollectionReference {
        return rootRef.collection(USERS_REF)
    }

    @Provides
    @Named(CURSOS_REF)
    fun provideCursosRef(rootRef: FirebaseFirestore): CollectionReference {
        return rootRef.collection(CURSOS_REF)
    }

}