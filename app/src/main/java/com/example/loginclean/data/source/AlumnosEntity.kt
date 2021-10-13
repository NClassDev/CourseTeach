package com.example.loginclean.data.source

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alumnos_table")
data class AlumnosEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "alumnoId")
    val alumnoId: Int,

    @ColumnInfo(name = "nombre")
    val nombre: String

)