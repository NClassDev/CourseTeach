package com.example.loginclean.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.loginclean.R
import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.databinding.FragmentProfesorPerfilBinding
import com.example.loginclean.presentation.ProfesorViewModel
import com.example.loginclean.utilis.showToast


class ProfesorPerfilFragment : Fragment() {

    private var _binding: FragmentProfesorPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<ProfesorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfesorPerfilBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCursosActivos()
    }

    private fun getCursosActivos() {
        viewModel.getCursosActivos().observe(viewLifecycleOwner, {
            response ->
            when(response){
                is ResourceFirebase.Loading -> {

                }

                is ResourceFirebase.Success -> {
                    showToast("Cargando Datos")
                }
            }
        })

    }


}