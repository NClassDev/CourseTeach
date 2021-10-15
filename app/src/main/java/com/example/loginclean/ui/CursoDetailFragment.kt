package com.example.loginclean.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginclean.data.ResourceFirebase
import androidx.fragment.app.activityViewModels
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.databinding.FragmentCursoDetailBinding
import com.example.loginclean.presentation.CursosViewModel
import com.example.loginclean.utilis.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_curso_detail.*

@AndroidEntryPoint
class CursoDetailFragment : Fragment() {


    private var _binding: FragmentCursoDetailBinding? = null
    private val binding get () = _binding!!

    private val viewModel by activityViewModels<CursosViewModel>()

    private lateinit var curso: Cursos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            curso = it.getParcelable("curso")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCursoDetailBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNameCurso.text = curso.namecurso
        binding.tvHorarioCurso.text = curso.hora
        binding.tvHoraCurso.text = curso.horario
        binding.tvClasesporsemanaCurso.text = curso.clasesporsemana

        binding.btnInscribirse.setOnClickListener {
            sendUserRequestCurso()

        }

    }

    private fun sendUserRequestCurso() {
       viewModel.sendRequestUserCurso(curso.idcurso!!).observe(viewLifecycleOwner, {
           response ->
           when (response){
               is ResourceFirebase.Loading -> {

               }

               is ResourceFirebase.Success -> {
                   showToast("Petición a Curso enviada")
               }
           }
       })
    }


}