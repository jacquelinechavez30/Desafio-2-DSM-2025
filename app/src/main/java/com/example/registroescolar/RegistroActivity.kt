package com.example.registroescolar

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.widget.TextView

class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etRegisterEmail: EditText
    private lateinit var etRegisterPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterPassword = findViewById(R.id.etRegisterPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)

        btnRegister.setOnClickListener {
            val email: String = etRegisterEmail.text.toString()
            val password: String = etRegisterPassword.text.toString()
            signUp(email, password)
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun signUp(email: String, password: String) {
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

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso, actualizar la interfaz de usuario con la información del usuario registrado
                    val user = auth.currentUser
                    Log.d(TAG, "signUpWithEmail:success, User: $user")
                    // Aquí puedes redirigir a la siguiente actividad o realizar otras operaciones después del registro exitoso
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // Si el registro falla, mostrar un mensaje al usuario.
                    Log.w(TAG, "signUpWithEmail:failure", task.exception)
                    // Aquí puedes mostrar un mensaje de error al usuario, por ejemplo, Toast
                    showAlertDialog("Error", "signUpWithEmail:failure" + task.exception)
                }
            }
    }

    companion object {
        private const val TAG = "RegistroActivity"
    }
}