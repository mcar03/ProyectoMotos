package com.example.motosproyecto.ui.views.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.motosproyecto.R
import com.example.motosproyecto.data.models.Moto
import com.example.motosproyecto.databinding.FragmentDialogBinding

class CreateDialogue(
    var okOnCreateMoto: (Moto) -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            val viewMoto = inflater.inflate(R.layout.fragment_dialog, null)
            builder.setView(viewMoto)
            builder.setMessage("Añadir Moto")
            builder.setPositiveButton("Añadir",
                DialogInterface.OnClickListener { dialog, id ->
                    val moto = createMoto(viewMoto)

                    if (isMotoFilled(moto))
                        okOnCreateMoto(moto)
                    else
                        Toast.makeText(activity, "Todos los campos excepto imagen son obligatorios", Toast.LENGTH_LONG).show()
                })
            builder.setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(activity, "Operación cancelada", Toast.LENGTH_LONG).show()
                    dialog?.dismiss()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun createMoto(view: View) : Moto {
        val binding = FragmentDialogBinding.bind(view)
        return Moto(
            binding.etNombreMoto.text.toString(),
            binding.etAnno.text.toString().toInt(),
            binding.etFrabricadora.text.toString(),
            binding.etPrecioMoto.text.toString().toDouble(),
            binding.etImagen.text.toString()
        )
    }

    private fun isMotoFilled(moto: Moto) : Boolean {
        return (! (moto.nombre.isNullOrBlank() || moto.precio.toString().isNullOrBlank() ||
                moto.fabricadora.isNullOrBlank() || moto.annoFabricacion.toString().isNullOrBlank()
                || moto.image.isNullOrBlank()))
    }
}