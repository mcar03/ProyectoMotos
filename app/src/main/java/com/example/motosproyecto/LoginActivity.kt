package com.example.motosproyecto

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motosproyecto.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var preferencias: SharedPreferences
    private lateinit var editor: Editor
    private lateinit var botonEntrar: Button
    private lateinit var botonRegistrarse : Button
    private lateinit var textoOlvidaContrasena : TextView
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initEvent()
    }

    private fun checkPreferences() {
        if (preferencias.getBoolean("isLoggedIn", false)) {
            intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initEvent() {

        preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE)

        editor = preferencias.edit()
        checkPreferences()

        auth = Firebase.auth
        botonEntrar = binding.buttonLoginEntrar
        botonEntrar.setOnClickListener {

            val user = binding.editTextLoginUsername.text
            val pass = binding.editTextLoginPassword.text

            if (user.isNotEmpty() && pass.isNotEmpty())
                startLogin(user.toString(), pass.toString()){
                        result, msg ->
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                    if (result){
                        intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            else
                Toast.makeText(this, "Tienes algún campo vacío", Toast.LENGTH_LONG).show()
        }


        botonRegistrarse = binding.buttonLoginRegistrarse
        botonRegistrarse.setOnClickListener {
            intent = Intent (this, RegisterActivity::class.java)
            startActivity(intent)
        }

        textoOlvidaContrasena = binding.tvForgotPassword
        textoOlvidaContrasena.setOnClickListener {

            intent = Intent (this, OlvidoPswActivity::class.java)
            startActivity(intent)
        }
    }




    private fun startLogin(user: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener {
                    taskAssin ->
                var msg = ""
                if (taskAssin.isSuccessful){
                    //debemos comprobar si el usuario ha verificado el email
                    val posibleUser = auth.currentUser
                    if (posibleUser?.isEmailVerified == true){
                        editor.putString("username", user)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()
                        onResult ( true, "Usuario Logueado satisfactoriamente")
                    }else{
                        auth.signOut() //hay que desloguearse, porque no ha verificado.
                        onResult (false, "Debes verificar tu correo antes de loguearte")
                    }
                }else{
                    try {
                        throw taskAssin.exception?: Exception("Error desconocido")
                    }catch (e: FirebaseAuthInvalidUserException){
                        msg = "El usuario tiene problemas por haberse borrado o desabilitado"
                    }catch (e: FirebaseAuthInvalidCredentialsException){
                        msg = if (e.message?.contains("There is no user record corresponding to this identifier") == true){
                            "El usuario no existe"
                        }else "contraseña incorrecta"

                    }catch (e: Exception){
                        msg = e.message.toString()
                    }

                    onResult (false, msg)  //genérico.
                }

            }

    }
}