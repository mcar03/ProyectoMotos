package com.example.motosproyecto.adapter


import android.view.View
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.motosproyecto.databinding.ItemBinding
import com.example.motosproyecto.models.Moto

class ViewHolderMoto (private val binding: ItemBinding,
                      private val borrarItemRecycler: (Int) -> Unit,
                      private val editOnClick: (Int, Moto)  -> Unit)
                        : RecyclerView.ViewHolder(binding.root){

    //método que se encarga de mapear los item por propiedad del modelo.

    fun renderize(moto: Moto){

        binding.textViewNombreItemRecycler.text = moto.nombre
        binding.textViewAnnoFrabricacionItemRecycler.text = moto.annoFabricacion.toString()
        binding.textViewFabricadoraItemRecycler.text = moto.fabricadora
        binding.textViewPrecioItemRecycler.text = moto.precio.toString()

        Glide.with(itemView.context).load(moto.image).centerCrop().into(binding.imageViewImagenItemRecycler)

        borrarItem()
        editItem(adapterPosition, moto)
    }

    private fun borrarItem() {
        binding.imageButtonBorrarItemRecycler.setOnClickListener {
            borrarItemRecycler(adapterPosition)
        }
    }

    private fun editItem(position: Int, moto: Moto) {
        binding.imageButtonEditarItemRecycler.setOnClickListener { view ->
            editOnClick(position, moto)
        }

    }
}