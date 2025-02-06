package com.example.motosproyecto.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.domain.usecase.AddMotoUseCase
import com.example.motosproyecto.domain.usecase.DeleteMotoUseCase
import com.example.motosproyecto.domain.usecase.GetMotoUseCase
import com.example.motosproyecto.domain.usecase.UpdateMotoUseCase
import com.example.proyectopmdm.data.models.MutableListMotos
import kotlinx.coroutines.launch

class RecyclerViewModel : ViewModel() {
    private val getMotoUseCase = GetMotoUseCase()
    private val addMotoUseCase = AddMotoUseCase()
    private val deleteMotoUseCase = DeleteMotoUseCase()
    private val updateMotoUseCase = UpdateMotoUseCase()
    var motosLiveData = MutableLiveData<List<Moto>>()

    fun listMotos() {
        viewModelScope.launch {
            val motos = getMotoUseCase()
            motosLiveData.value = motos
        }
    }

    fun addMotos(moto: Moto) {
        viewModelScope.launch {
            val motos = addMotoUseCase(moto)
            motosLiveData.value = motos
        }
    }

    fun deleteMotos(pos: Int) {
        viewModelScope.launch {
            val motos = deleteMotoUseCase(pos)
            motosLiveData.value = motos
        }
    }

    fun updateMotos(pos: Int, moto: Moto) {
        viewModelScope.launch {
            val motos = updateMotoUseCase(pos, moto)
            motosLiveData.value = motos
        }
    }
}