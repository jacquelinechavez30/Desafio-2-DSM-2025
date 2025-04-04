package com.example.registroescolar.Registros

data class Registros_estudiantes(
    var id: String = "",  // <-- Agregar campo para el ID
    var nombre: String = "",
    var apellido: String = "",
    var grado: String = "",
    var materia: String = "",
    var notaFinal: Double = 0.0,
    var userId: String? = null
) {
    // Constructor
    constructor() : this("","", "", "", "", 0.0, null)

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "nombre" to nombre,
            "apellido" to apellido,
            "grado" to grado,
            "materia" to materia,
            "notaFinal" to notaFinal,
            "userId" to userId
        )
    }
}