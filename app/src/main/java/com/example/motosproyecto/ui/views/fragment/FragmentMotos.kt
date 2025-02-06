package com.example.motosproyecto.ui.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motosproyecto.ui.views.MainActivity
import com.example.motosproyecto.controller.Controller
import com.example.motosproyecto.databinding.FragmentMotosBinding
import com.example.motosproyecto.ui.adapter.AdapterMoto
import com.example.motosproyecto.ui.views.dialogues.CreateDialogue
import com.example.proyectopmdm.data.models.MutableListMotos


class FragmentMotos : Fragment() {

    lateinit var binding: FragmentMotosBinding
   // lateinit var controller: Controller
    lateinit var activitycontext: MainActivity

  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activitycontext = requireActivity() as MainActivity
        binding = FragmentMotosBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerMotos.layoutManager = LinearLayoutManager(context)

        val adapter = AdapterMoto()

        binding.recyclerMotos.adapter = adapter

        activitycontext.motosViewModel.motosLiveData.observe(
            requireActivity(), { motos ->
                binding.recyclerMotos.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        )

        binding.imageButtonAnnadirItemRecycler.setOnClickListener {
            addMoto()
        }
    }

    private fun addMoto() {
        val dialog = CreateDialogue(
            {
                    moto ->
                activitycontext.motosViewModel.addMotos(moto)
                binding.recyclerMotos.layoutManager!!.scrollToPosition(MutableListMotos.motos.lastIndex)
            })
        dialog.show(activitycontext.supportFragmentManager, "Añadir")
    }


}