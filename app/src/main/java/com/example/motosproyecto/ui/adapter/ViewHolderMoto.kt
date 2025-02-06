package com.example.motosproyecto.ui.adapter


import android.view.View
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.motosproyecto.databinding.ItemBinding
import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.databinding.FragmentMotosBinding
import com.example.motosproyecto.ui.views.MainActivity
import com.example.motosproyecto.ui.views.dialogues.EditDialogue
import com.example.proyectopmdm.data.models.MutableListMotos

class ViewHolderMoto (view: View) : RecyclerView.ViewHolder(view){
    var binding: ItemBinding
    private var mainActivity : MainActivity



    //método que se encarga de mapear los item por propiedad del modelo.

    init {
        binding = ItemBinding.bind(view)
        mainActivity = view.context as MainActivity
    }

    fun renderize(moto: Moto){

        binding.textViewNombreItemRecycler.text = moto.nombre
        binding.textViewAnnoFrabricacionItemRecycler.text = moto.annoFabricacion.toString()
        binding.textViewFabricadoraItemRecycler.text = moto.fabricadora
        binding.textViewPrecioItemRecycler.text = moto.precio.toString()

        Glide.with(itemView.context).load(moto.image).centerCrop().into(binding.imageViewImagenItemRecycler)

        setOnClickListener(adapterPosition, moto)
    }

    private fun setOnClickListener(position: Int, moto: Moto) {
        binding.imageButtonBorrarItemRecycler.setOnClickListener { view ->
            borrarItem(position)
        }

        binding.imageButtonEditarItemRecycler.setOnClickListener { view ->
            editItem(position, moto)
        }
    }

    private fun borrarItem(position: Int) {
        mainActivity.motosViewModel.deleteMotos(adapterPosition)
    }

    private fun editItem(position: Int, moto: Moto) {
        val dialog = EditDialogue(
            position,
            moto,
            {
                    moto, pos -> mainActivity.motosViewModel.updateMotos(pos, moto)
                    // No puedo binding.recyclerMotos.layoutManager!!.scrollToPosition(MutableListMotos.motos.lastIndex)
            }
        )
        dialog.show(mainActivity.supportFragmentManager, "Editar")

    }
}