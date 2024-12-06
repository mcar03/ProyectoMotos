package com.example.motosproyecto.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class DialogFragmentPrueba : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
// Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("prueba")
                .setPositiveButton("Añadir",
                    DialogInterface.OnClickListener { dialog, id ->
// START THE GAME!
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
// User cancelled the dialog
                    })
// Create the AlertDialog object and return it
            builder.create() //podemos quitar el builder
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}