package com.example.loginclean.ui

import CursosAdapter
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginclean.data.ResourceFirebase
import com.example.loginclean.databinding.FragmentHomeBinding
import com.example.loginclean.presentation.CursosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CursosViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclearView()
        setupObservers()

        binding.floatingBtnAddCourse.setOnClickListener {
         val action = HomeFragmentDirections.actionHomeFragmentToAddCourseFragment()
            findNavController().navigate(action)
        }

    }


    private fun setupRecyclearView() {
        binding.rcyclviewCursos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setupObservers() {
        getCursos()
    }

    private fun getCursos() {
        viewModel.getCursosFrom().observe(viewLifecycleOwner, { response ->

            when (response) {
                is ResourceFirebase.Loading -> binding.progressBar.visibility = View.VISIBLE
                is ResourceFirebase.Success -> {

//                    val data = listOf(Cursos("Ingles", "Sabado"), Cursos("Ruso", "Sabado"))
//                    Log.d("Home: ", response.data[0].horario)
                    binding.rcyclviewCursos.adapter = CursosAdapter(requireContext(), response.data)

                    Toast.makeText(requireContext(), "Success ${response.data.size}", Toast.LENGTH_LONG).show()

                    binding.progressBar.visibility = View.GONE
                }
                is ResourceFirebase.Failure -> {
                    Toast.makeText(requireContext(), "Fallo al obtener datos", Toast.LENGTH_LONG).show()
//                    binding.progressBar.visibility  = View.GONE
                }
            }

        })
    }

    private fun navigateToAddCourse(fragment: Int){
        findNavController().navigate(fragment)
    }

}