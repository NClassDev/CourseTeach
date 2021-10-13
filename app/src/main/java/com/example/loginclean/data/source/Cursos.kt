package com.example.loginclean.data.source

import java.io.Serializable

data class Cursos (
    var namecurso: String? = null,
    var horario:String? = null,
    var hora:String? = null,
    var clasesporsemana:String? = null,
    var listaalumnos: List<Alumnos>? = null

):Serializable{

}