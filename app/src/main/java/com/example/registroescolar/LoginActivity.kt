package com.example.registroescolar

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_profesores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email: String = etEmail.text.toString()
            val password: String = etPassword.text.toString()
            signIn(email, password)
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun signIn(email: String, password: String) {
        // Validar los campos (bloque de validación único)
        when {
            email.isEmpty() || password.isEmpty() -> {
                showAlertDialog("Error", "Por favor, complete todos los campos")
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showAlertDialog("Error", "Por favor, ingrese una dirección de correo electrónico válida")
                return
            }
            password.length < 6 -> {
                showAlertDialog("Error", "La contraseña debe tener al menos 6 caracteres")
                return
            }
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Inicio de sesión exitoso
                    val user = auth.currentUser
                    Log.d(TAG, "signInWithEmail:success, User: $user")
                    //Obtengo el id usuario(Profesos)
                    val uid = auth.currentUser?.uid
                    // vamos a la siguiente actividad
                    val intent = Intent(this, VerRegistrosActivity::class.java)
                   //Le estoy enviando el id del usuario a la siguiente actividad
                    intent.putExtra("USER_ID", uid)
                    startActivity(intent)
                } else {
                    // Fallo el inicio de sesion e mujuestra un mensaje
                    Log.w(TAG, "Se ha producido un error. Revisa tus credenciales por favor.", task.exception)
                    // Aquí muestra un mensaje de error al usuario
                    showAlertDialog("Error", "Error al iniciar sesión, revise sus credenciales por favor.")
                }
            }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}