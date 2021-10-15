package com.example.loginclean.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.loginclean.domain.model.usecase.AuthRepository
import com.example.loginclean.utilis.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val authRepository: AuthRepository,
) : ViewModel() {


    fun createUserWithGoogleAuth() = liveData(Dispatchers.IO) {
        authRepository.createUserInFirestore().collect { response ->
            emit(response)
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) = liveData(Dispatchers.IO) {
        authRepository.signInWithEmailAndPassword(email, password).collect { response ->
            emit(response)
            setStoredTag(email)
        }
    }

    fun getStoredTag(): String{
        return sharedPreferences.getString(Constants.PREF_NAME,"")!!
    }

    fun setStoredTag(query: String){
        sharedPreferences.edit().putString(Constants.PREF_NAME, query ).apply()
    }

    fun eraseStoredTag(){
        sharedPreferences.edit().clear().apply()
    }


}