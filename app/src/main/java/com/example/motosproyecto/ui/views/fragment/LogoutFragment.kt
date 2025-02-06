package com.example.motosproyecto.ui.views.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.motosproyecto.ui.views.LoginActivity
import com.example.motosproyecto.R
import com.google.firebase.auth.FirebaseAuth

class LogoutFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()  // Inicializa FirebaseAuth
        initEvent()
    }

    private fun initEvent() {
        logout()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    private fun logout() {
        // Cerrar sesión con Firebase
        auth.signOut()

        // Redirigir al usuario a la pantalla de inicio de sesión
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)

        // Finalizar la actividad para que no vuelva a la pantalla de ajustes
        activity?.finish()
    }
}
