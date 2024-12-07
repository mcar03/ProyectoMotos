package com.example.motosproyecto.controller


import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motosproyecto.MainActivity
import com.example.motosproyecto.adapter.AdapterMoto
import com.example.motosproyecto.dao.DaoMoto
import com.example.motosproyecto.databinding.ActivityMainBinding
import com.example.motosproyecto.dialogues.CreateDialogue
import com.example.motosproyecto.dialogues.EditDialogue
import com.example.motosproyecto.models.Moto

class Controller(val context: Context, var binding: ActivityMainBinding) {
    lateinit var listaMoto: MutableList<Moto>
    lateinit var adapterMoto: AdapterMoto
    lateinit var mainActivity: MainActivity
    lateinit var layoutManager: LinearLayoutManager

    init {
        initData()
    }

    fun initData(){
        listaMoto = DaoMoto. myDao.getDataMoto(). toMutableList()
        mainActivity = context as MainActivity
        layoutManager = mainActivity.binding.recyclerMotos.layoutManager as LinearLayoutManager
    }

    fun setAdapter() {

        adapterMoto = AdapterMoto(
            listaMoto,
            {pos -> delMoto(pos)},{pos, moto -> editMoto(pos, moto) }
        )

        mainActivity. binding.recyclerMotos.adapter = adapterMoto
        mainActivity.binding.recyclerMotos.layoutManager = LinearLayoutManager(context)
    }


    fun delMoto(pos: Int) {

        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Confirmación")
            .setMessage("¿Deseas eliminar la moto: ${listaMoto[pos].nombre}?")
            .setPositiveButton("Sí"){_,_ ->

                val nombreMoto = listaMoto[pos].nombre
                listaMoto.removeAt(pos)
                (binding.recyclerMotos.adapter as AdapterMoto).notifyItemRemoved(pos)
                (binding.recyclerMotos.adapter as AdapterMoto).notifyItemRangeChanged(pos, listaMoto.size - pos)
                Toast.makeText(context, "Se eliminó la moto: $nombreMoto", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") {dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    private fun editMoto(pos: Int, moto: Moto) {
        val dialog = EditDialogue(
            pos,
            moto,
            {
                    moto, pos -> okOnEditMoto(moto, pos)
            }
        )
        dialog.show(mainActivity.supportFragmentManager, "Editar")
    }

    private fun okOnEditMoto(moto: Moto, pos: Int) {
        val oldMoto = listaMoto.get(pos)
        oldMoto.nombre = moto.nombre
        oldMoto.annoFabricacion = moto.annoFabricacion
        oldMoto.fabricadora = moto.fabricadora
        oldMoto.precio = moto.precio
        oldMoto.image = moto.image
        adapterMoto.notifyItemChanged(pos)
    }

    fun addMoto() {
        val dialog = CreateDialogue(

            {
                    moto -> okOnCreateMoto(moto)
            })
        dialog.show(mainActivity.supportFragmentManager, "Añadir")
    }

    private fun okOnCreateMoto(moto: Moto) {
        listaMoto.add(moto)
        adapterMoto.notifyItemInserted(listaMoto.lastIndex)
        layoutManager.scrollToPositionWithOffset(listaMoto.lastIndex, 20)
    }
}


