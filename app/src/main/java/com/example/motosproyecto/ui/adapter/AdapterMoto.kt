package com.example.motosproyecto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motosproyecto.R
import com.example.motosproyecto.databinding.ActivityMainBinding
import com.example.motosproyecto.databinding.ItemBinding
import com.example.motosproyecto.data.models.Moto
import com.example.proyectopmdm.data.models.MutableListMotos

private lateinit var binding: ActivityMainBinding

class AdapterMoto(): RecyclerView.Adapter<ViewHolderMoto>(){


    /*Método que crea la view del ViewHolderHotel*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMoto {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cardViewLayout = R.layout.item
        return ViewHolderMoto(layoutInflater.inflate(cardViewLayout, parent, false))



    }

    /*Este método, debe renderizar todos los datos o propiedades de cada hotel con la view.
        Accedemos al objeto por medio de position*/

    override fun onBindViewHolder(holder: ViewHolderMoto, position: Int) {
        holder.renderize(MutableListMotos.motos.get(position))   //renderizamos la view.
    }

    /*Este método, devuelve el número de objetos a representar en el recyclerView.*/

    override fun getItemCount(): Int = MutableListMotos.motos.size
}