package com.example.motosproyecto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motosproyecto.R
import com.example.motosproyecto.databinding.ActivityMainBinding
import com.example.motosproyecto.databinding.ItemBinding
import com.example.motosproyecto.models.Moto

private lateinit var binding: ActivityMainBinding

class AdapterMoto(var listMoto : MutableList<Moto>,var borrarItemRecycler: (Int) -> Unit) : RecyclerView.Adapter<ViewHolderMoto>() {

    /*Método que crea la view del ViewHolderHotel*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMoto {

        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderMoto(binding,borrarItemRecycler)

    }

    /*Este método, debe renderizar todos los datos o propiedades de cada hotel con la view.
        Accedemos al objeto por medio de position*/

    override fun onBindViewHolder(holder: ViewHolderMoto, position: Int) {
        holder.renderize (listMoto.get(position))   //renderizamos la view.
    }

    /*Este método, devuelve el número de objetos a representar en el recyclerView.*/

    override fun getItemCount(): Int = listMoto.size
}