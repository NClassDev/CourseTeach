package com.example.loginclean.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.loginclean.domain.model.usecase.CursosRepository
import com.squareup.okhttp.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class CursosViewModel @Inject constructor(
    private val repository: CursosRepository
) : ViewModel() {
    fun getCursosFrom() = liveData(Dispatchers.IO) {
        repository.getCursosFrom().collect { response ->
            emit(response)
        }
    }
}
