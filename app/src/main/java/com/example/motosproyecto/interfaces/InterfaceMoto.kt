package com.example.motosproyecto.interfaces

import com.example.motosproyecto.models.Moto

interface InterfaceMoto {

    interface InterfaceMusica {
        fun getDataMoto(): List<Moto>
    }

    fun getDataMoto(): List<Moto>
}