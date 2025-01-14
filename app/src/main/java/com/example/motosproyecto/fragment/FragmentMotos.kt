package com.example.motosproyecto.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motosproyecto.MainActivity
import com.example.motosproyecto.R
import com.example.motosproyecto.controller.Controller
import com.example.motosproyecto.databinding.FragmentMotosBinding


class FragmentMotos : Fragment() {

    lateinit var binding: FragmentMotosBinding
    lateinit var controller: Controller
    lateinit var activitycontext: MainActivity

  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activitycontext = requireActivity() as MainActivity
        binding = FragmentMotosBinding.inflate(inflater, container, false)
        controller = Controller(activitycontext, this)
        initRecyclerView()

      binding.imageButtonAnnadirItemRecycler.setOnClickListener {
          controller.addMoto()
      }
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerMotos.layoutManager = LinearLayoutManager(context)
        controller.setAdapter()
    }


}