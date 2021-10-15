package com.example.loginclean.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.loginclean.domain.model.usecase.ProfesorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProfesorViewModel @Inject constructor(
    private val repository: ProfesorRepository
): ViewModel() {

    fun getCursosActivos () = liveData(Dispatchers.IO){
        repository.getCursosActivos().collect {
            emit(it)
        }
    }

}