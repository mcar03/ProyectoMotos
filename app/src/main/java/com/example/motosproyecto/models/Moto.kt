package com.example.motosproyecto.models

class Moto (
    var nombre: String,
    var annoFabricacion: Int,
    var fabricadora: String,
    var precio: Double,
    var image: String
){

    override fun toString(): String {
        return "Moto(nombre = '$nombre',annoFabricacion = '$annoFabricacion', fabricadora = '$fabricadora',precio = '$precio',image= '$image' )"
    }

}