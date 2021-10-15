package com.example.loginclean.presentation

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.loginclean.domain.model.usecase.AuthRepository
import com.example.loginclean.domain.model.usecase.RegisterRepository
import com.example.loginclean.utilis.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel(){

    fun signUpWithEmailAndPassword(email: String, password: String) = liveData(Dispatchers.IO) {
        registerRepository.signUpWithEmailAndPassword(email, password).collect {
            response->
            emit(response)
        }
    }

    fun createNewUser(email: String, name: String, uid: String) = liveData(Dispatchers.IO){
        registerRepository.createUserInFirestore(email, name, uid).collect {
            response->
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