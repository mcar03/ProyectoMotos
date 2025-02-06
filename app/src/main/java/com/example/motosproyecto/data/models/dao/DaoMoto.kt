package com.example.motosproyecto.data.models.dao

import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.data.datasource.Motos
import com.example.proyectopmdm.data.models.MutableListMotos

class DaoMoto private constructor() {

    companion object{
        val  myDao: DaoMoto by lazy {
            DaoMoto()
        }
    }

    fun getDataMoto() : List<Moto> {
        MutableListMotos.motos = Motos.listMotos.toMutableList()
        return MutableListMotos.motos
    }

     fun getMotos(): List<Moto> = MutableListMotos.motos

     fun addMoto(moto: Moto): List<Moto> {
         MutableListMotos.motos.add(moto)
        return getMotos()
    }

     fun removeMoto(pos: Int): List<Moto> {
         MutableListMotos.motos.removeAt(pos)
        return getMotos()
    }

     fun updateMoto(pos: Int, moto: Moto): List<Moto> {
         MutableListMotos.motos.set(pos, moto)
        return getMotos()
    }

}