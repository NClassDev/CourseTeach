package com.example.loginclean.data.source

import java.io.Serializable

data class Alumnos(var name: String? = null,
                   var email: String? = null,
                   var isregistred: String? = null

) : Serializable {}