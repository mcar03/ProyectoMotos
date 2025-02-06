package com.example.motosproyecto.domain.usecase

import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.data.models.dao.DaoMoto

class UpdateMotoUseCase {
    operator fun invoke(pos: Int, moto: Moto): List<Moto> {
        return DaoMoto.myDao.updateMoto(pos,moto)
    }
}