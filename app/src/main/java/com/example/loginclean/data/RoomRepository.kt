package com.example.loginclean.data

import com.example.loginclean.data.source.AlumnosDao
import com.example.loginclean.data.source.AlumnosEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val alumnosDao: AlumnosDao
) {

    suspend fun setAlumno(alumnosEntity: AlumnosEntity) = alumnosDao.setAlumno(alumnosEntity)

    fun getAllAlumnos(): Flow<List<AlumnosEntity>> = alumnosDao.getAllAlumnos()

}