package com.example.loginclean.utilis

import com.example.loginclean.data.source.Alumnos
import com.example.loginclean.data.source.AlumnosEntity

object DataMaper {


    fun changeAlumnoFirebaseToAlumnoRoom(
        input: Alumnos
    ): AlumnosEntity{
        return AlumnosEntity(
            input.hashCode(),
            input.name ?: ""
        )
    }

//    fun mapListAlumnosFromFirebase(
//        input: List<Alumnos>
//    ): List<AlumnosEntity>{
//        return input.map {
//            AlumnosEntity(it.name ?: "")
//        }
//    }
}