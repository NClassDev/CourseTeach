package com.example.loginclean.domain.model.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.data.RoomRepository
import com.example.loginclean.data.source.Alumnos
import com.example.loginclean.data.source.AlumnosEntity
import com.example.loginclean.utilis.Constants
import com.example.loginclean.utilis.Constants.USERS_REF
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

@Singleton
class AlumnosRepository @Inject constructor(
    @Named(USERS_REF) private val alumnosCollRef: CollectionReference,
    private val roomRepository: RoomRepository
    ) {

    fun getAlumnosList()  = flow{
        try {
            emit(ResourceFirebase.Loading)
            emit(ResourceFirebase.Success(getAlumnosFromCloudFirestore()))

        }catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }


    fun getAlumnosFromRoom() = flow {
        try {
            var listAlumnos = roomRepository.getAllAlumnos()
            emit(ResourceFirebase.Loading)
            emit(ResourceFirebase.Success(listAlumnos))

        }catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    private suspend fun getAlumnosFromCloudFirestore(): List<Alumnos>{
        return alumnosCollRef.orderBy("name",).get().await().toObjects(Alumnos::class.java)
    }

    suspend fun putAlumnosIntoRoom(listAlumnos: List<Alumnos>) {
        for(alumno in listAlumnos){
            var alumnoTemp = AlumnosEntity(alumno.hashCode(), alumno.name.toString())
            roomRepository.setAlumno(alumnoTemp)
        }
    }

}
