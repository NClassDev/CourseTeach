package com.example.loginclean.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.loginclean.domain.model.usecase.AuthRepository
import com.example.loginclean.domain.model.usecase.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel(){

    fun signUpWithEmailAndPassword(email: String, password: String) = liveData(Dispatchers.IO) {
        registerRepository.signUpWithEmailAndPassword(email, password).collect {
            response->
            emit(response)
        }
    }

    fun createNewUser(email: String, name: String) = liveData(Dispatchers.IO){
        registerRepository.createUserInFirestore(email, name).collect {
            response->
            emit(response)
        }
    }



}