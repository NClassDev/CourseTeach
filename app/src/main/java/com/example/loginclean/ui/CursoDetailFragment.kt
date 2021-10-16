package com.example.loginclean.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.loginclean.data.ResourceFirebase
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginclean.adapters.AlumnosAdapter
import com.example.loginclean.adapters.AlumnosRoomAdapter
import com.example.loginclean.data.source.Alumnos
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.databinding.FragmentCursoDetailBinding
import com.example.loginclean.presentation.CursosViewModel
import com.example.loginclean.presentation.ProfesorViewModel
import com.example.loginclean.utilis.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_curso_detail.*

@AndroidEntryPoint
class CursoDetailFragment : Fragment(), AlumnosAdapter.OnAlumnoClickListener {


    private var _binding: FragmentCursoDetailBinding? = null
    private val binding get () = _binding!!

    private val viewModel by activityViewModels<CursosViewModel>()
    private val viewModelProfesor by activityViewModels<ProfesorViewModel>()

    private lateinit var alumnosAdapter: AlumnosAdapter


    private lateinit var curso: Cursos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            curso = it.getParcelable("curso")!!
        }

        alumnosAdapter = AlumnosAdapter(requireContext(), this)

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

        setupRecyclearView()
        setupObservers()

        binding.tvNameCurso.text = curso.namecurso
        binding.tvHorarioCurso.text = curso.horario
        binding.tvHoraCurso.text = curso.hora
        binding.tvClasesporsemanaCurso.text = curso.clasesporsemana

        binding.btnInscribirse.setOnClickListener {
            sendUserRequestCurso()
        }

    }

    private fun setupObservers() {
        checkIsProfesor()
    }

    private fun checkIsProfesor() {
        viewModel.checkIsCursoProfesor(curso.idcurso.toString()).observe(viewLifecycleOwner,{
            response ->
            when(response){
                is ResourceFirebase.Loading -> {
                    progres_bar_cursodetail.visibility = View.VISIBLE
                }

                is ResourceFirebase.Success -> {

                    progres_bar_cursodetail.visibility = View.GONE

                    response.data?.let {

                        if(response.data){
                            showToast("Es Profesor del curso")
                            getAlumnosInscritos()
                            hideLayoutsForProfesor()
                        }else{
                            showToast("No  es Profesor del curso")
                            hideLayoutsForAlumno()
                        }

                    }
                }

                is ResourceFirebase.Failure -> {
                    Toast.makeText(requireContext(), "Fallo al guardar datos", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun hideLayoutsForAlumno() {
        binding.btnInscribirse.visibility = View.VISIBLE
        binding.msgAlumnos.visibility = View.GONE
    }

    private fun hideLayoutsForProfesor() {
        binding.btnInscribirse.visibility = View.GONE
        binding.msgAlumnos.visibility = View.VISIBLE
        binding.rcyclviewAlumnosInscritos.visibility = View.VISIBLE
    }

    private fun getAlumnosInscritos() {
        viewModelProfesor.getAlumnosInscritos(curso.idcurso.toString()).observe(viewLifecycleOwner, {
            response ->
            when(response){
                is ResourceFirebase.Loading -> {

                }

                is ResourceFirebase.Success -> {
                    response.data?.let {
                        alumnosAdapter.setAlumnosList(it)
                    }
                }

                is ResourceFirebase.Failure -> {
                    Toast.makeText(requireContext(), "Fallo al guardar datos", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }



    private fun setupRecyclearView(){
        binding.rcyclviewAlumnosInscritos.adapter = alumnosAdapter
        binding.rcyclviewAlumnosInscritos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

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

    override fun onAlumnoClick(alumno: Alumnos, position: Int) {

    }


}