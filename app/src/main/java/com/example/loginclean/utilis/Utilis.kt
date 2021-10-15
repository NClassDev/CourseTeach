package com.example.loginclean.utilis

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}

fun randomIDCurso(): String = List(4) {
    (('A'..'G') + ('0'..'9')).random()
}.joinToString("")