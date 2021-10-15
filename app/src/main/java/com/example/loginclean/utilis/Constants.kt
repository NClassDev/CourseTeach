package com.example.loginclean.utilis

import com.example.loginclean.data.source.Alumnos

object Constants {
    //References
    const val USERS_REF = "users"
    const val USER_REF = "user"

    const val CURSOS_REF = "cursos"
    const val PREF_NAME = "CursosProfesores"

    //Messages
    const val ERROR_MESSAGE = "Unexpected error!"

    //Intents
    const val AUTH_INTENT = "authIntent"
    const val REGISTER_INTENT = "registerIntent"
    const val MAIN_INTENT = "mainIntent"



    //User Fields
    const val NAME = "name"
    const val CURSOSPROFESOR = "cursosactivos"
    const val UID = "uid"
    const val EMAIL = "email"
    const val CREATED_AT = "createdAt"

    //Curso Fields
    const val NAMECURSO = "namecurso"
    const val IDCURSO = "idcurso"
    const val IDPROFESOR = "idprofesor"
    const val HORARIOCURSO = "horario"
    const val HORACURSO = "hora"
    const val CLASESPORSEMANACURSO = "clasesporsemana"
    const val LISTAALUMNOSCURSO = "listaalumnos"

}