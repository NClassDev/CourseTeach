package com.example.loginclean.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.loginclean.domain.model.usecase.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel(){

    fun createUserWithGoogleAuth() = liveData(Dispatchers.IO) {
        authRepository.createUserInFirestore().collect { response->
            emit(response)
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) = liveData(Dispatchers.IO) {
        authRepository.signInWithEmailAndPassword(email, password).collect {
            response->
            emit(response)
        }
    }
}