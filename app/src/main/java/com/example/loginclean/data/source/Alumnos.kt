package com.example.loginclean.data.source

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Alumnos(var idalumno: String? = null,
                   var email: String? = null,
                   var name: String? = null,
                   var state: String? = null,
                   var isregistred: String? = null

) : Parcelable, Serializable