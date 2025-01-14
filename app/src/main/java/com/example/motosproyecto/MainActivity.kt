package com.example.motosproyecto

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motosproyecto.controller.Controller
import com.example.motosproyecto.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var controller : Controller
    lateinit var  botonLogout : FloatingActionButton
    lateinit var preferencias: SharedPreferences
    private lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initEvent()
    }

    private fun initEvent() {
        binding.recyclerMotos.layoutManager = LinearLayoutManager( this)
        controller = Controller(this, binding)
        controller.setAdapter()

        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        editor = preferencias.edit()

        botonLogout = binding.logoutButton
        botonLogout.setOnClickListener {
            editor.putString("username", "")
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }

        var botonAnnadir = binding.imageButtonAnnadirItemRecycler
        botonAnnadir.setOnClickListener {
            controller.addMoto()
        }
    }


}