package com.example.motosproyecto.domain.usecase

import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.data.models.dao.DaoMoto

class GetMotoUseCase {
    operator fun invoke(): List<Moto> {
        return DaoMoto.myDao.getMotos()
    }
}