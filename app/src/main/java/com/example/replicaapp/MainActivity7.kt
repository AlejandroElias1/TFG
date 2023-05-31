package com.example.replicaapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.replicaapp.databinding.ActivityMain7Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity7 : AppCompatActivity() {

    private lateinit var textViewProfileName: TextView
    lateinit var binding: ActivityMain7Binding
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 2

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Inicializar Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Obtener referencias de los elementos de la interfaz de usuario
        textViewProfileName = findViewById(R.id.textViewProfileName)

        // Verificar si el permiso de la cámara ya está concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // El permiso de la cámara ya está concedido, puedes acceder a la cámara aquí
            setupCameraButton()
        } else {
            // El permiso de la cámara no está concedido, debes solicitarlo al usuario
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }

        // Obtener el correo electrónico del usuario actualmente logueado
        val userEmail = auth.currentUser?.email

        // Obtener el nombre del perfil desde Firestore
        if (userEmail != null) {
            obtenerNombrePerfil(userEmail)
        }

        // Botón de CALL
        binding.imageViewCall.setOnClickListener {
            val intent = Intent()
            intent.data = Uri.parse("tel:")
            val intentChooser = Intent.createChooser(intent, "Llamada")
            startActivity(intentChooser)
        }

        // Boton de CHAT
        binding.imageViewChat.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }

        // Boton de INFO
        binding.imageViewInfo.setOnClickListener {
            val intent = Intent(this, MainActivity9::class.java)
            startActivity(intent)
        }
    }

    private fun obtenerNombrePerfil(userEmail: String) {
        // Realizar consulta a Firestore para obtener el nombre del perfil basado en el correo electrónico
        firestore.collection("perfilesUsuarios")
            .whereEqualTo("correo", userEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val nombrePerfil = document.getString("nombre")
                    textViewProfileName.text = nombrePerfil
                } else {
                    // Si no se encuentra un perfil para el correo electrónico, mostrar mensaje o realizar acción adicional
                    mostrarMensaje("No se encontró un perfil asociado al correo electrónico.")
                }
            }
            .addOnFailureListener { e ->
                // Error al obtener el nombre del perfil
                mostrarMensaje("Error al obtener el nombre del perfil: ${e.message}")
            }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun setupCameraButton() {
        // Configurar el OnClickListener para el botón de la cámara
        binding.imageViewCam.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Aquí puedes realizar acciones con la imagen capturada
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El usuario ha concedido el permiso de la cámara, puedes acceder a la cámara aquí
                setupCameraButton()
            } else {
                // El usuario ha denegado el permiso de la cámara, muestra un mensaje o toma alguna acción adicional
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
