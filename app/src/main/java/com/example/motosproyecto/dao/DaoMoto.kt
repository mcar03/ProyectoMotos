package com.example.motosproyecto.dao

import com.example.motosproyecto.interfaces.InterfaceMoto
import com.example.motosproyecto.models.Moto
import com.example.motosproyecto.object_models.Repository

class DaoMoto private constructor(): InterfaceMoto {

    companion object{
        val  myDao: DaoMoto by lazy {
            DaoMoto()
        }
    }
        override fun getDataMoto() : List<Moto> = Repository.listMotos
}