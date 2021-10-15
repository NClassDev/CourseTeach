package com.example.loginclean.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.databinding.ActivityRegisterBinding
import com.example.loginclean.presentation.RegisterViewModel
import com.example.loginclean.utilis.Constants.MAIN_INTENT
import com.example.loginclean.utilis.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    @Named(MAIN_INTENT)
    @Inject
    lateinit var mainIntent: Intent

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        val email = binding.signupEmailEdtxt.text.toString().trim()
        val password = binding.signupPassword1Edtxt.text.toString().trim()
        val password2 = binding.signupPassword2Edtxt.text.toString().trim()
        val name = binding.signupNameEdtxt.text.toString().trim()


        if (password == password2) {
            if (email.isNotEmpty() || name.isNotEmpty() || password.isNotEmpty()) {
                viewModel.signUpWithEmailAndPassword(email, password).observe(this, { response ->
                    when (response) {
                        is ResourceFirebase.Loading -> {
                            binding.signupProgressBar.visibility = View.VISIBLE
                        }

                        is ResourceFirebase.Success -> {
                            binding.signupProgressBar.visibility = View.GONE
                            createNewUser(email, name, response.data.user!!.uid)
                        }

                        is ResourceFirebase.Failure -> {
                            binding.signupProgressBar.visibility = View.GONE
                            Toast.makeText(this, "Falló el registro", Toast.LENGTH_SHORT).show()
                        }

                    }
                })
            }
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNewUser(email: String, name: String, uid: String) {
        viewModel.createNewUser(email, name, uid).observe(this, { response ->
            when (response) {
                is ResourceFirebase.Loading -> {
                    binding.signupProgressBar.visibility = View.VISIBLE
                }

                is ResourceFirebase.Success -> {
                    binding.signupProgressBar.visibility = View.GONE
                    goToHomeActivity()
                }

                is ResourceFirebase.Failure -> {
                    binding.signupProgressBar.visibility = View.GONE
                    showToast(response.errorMessage)
//                    Toast.makeText(this, "Falló al crear usuario", Toast.LENGTH_SHORT).show()
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


}