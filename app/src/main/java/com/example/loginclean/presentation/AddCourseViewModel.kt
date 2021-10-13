package com.example.loginclean.presentation

import androidx.lifecycle.*
import com.example.loginclean.data.Resource
import com.example.loginclean.data.RoomRepository
import com.example.loginclean.data.source.Alumnos
import com.example.loginclean.data.source.AlumnosEntity
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.domain.model.usecase.AlumnosRepository
import com.example.loginclean.domain.model.usecase.CursosRepository
import com.example.loginclean.utilis.DataMaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCourseViewModel @Inject constructor(
    private val repository : AlumnosRepository,
    private val repositoryCursos: CursosRepository,
    private val roomRepository: RoomRepository
): ViewModel() {

    private var currentSource: LiveData<List<AlumnosEntity>> =
        roomRepository.getAllAlumnos().asLiveData()

    val alumnosMediatorLiveData = MediatorLiveData<List<AlumnosEntity>>()

    init {
        alumnosMediatorLiveData.addSource(currentSource){
            alumnosMediatorLiveData.value = it
        }
    }




    fun getAlumnos() = liveData(Dispatchers.IO){
        repository.getAlumnosList().collect { response ->
            emit(response)
        }
    }



//    fun syncAlumnosFromFirebase() = liveData(Dispatchers.IO){
//        repository.getAlumnosList().collect {
//            response ->
//
//            emit(response)
//        }
//    }

    fun getAlumnosFromRoom() = liveData(Dispatchers.IO) {
        repository.getAlumnosFromRoom().collect {
            response -> emit(response)
        }
    }

    fun putCurso(curso: Cursos) = liveData(Dispatchers.IO){
        repositoryCursos.putCursosFrom(curso).collect{
            respose->
            emit(respose)
        }
    }



    fun setAlumno(alumno: Alumnos){
        viewModelScope.launch {
            roomRepository.setAlumno(DataMaper.changeAlumnoFirebaseToAlumnoRoom(alumno))
        }
    }



}
