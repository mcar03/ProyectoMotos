package com.example.motosproyecto.adapter


import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.motosproyecto.databinding.ItemBinding
import com.example.motosproyecto.models.Moto

class ViewHolderMoto (private val binding: ItemBinding,private val borrarItemRecycler: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root){

    //método que se encarga de mapear los item por propiedad del modelo.

    fun renderize(moto: Moto){

        binding.textViewNombreItemRecycler.text = moto.nombre
        binding.textViewAnnoFrabricacionItemRecycler.text = moto.annoFabricacion.toString()
        binding.textViewFabricadoraItemRecycler.text = moto.fabricadora
        binding.textViewPrecioItemRecycler.text = moto.precio.toString()

        Glide.with(itemView.context).load(moto.image).centerCrop().into(binding.imageViewImagenItemRecycler)

        borrarItem()
    }

    private fun borrarItem() {
        binding.imageButtonBorrarItemRecycler.setOnClickListener {
            borrarItemRecycler(adapterPosition)
        }
    }


}