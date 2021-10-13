package com.example.loginclean.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginclean.adapters.AlumnosAdapter
import com.example.loginclean.adapters.AlumnosRoomAdapter
import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.data.source.Alumnos
import com.example.loginclean.data.source.AlumnosEntity
import com.example.loginclean.data.source.Cursos
import com.example.loginclean.databinding.FragmentAddCourseBinding
import com.example.loginclean.presentation.AddCourseViewModel
import com.example.loginclean.utilis.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddCourseFragment : Fragment(), AlumnosAdapter.OnAlumnoClickListener, AlumnosRoomAdapter.OnAlumnoClickListener {

    private var _binding: FragmentAddCourseBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddCourseViewModel>()
    private lateinit var alumnosAdapter: AlumnosAdapter
    private lateinit var alumnosRoomAdapter: AlumnosRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alumnosAdapter = AlumnosAdapter(requireContext(), this)
        alumnosRoomAdapter = AlumnosRoomAdapter(requireContext(), this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclearView()
        setupObservers()

        binding.btnAddAlumno.setOnClickListener {
            putCurso()
        }


    }

    private fun setupRecyclearView() {
        binding.rcyclviewAlumnos.adapter = alumnosAdapter
        binding.rcyclviewAlumnosRoom.adapter = alumnosRoomAdapter

        binding.rcyclviewAlumnos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rcyclviewAlumnosRoom.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupObservers() {
        getAlumnos()
        getAlumnosFromRoom()
    }

    private fun getDataByForm(): Cursos {

        var tempCurso = Cursos(binding.textViewMsgNombreClase.text.toString(),
            binding.textViewMsgHorarios.text.toString(),
            binding.textViewMsgHora.text.toString(),
            binding.textViewMsgClasesPorSemana.text.toString(),
        )

        return tempCurso
    }


    private fun putCurso() {
        viewModel.putCurso(getDataByForm()).observe(viewLifecycleOwner, { response ->

            when (response) {
                is ResourceFirebase.Loading -> {
                    binding.progressBarAddcourse.visibility = View.VISIBLE
                }

                is ResourceFirebase.Success -> {
                    binding.progressBarAddcourse.visibility = View.GONE
                    Toast.makeText(requireContext(), "Curso Success ", Toast.LENGTH_LONG).show()
                }

                is ResourceFirebase.Failure -> {
                    binding.progressBarAddcourse.visibility = View.GONE
                    Toast.makeText(requireContext(), "Fallo al guardar datos", Toast.LENGTH_LONG)
                        .show()
                }

            }
        })
    }

    private fun getAlumnos() {
        viewModel.getAlumnos().observe(viewLifecycleOwner, { response ->

            when(response){
                is ResourceFirebase.Loading -> {
                    binding.progressBarAddcourse.visibility = View.VISIBLE
                }

                is ResourceFirebase.Success -> {
                    binding.progressBarAddcourse.visibility = View.GONE
                    alumnosAdapter.setAlumnosList(response.data)

                }

                is ResourceFirebase.Failure -> {
                    binding.progressBarAddcourse.visibility = View.GONE
                    Toast.makeText(requireContext(), "Fallo al guardar datos", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }


    private fun getAlumnosFromRoom(){
        viewModel.alumnosMediatorLiveData.observe(viewLifecycleOwner){alumnosList ->
            alumnosList?.let {
                alumnosRoomAdapter.setAlumnosList(it)

                if(it.isNotEmpty()){
                    binding.progressBarAddcourse.visibility = View.GONE
                }

            }
        }
    }



    override fun onAlumnoClick(alumno: Alumnos, position: Int) {
        showToast("${alumno.name}")

        viewModel.setAlumno(alumno)

    }

    override fun onAlumnoClick(alumno: AlumnosEntity, position: Int) {
        TODO("Not yet implemented")
    }


}