package com.example.motosproyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motosproyecto.databinding.ActivityOlvidopswBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth


class OlvidoPswActivity : AppCompatActivity() {


    private lateinit var binding: ActivityOlvidopswBinding
    private lateinit var botonenviar: Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOlvidopswBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initEvent()
    }

    private fun initEvent() {
        auth = Firebase.auth


         botonenviar = binding.buttonEnviarOlvidoContrasena
        botonenviar.setOnClickListener {
            var email = binding.editTextOlvidoContrasenaUsername.text.toString()
            if (email.isNotEmpty())
                recoverPassword(email){
                        result, msg ->
                    Toast.makeText(this@OlvidoPswActivity, msg, Toast.LENGTH_LONG).show()
                    if (result){
                        intent = Intent(this@OlvidoPswActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            else
                Toast.makeText(this, "Tienes algún campo vacío", Toast.LENGTH_LONG).show()
        }
    }

    private fun recoverPassword(email : String, onResult: (Boolean, String)->Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{
                    taskResetEmail ->
                if (taskResetEmail.isSuccessful){
                    onResult (true, "Acabamos de enviarte un email con la nueva password")
                }else{
                    var msg = ""
                    try{
                        throw taskResetEmail.exception?:Exception("Error de reseteo inesperado")
                    }catch (e : FirebaseAuthInvalidCredentialsException){
                        msg = "El formato del email es incorrecto"
                    }catch (e: Exception){
                        msg = e.message.toString()
                    }
                    onResult(false, msg)


                }
            }


    }
}



