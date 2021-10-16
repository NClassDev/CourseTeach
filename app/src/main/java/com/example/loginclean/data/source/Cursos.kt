package com.example.loginclean.data.source

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Cursos (
    var namecurso: String? = null,
    var horario:String? = null,
    var hora:String? = null,
    var clasesporsemana:String? = null,
    var idcurso: String? = null,
    var idprofesor: String? = null,

): Parcelable, Serializable