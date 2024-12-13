package com.example.motosproyecto

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motosproyecto.controller.Controller
import com.example.motosproyecto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var controller : Controller

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

        var botonAnnadir = binding.imageButtonAnnadirItemRecycler
        botonAnnadir.setOnClickListener {
            controller.addMoto()
        }
    }


}