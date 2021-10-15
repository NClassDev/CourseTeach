package com.example.loginclean.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels

import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.databinding.ActivityLoginBinding
import com.example.loginclean.presentation.LoginViewModel
import com.example.loginclean.utilis.Constants.MAIN_INTENT
import com.example.loginclean.utilis.Constants.REGISTER_INTENT
import com.example.loginclean.utilis.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Named(MAIN_INTENT)
    @Inject lateinit var mainIntent: Intent

    @Named(REGISTER_INTENT)
    @Inject lateinit var registerIntent: Intent

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
//            goToHomeActivity()

            signIn()
        }

        binding.loginCreateNewAccountTxtv.setOnClickListener {
            goToRegisterActivity()
        }

        isCheckUser()

    }


    private fun signIn() {
        val email = binding.loginEmailEdtxt.text.toString().trim()
        val password = binding.loginPasswordEdtxt.text.toString().trim()


        if(email.isEmpty() || password.isEmpty() ){
            Toast.makeText(this, "Errror al iniciar sesion", Toast.LENGTH_SHORT).show()
        }else{
            signInWithEmailAndPassword(email, password)
        }

    }

    private fun signInWithEmailAndPassword(email: String, password: String){
        viewModel.signInWithEmailAndPassword(email, password).observe(this,{
            response ->
            when(response){
                is ResourceFirebase.Loading -> {
                    binding.loginProgressBar.visibility = View.VISIBLE
                }
                is ResourceFirebase.Success -> {
                    goToHomeActivity()
                    binding.loginProgressBar.visibility = View.GONE
                }
                is ResourceFirebase.Failure ->{
                    showToast(response.errorMessage)
                    binding.loginProgressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun isCheckUser(){
        if(viewModel.getStoredTag().isNotEmpty()){
            goToHomeActivity()
        }
    }

    private fun goToHomeActivity() {
        startActivity(mainIntent)
        finish()
    }

    private fun goToRegisterActivity() {
        startActivity(registerIntent)
        finish()
    }

}