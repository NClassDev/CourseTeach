package com.example.loginclean.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlumnosDao {
    @Query("SELECT * FROM alumnos_table")
    fun getAllAlumnos(): Flow<List<AlumnosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAlumno(alumnosEntity: AlumnosEntity)

}