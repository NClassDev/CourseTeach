package com.example.loginclean.data.source

import java.util.*

data class User (
    var name: String? = null,
    var email: String? = null,
    var createdAt: Date? = null,
    var cursos: List<Cursos>? = null
)
