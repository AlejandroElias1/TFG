package com.example.replicaapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity6 : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextBirthDate: EditText
    private lateinit var editTextMedicalHistory: EditText
    private lateinit var buttonCreateProfile: Button

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextName = findViewById(R.id.editTextName)
        editTextBirthDate = findViewById(R.id.editTextBirthDate)
        editTextMedicalHistory = findViewById(R.id.editTextMedicalHistory)
        buttonCreateProfile = findViewById(R.id.buttonCreateProfile)

        // Configurar el clic del botón para guardar el perfil del usuario
        buttonCreateProfile.setOnClickListener {
            guardarPerfilUsuario()
            val intent = android.content.Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }
    }

    private fun guardarPerfilUsuario() {
        // Obtener los valores ingresados por el usuario
        val nombre = editTextName.text.toString()
        val fechaNacimiento = editTextBirthDate.text.toString()
        val historialMedico = editTextMedicalHistory.text.toString()

        // Obtener el correo electrónico del usuario actualmente autenticado
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""

        // Crear un objeto Map con los datos del perfil del usuario
        val perfilUsuario = hashMapOf(
            "nombre" to nombre,
            "fechaNacimiento" to fechaNacimiento,
            "historialMedico" to historialMedico,
            "correo" to email
        )

        // Guardar los datos en Firestore
        firestore.collection("perfilesUsuarios")
            .add(perfilUsuario)
            .addOnSuccessListener { documentReference ->
                // Éxito al guardar los datos
                val documentId = documentReference.id
                mostrarMensaje("Perfil creado con éxito. Documento ID: $documentId")
                limpiarCampos()
            }
            .addOnFailureListener { e ->
                // Error al guardar los datos
                mostrarMensaje("Error al guardar el perfil: ${e.message}")
            }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        editTextName.text.clear()
        editTextBirthDate.text.clear()
        editTextMedicalHistory.text.clear()
    }
}


