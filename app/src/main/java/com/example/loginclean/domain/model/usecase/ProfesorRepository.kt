package com.example.loginclean.domain.model.usecase

import android.util.Log
import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.data.source.Alumnos
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.data.source.User
import com.example.loginclean.utilis.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ProfesorRepository @Inject constructor(
    @Named(Constants.USER_REF) private val usersRef: CollectionReference,
    @Named(Constants.CURSOS_REF) private val cursosRef: CollectionReference,

    private val auth: FirebaseAuth,

    ) {

    fun getCursosActivos() = flow {
        try {
            emit(ResourceFirebase.Loading)

            emit(ResourceFirebase.Success(getCursosActivosFromCloudF()))
            getCursosActivosFromCloudF()?.let {
            }

        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    fun getAlumnosInscritos(idCurso:String) = flow {
        try {
            emit(ResourceFirebase.Loading)
            emit(ResourceFirebase.Success(getAlumnosInscritosFromCloudF(idCurso)))

        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }


    private suspend fun getCursosActivosFromCloudF():List<Cursos>? {
        return usersRef.document(getUserID()).collection(Constants.CURSOSPROFESOR).get().await().toObjects(Cursos::class.java)
    }

    private suspend fun getAlumnosInscritosFromCloudF(cursoId: String):List<Alumnos>? {
        return cursosRef.document(cursoId).collection(Constants.ALUMNOSREQUEST).get().await().toObjects(Alumnos::class.java)
    }


    private fun getUserID(): String {
        return auth.uid!!
    }



}