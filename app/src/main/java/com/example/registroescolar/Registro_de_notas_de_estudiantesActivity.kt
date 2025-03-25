package com.example.registroescolar

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.view.View
import com.example.registroescolar.Registros.Registros_estudiantes
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Registro_de_notas_de_estudiantesActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    //variables para los datos del CRUD
    private lateinit var edtNombre: EditText
    private lateinit var edtApellido: EditText
    private lateinit var spinnerGrado: Spinner
    private lateinit var spinnerMateria: Spinner
    private lateinit var edtNotaFinal: EditText
    private lateinit var database: DatabaseReference
    private lateinit var btnVerRegistros: FloatingActionButton
    // para el usuarioid
    private var userId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_de_notas_de_estudiantes)

        auth = FirebaseAuth.getInstance()

        //recuperando usuario id
        userId = intent.getStringExtra("USER_ID")
        if (userId == null) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        database = FirebaseDatabase.getInstance().getReference("Registros")

        val btnCerrarSesion = findViewById<Button>(R.id.btncerrarsesion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnVerRegistros = findViewById(R.id.Verregistros)

        // Configurar el botón de cerrar sesión
        btnCerrarSesion.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        //boton guardar
        btnGuardar.setOnClickListener {
            guardarregistro(it)
        }
        //boton cancelar
        btnCancelar.setOnClickListener {
            cancelar(it)
        }
        //boton ver registros
        btnVerRegistros.setOnClickListener {
            val intent = Intent(this, VerRegistrosActivity::class.java)
           //Le estoy enviando el id del usuario a la siguiente actividad
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
        edtNombre = findViewById(R.id.edtNombre)
        edtApellido = findViewById(R.id.edtApellido)
        spinnerGrado = findViewById(R.id.spinnerGrado)
        spinnerMateria = findViewById(R.id.spinnerMateria)
        edtNotaFinal = findViewById(R.id.edtNotaFinal)

        // Datos para los Spinners (Grados y Materias)
        val grados = arrayOf("Primer Grado", "Segundo Grado", "Tercer Grado", "Cuarto Grado", "Quinto Grado", "Sexto Grado", "Septimo Grado", "Octavo Grado", "Noveno Grado")
        val materias = arrayOf("Matemáticas", "Ciencias", "Lenguaje", "Sociales", "Inglés", "Educación Física", "Informatica")

        // Adaptadores para los Spinners
        val adaptadorGrados = ArrayAdapter(this, android.R.layout.simple_spinner_item, grados)
        val adaptadorMaterias = ArrayAdapter(this, android.R.layout.simple_spinner_item, materias)

        // Los adaptadores en los Spinners
        spinnerGrado.adapter = adaptadorGrados
        spinnerMateria.adapter = adaptadorMaterias
    }

    fun guardarregistro(view: View) {
        val nombre = edtNombre.text.toString()
        val apellido = edtApellido.text.toString()
        val grado = spinnerGrado.selectedItem.toString()
        val materia = spinnerMateria.selectedItem.toString()
        val notaFinal = edtNotaFinal.text.toString().toDoubleOrNull()
          //Validaciones
        if (nombre.isEmpty() || apellido.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        if (notaFinal == null) {
            Toast.makeText(this, "Por favor, ingrese una nota numérica válida", Toast.LENGTH_SHORT).show()
            return
        }

        if (notaFinal < 0 || notaFinal > 10) {
            Toast.makeText(this, "La nota final debe estar entre 0 y 10", Toast.LENGTH_SHORT).show()
            return
        }
         //Creando el objeto registro con los datos ingresados y el id del usuario
        val registro = Registros_estudiantes(nombre, apellido, grado, materia, notaFinal, userId)

        val newKey = database.push().key
        if (newKey != null) {
            database.child(newKey).setValue(registro).addOnSuccessListener {
                Toast.makeText(this, "Registro guardado con éxito", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al guardar el registro", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No se pudo generar una clave", Toast.LENGTH_SHORT).show()
        }
    }
    //mandar a la actividad de ver registros
    fun cancelar(view: View) {
        val intent = Intent(this, VerRegistrosActivity::class.java)
       //Le estoy enviando el id del usuario a la siguiente actividad
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
    }
    //limpiar campos
    private fun limpiarCampos() {
        edtNombre.text.clear()
        edtApellido.text.clear()
        edtNotaFinal.text.clear()
    }


}


