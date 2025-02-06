package com.example.motosproyecto.ui.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motosproyecto.R
import com.example.motosproyecto.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferencias: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 100

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

        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        editor = preferencias.edit()

        auth = Firebase.auth
        checkPreferences()
        initEvent()
        initGoogleSignIn()
        initFacebookSignIn()
        initGithubSignIn()
        initTwitterSignIn()
    }

    private fun checkPreferences() {
        if (preferencias.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun initEvent() {
        binding.buttonLoginEntrar.setOnClickListener {
            val user = binding.editTextLoginUsername.text.toString()
            val pass = binding.editTextLoginPassword.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                startLogin(user, pass) { result, msg ->
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                    if (result) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Tienes algún campo vacío", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonLoginRegistrarse.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, OlvidoPswActivity::class.java))
        }
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.imageButtonIniciarConGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun initFacebookSignIn() {
        binding.imageButtonIniciarConFacebook.setOnClickListener {
            //Toast.makeText(this, "Inicio de sesión con Facebook no implementado todavía.", Toast.LENGTH_SHORT).show()
            val provider = OAuthProvider.newBuilder("Facebook.com")

            auth.startActivityForSignInWithProvider(this, provider.build())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Bienvenido ${user?.displayName}", Toast.LENGTH_SHORT).show()

                        editor.putString("username", user?.email)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error con Facebook: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun initGithubSignIn() {
        binding.imageButtonIniciarConGithub.setOnClickListener {
            val provider = OAuthProvider.newBuilder("github.com")
            provider.addCustomParameter("allow_signup", "false")

            auth.startActivityForSignInWithProvider(this, provider.build())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Bienvenido ${user?.displayName}", Toast.LENGTH_SHORT).show()

                        editor.putString("username", user?.email)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error con GitHub: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun initTwitterSignIn() {
        binding.imageButtonIniciarConTwitter.setOnClickListener {
            val provider = OAuthProvider.newBuilder("X.com")

            auth.startActivityForSignInWithProvider(this, provider.build())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Bienvenido ${user?.displayName}", Toast.LENGTH_SHORT).show()

                        editor.putString("username", user?.email)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error con Twitter: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun startLogin(user: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val posibleUser = auth.currentUser
                    if (posibleUser?.isEmailVerified == true) {
                        editor.putString("username", user)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()
                        onResult(true, "Usuario logueado satisfactoriamente")
                    } else {
                        auth.signOut()
                        onResult(false, "Debes verificar tu correo antes de loguearte")
                    }
                } else {
                    val msg = when (val e = task.exception) {
                        is FirebaseAuthInvalidUserException -> "El usuario no existe o ha sido deshabilitado."
                        is FirebaseAuthInvalidCredentialsException -> "Credenciales incorrectas."
                        else -> e?.localizedMessage ?: "Error desconocido."
                    }
                    onResult(false, msg)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Error con Google: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    editor.putString("username", user?.email)
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error al autenticar con Google", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
