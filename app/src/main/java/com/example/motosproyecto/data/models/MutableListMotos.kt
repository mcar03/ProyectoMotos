package com.example.proyectopmdm.data.models

import com.example.motosproyecto.data.models.Moto




class MutableListMotos {
    companion object {
        var motos:MutableList<Moto> = emptyList<Moto>().toMutableList()
    }
}