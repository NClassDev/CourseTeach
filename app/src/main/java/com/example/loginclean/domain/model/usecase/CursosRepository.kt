package com.example.loginclean.domain.model.usecase

import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.utilis.Constants.CLASESPORSEMANACURSO
import com.example.loginclean.utilis.Constants.CURSOSPROFESOR
import com.example.loginclean.utilis.Constants.CURSOS_REF
import com.example.loginclean.utilis.Constants.ERROR_MESSAGE
import com.example.loginclean.utilis.Constants.HORACURSO
import com.example.loginclean.utilis.Constants.HORARIOCURSO
import com.example.loginclean.utilis.Constants.IDCURSO
import com.example.loginclean.utilis.Constants.IDPROFESOR
import com.example.loginclean.utilis.Constants.LISTAALUMNOSCURSO
import com.example.loginclean.utilis.Constants.NAMECURSO
import com.example.loginclean.utilis.Constants.USERS_REF
import com.example.loginclean.utilis.Constants.USER_REF
import com.example.loginclean.utilis.randomIDCurso
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
class CursosRepository @Inject constructor(
    @Named(CURSOS_REF) private val cursosCollRef: CollectionReference,
    private val user: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val cursoRef: FirebaseFirestore
) {

    fun getCursosFrom() = flow {
        try {
            emit(ResourceFirebase.Loading)
            emit(ResourceFirebase.Success(getCursosFromCloudFirestore()))
        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: ERROR_MESSAGE))
        }
    }


    private suspend fun getCursosFromCloudFirestore(): List<Cursos> {

        var cursos = cursosCollRef.get().await().toObjects(Cursos::class.java)
        var tempCursos = arrayListOf<Cursos>()

        for (curso in cursos) {
            tempCursos.add(curso)
        }
        return tempCursos
    }


    fun setUserRequestCurso(idCurso: String) = flow {
        try {
            emit(ResourceFirebase.Loading)
            cursoRef.collection(CURSOS_REF).document(idCurso).collection("alumnosrequest")
                .document(getUserID())
                .set(
                    hashMapOf(
                        "alumno" to getUserID(),
                        "state" to "waiting"
                    )
                ).await().also {
                    emit(ResourceFirebase.Success(it))
                }


        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    fun putCursosFrom(curso: Cursos) = flow {
        try {
            val randomIDCurso = randomIDCurso()
            emit(ResourceFirebase.Loading)
            cursoRef.collection(CURSOS_REF).document(randomIDCurso).set(
                hashMapOf(
                    IDCURSO to randomIDCurso,
                    IDPROFESOR to getUserID(),
                    NAMECURSO to curso.namecurso,
                    HORACURSO to curso.hora,
                    HORARIOCURSO to curso.horario,
                    CLASESPORSEMANACURSO to curso.clasesporsemana
                )
            ).await().also { it ->
                emit(ResourceFirebase.Success(it))

                user.collection(USER_REF).document(getUserID()).collection(CURSOSPROFESOR)
                    .document(randomIDCurso).set(
                        hashMapOf(IDCURSO to randomIDCurso, NAMECURSO to curso.namecurso)
                    ).await().also { response ->
                        emit(ResourceFirebase.Success(response))
                    }


            }
        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: ERROR_MESSAGE))
        }
    }


    private fun getUserID(): String {
        return auth.uid!!
    }

}