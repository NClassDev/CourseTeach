package com.example.loginclean.domain.model.usecase

import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.utilis.Constants.CLASESPORSEMANACURSO
import com.example.loginclean.utilis.Constants.CURSOS_REF
import com.example.loginclean.utilis.Constants.ERROR_MESSAGE
import com.example.loginclean.utilis.Constants.HORACURSO
import com.example.loginclean.utilis.Constants.HORARIOCURSO
import com.example.loginclean.utilis.Constants.LISTAALUMNOSCURSO
import com.example.loginclean.utilis.Constants.NAMECURSO
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

    fun putCursosFrom(curso: Cursos) = flow {
        try {
            emit(ResourceFirebase.Loading)
            cursoRef.collection(CURSOS_REF).document(curso.namecurso.toString()).set(
                hashMapOf(NAMECURSO to curso.namecurso ,
                    HORACURSO to curso.hora,
                    HORARIOCURSO to curso.horario,
                    LISTAALUMNOSCURSO to curso.listaalumnos,
                    CLASESPORSEMANACURSO to curso.clasesporsemana
                    )).await().also {
                        emit(ResourceFirebase.Success(it))
            }
        } catch (e: Exception) {
            emit(ResourceFirebase.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    private suspend fun getCursosFromCloudFirestore(): List<Cursos> {

        var cursos =  cursosCollRef.get().await().toObjects(Cursos::class.java)
        var tempCursos = arrayListOf<Cursos>()

         for( curso in cursos){
             if(curso.namecurso.equals("Ingles")){
                 tempCursos.add(curso)
             }
         }
        return tempCursos
    }

}