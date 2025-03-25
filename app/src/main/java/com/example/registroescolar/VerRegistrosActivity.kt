package com.example.registroescolar

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.registroescolar.Registros.Registros_estudiantes
import com.google.firebase.database.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class VerRegistrosActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var linearLayoutRegistros: LinearLayout
    private lateinit var agregarRegistrosButton: FloatingActionButton
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_registros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().getReference("Registros")
        linearLayoutRegistros = findViewById(R.id.llRegistros)
        agregarRegistrosButton = findViewById(R.id.agregarregistros)
        auth = FirebaseAuth.getInstance()

        // Botón de cerrar sesión
        val btnCerrarSesion = findViewById<Button>(R.id.btncerrarsesion)
        btnCerrarSesion.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        userId = intent.getStringExtra("USER_ID")
        if (userId == null) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        obtenerRegistros(userId!!)
          //Ir ala actividad de registro
        agregarRegistrosButton.setOnClickListener {
            val intent = Intent(this, Registro_de_notas_de_estudiantesActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
    }

    private fun obtenerRegistros(userId: String) {
        database.orderByChild("userId").equalTo(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                linearLayoutRegistros.removeAllViews()
                if (snapshot.exists()) {
                    for (registroSnapshot in snapshot.children) {
                        val registro = registroSnapshot.getValue(Registros_estudiantes::class.java)
                        registro?.let {
                            mostrarRegistro(it)
                        }
                    }
                } else {
                    Toast.makeText(this@VerRegistrosActivity, "No hay  registrados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@VerRegistrosActivity, "Error al leer los datos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarRegistro(registro: Registros_estudiantes) {
        val view = LayoutInflater.from(this).inflate(
            R.layout.registroestudianteitem,
            linearLayoutRegistros,
            false
        )
        val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        val tvApellido = view.findViewById<TextView>(R.id.tvApellido)
        val tvGrado = view.findViewById<TextView>(R.id.tvGrado)
        val tvMateria = view.findViewById<TextView>(R.id.tvMateria)
        val tvNotaFinal = view.findViewById<TextView>(R.id.tvNotaFinal)

        // Mostrar los datos del registro
        tvNombre.text = "Nombre: ${registro.nombre}"
        tvApellido.text = "Apellido: ${registro.apellido}"
        tvGrado.text = "Grado: ${registro.grado}"
        tvMateria.text = "Materia: ${registro.materia}"
        tvNotaFinal.text = "Nota Final: ${registro.notaFinal}"

        linearLayoutRegistros.addView(view)

    }}

