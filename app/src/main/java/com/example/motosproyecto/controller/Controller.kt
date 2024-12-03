package com.example.motosproyecto.controller

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motosproyecto.MainActivity
import com.example.motosproyecto.adapter.AdapterMoto
import com.example.motosproyecto.dao.DaoMoto
import com.example.motosproyecto.databinding.ActivityMainBinding
import com.example.motosproyecto.models.Moto

class Controller(val context: Context, var binding: ActivityMainBinding) {
    lateinit var listaMoto: MutableList<Moto>

    init {
        initData()
    }

    fun initData(){
        listaMoto = DaoMoto. myDao.getDataMoto(). toMutableList()
    }

    fun setAdapter() {
        val myActivity = context as MainActivity
        val adapterMoto = AdapterMoto(
            listaMoto,
            {pos -> delMoto(pos)},
        )

        myActivity. binding.recyclerMotos.adapter = adapterMoto
        myActivity.binding.recyclerMotos.layoutManager = LinearLayoutManager(context)
    }


    fun delMoto(pos: Int) {
        val nombreArtista = listaMoto[pos].nombre
        listaMoto.removeAt(pos)
        (binding.recyclerMotos.adapter as AdapterMoto).notifyItemRemoved(pos)
        (binding.recyclerMotos.adapter as AdapterMoto).notifyItemRangeChanged(pos, listaMoto.size - pos)
        Toast.makeText(context, "Se eliminó la moto: $nombreArtista", Toast.LENGTH_SHORT).show()
    }
}