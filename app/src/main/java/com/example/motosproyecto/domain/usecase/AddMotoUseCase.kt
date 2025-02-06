package com.example.motosproyecto.domain.usecase

import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.data.models.dao.DaoMoto

class AddMotoUseCase {

    operator fun invoke(moto: Moto) : List<Moto> {
        return DaoMoto.myDao.addMoto(moto)
    }
}