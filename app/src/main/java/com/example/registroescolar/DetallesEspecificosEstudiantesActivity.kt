package com.example.registroescolar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetallesEspecificosEstudiantesActivity : AppCompatActivity() {

    // Definicion de botones
    private lateinit var btnModificar: MaterialButton
    private lateinit var btnEliminar: MaterialButton
    private lateinit var database: DatabaseReference
    private lateinit var verRegistrosButton: FloatingActionButton

    // Obtener referencia a la base de datos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_especificos_estudiantes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencias a los elementos del layout
        val edtIdEstudiante = findViewById<EditText>(R.id.idEstudiante)
        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtApellido = findViewById<EditText>(R.id.edtApellido)
        val spinnerGrado = findViewById<Spinner>(R.id.spinnerGrado)
        val spinnerMateria = findViewById<Spinner>(R.id.spinnerMateria)
        val edtNotaFinal = findViewById<EditText>(R.id.edtNotaFinal)

        // Obtener datos del Intent
        val uid = intent.getStringExtra("USER_ID")
        val codigoEstudiante = intent.getStringExtra("codigoEstudiante")
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val grado = intent.getStringExtra("grado")  // Si es número, usa getIntExtra()
        val materia = intent.getStringExtra("materia")
        val notaFinal = intent.getFloatExtra("notaFinal", 0.0f)  // Usa 0.0f como valor por defecto


        // Adsignacion de valores de los botones
        btnModificar = findViewById(R.id.btnModificar)
        btnEliminar = findViewById(R.id.btnEliminar)
        verRegistrosButton = findViewById(R.id.verregistrosButton)
        database = FirebaseDatabase.getInstance().getReference("Registros")

        // Mostrar los datos en los campos de la UI

        edtIdEstudiante.setText(codigoEstudiante)
        edtNombre.setText(nombre)
        edtApellido.setText(apellido)
        edtNotaFinal.setText(String.format(notaFinal.toString()))

        // Datos para los Spinners (Grados y Materias)
        val grados = arrayOf("Primer Grado", "Segundo Grado", "Tercer Grado", "Cuarto Grado", "Quinto Grado", "Sexto Grado", "Septimo Grado", "Octavo Grado", "Noveno Grado")
        val materias = arrayOf("Matemáticas", "Ciencias", "Lenguaje", "Sociales", "Inglés", "Educación Física", "Informatica")

        // Adaptadores para los Spinners
        val adaptadorGrados = ArrayAdapter(this, android.R.layout.simple_spinner_item, grados)
        val adaptadorMaterias = ArrayAdapter(this, android.R.layout.simple_spinner_item, materias)

        // Los adaptadores en los Spinners
        spinnerGrado.adapter = adaptadorGrados
        spinnerMateria.adapter = adaptadorMaterias

        // Se establecen los valores que vienen con el objeto de estudiante.
        spinnerGrado.setSelection(grados.indexOf(grado))
        spinnerMateria.setSelection(materias.indexOf(materia))



        btnEliminar.setOnClickListener {
            // Eliminar el nodo con el ID específico

            AlertDialog.Builder(this)
                .setTitle("Eliminar Registro")
                .setMessage("¿Estás seguro de que deseas eliminar este registro?")
                .setPositiveButton("Sí") { _, _ ->
                    this.mostrarDialogoConfirmacion(it.context, codigoEstudiante.toString())
                }
                .setNegativeButton("No", null)
                .show()
        }

        verRegistrosButton.setOnClickListener {
            intent = Intent(this, VerRegistrosActivity::class.java)
            intent.putExtra("USER_ID", uid)
            startActivity(intent)
            finish()
        }

    }

    private fun eliminarRegistro(idEstudiante:String) {
        database.child(idEstudiante).removeValue()
            .addOnSuccessListener {
                println("Registro eliminado correctamente")
                Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                //Devolver a la vista anterior
                verRegistrosButton.performClick() // Aquí simulamos un clic en el botón
            }
            .addOnFailureListener { exception ->
                println("Error al eliminar el registro: ${exception.message}")
                Toast.makeText(this, "No se ha podido eliminar el registro", Toast.LENGTH_SHORT).show()
            }
    }

    private fun mostrarDialogoConfirmacion(context: Context, idRegistro: String) {
        val editText = EditText(context)
        editText.hint = "Escribe: Quiero eliminar este registro"

        // Configurar el EditText dentro de un LinearLayout
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 20, 50, 20)
        layout.addView(editText)

        // Crear el AlertDialog
        val dialog = AlertDialog.Builder(context)
            .setTitle("Confirmar Eliminación")
            .setMessage("Para eliminar este registro, escribe: 'Quiero eliminar este registro'")
            .setView(layout)
            .setPositiveButton("Eliminar") { _, _ ->
                val textoIngresado = editText.text.toString()

                if (textoIngresado == "Quiero eliminar este registro") {
                    eliminarRegistro(idRegistro)
                    Toast.makeText(context, "Registro eliminado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "El texto ingresado no es correcto", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}