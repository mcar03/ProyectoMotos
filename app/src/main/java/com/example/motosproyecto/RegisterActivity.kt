package com.example.motosproyecto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motosproyecto.databinding.ActivityLoginBinding
import com.example.motosproyecto.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var etEmail : EditText
    private lateinit var etUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var etConfirmarContrasena: EditText
    private lateinit var btnRegistrarse: Button
    private lateinit var tvTienesCuenta: TextView
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        etEmail = binding.editTextRegisterEmail
        etUsuario = binding.editTextRegisterUsername
        etContrasena = binding.editTextRegisterPassword
        etConfirmarContrasena = binding.editTextRegisterConfirmarPassword

        btnRegistrarse = binding.buttonRegistrarse
        btnRegistrarse.setOnClickListener {

            if (etEmail.text.toString().isNotEmpty() && etUsuario.text.toString().isNotEmpty() &&
                etContrasena.text.toString().isNotEmpty()
                && etConfirmarContrasena.text.toString().isNotEmpty() &&
                etContrasena.text.toString().equals(etConfirmarContrasena.text.toString())) {

                registerUser (etEmail.text.toString(), etContrasena.text.toString()){
                        result, msg ->
                    Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_LONG).show()
                    if (result) {

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            }
        }

        tvTienesCuenta = binding.tvTengoCuenta
        tvTienesCuenta.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { taskAssin ->
                if (taskAssin.isSuccessful) {
                    //enviaremos un email de confirmación
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { taskVerification ->
                            var msg = ""
                            if (taskVerification.isSuccessful)
                                msg = "Usuario Registrado Ok. Verifique su correo"
                            else
                                msg =
                                    "Usuario Registrado Ok. ${taskVerification.exception?.message}"
                            auth.signOut() //tiene que verificar antes el email
                            onResult(true, msg)
                        }
                        ?.addOnFailureListener { exception ->
                            Log.e(
                                "ActivityRegister",
                                "Fallo al enviar correo de verificación: ${exception.message}"
                            )
                            onResult(
                                false,
                                "No se pudo enviar el correo de verificación: ${exception.message}"
                            )
                        }

                } else {
                    try {
                        throw taskAssin.exception ?: Exception("Error desconocido")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        onResult(false, "Ese usuario ya existe")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        onResult(false, "La contraseña es débil: ${e.reason}")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        onResult(false, "El email proporcionado, no es válido")
                    } catch (e: Exception) {
                        onResult(false, e.message.toString())
                    }

                }
            }
    }
}