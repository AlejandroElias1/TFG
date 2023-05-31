package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMain5Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity5 : AppCompatActivity() {
    private lateinit var binding: ActivityMain5Binding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val correo = intent.getStringExtra("correo")
        firestore = FirebaseFirestore.getInstance()

        binding.flecha.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        setup(correo!!)
    }

    private fun setup(correo: String) {
        binding.button4.setOnClickListener {
            if (binding.pass1.text!!.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(correo, binding.pass1.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            verificarPerfil(correo)
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun verificarPerfil(correo: String) {
        val perfilRef = firestore.collection("perfilesUsuarios")
            .whereEqualTo("correo", correo)

        perfilRef.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val intent = android.content.Intent(this, MainActivity7::class.java)
                startActivity(intent)
                finish() // Finaliza el MainActivity5 para que el usuario no pueda volver atrás
            } else {
                val intent = android.content.Intent(this, MainActivity6::class.java)
                startActivity(intent)
                finish() // Finaliza el MainActivity5 para que el usuario no pueda volver atrás
            }
        }.addOnFailureListener { exception ->
            // Manejo de errores
            showAlert()
        }
    }

    private fun showAlert() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: android.app.AlertDialog = builder.create()
        dialog.show()
    }
}

