package com.example.motosproyecto.domain.usecase

import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.data.models.dao.DaoMoto

class DeleteMotoUseCase {
    operator fun invoke(pos: Int): List<Moto> {
        return DaoMoto.myDao.removeMoto(pos)
    }
}