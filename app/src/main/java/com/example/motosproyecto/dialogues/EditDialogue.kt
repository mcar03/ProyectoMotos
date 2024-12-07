package com.example.motosproyecto.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.motosproyecto.R
import com.example.motosproyecto.models.Moto
import com.example.motosproyecto.databinding.FragmentDialogBinding

import java.lang.reflect.Field

class EditDialogue(
    val pos: Int,
    val moto: Moto,
    val okOnEditMoto: (Moto, Int) -> Unit
) : DialogFragment() {

    init {
        val args = Bundle().apply{
            putString("NOMBRE_MOTO", moto.nombre)
            putString("AÑO_MOTO", moto.annoFabricacion.toString())
            putString("FRABRICADORA_MOTO", moto.fabricadora)
            putString("PRECIO_MOTO", moto.precio.toString())
            putString("URL_MOTO", moto.image)
        }
        this.arguments = args
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{ activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            val viewMoto = inflater.inflate(R.layout.fragment_dialog, null)
            builder.setView(viewMoto)
            setFields(viewMoto)
            builder.setMessage("Editar moto")
            builder.setPositiveButton("Editar",
                DialogInterface.OnClickListener { dialog, id ->
                    val motoNueva = createMoto(viewMoto)

                    if (isMotoFilled(motoNueva))
                        okOnEditMoto(motoNueva, pos)
                    else
                        Toast.makeText(activity, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show()
                })
            builder.setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(activity, "Operación cancelada", Toast.LENGTH_LONG).show()
                    dialog?.dismiss()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setFields(view: View) {
        val binding = FragmentDialogBinding.bind(view)
        arguments?.let{ args->
            binding.etNombreMoto.setText(args.getString("NOMBRE_MOTO"))
            binding.etAnno.setText(args.getString("AÑO_MOTO"))
            binding.etFrabricadora.setText(args.getString("FRABRICADORA_MOTO"))
            binding.etPrecioMoto.setText(args.getString("PRECIO_MOTO"))
            binding.etImagen.setText(args.getString("URL_MOTO"))
        }
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