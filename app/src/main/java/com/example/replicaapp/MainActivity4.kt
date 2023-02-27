package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMain4Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity4 : AppCompatActivity() {
    lateinit var binding: ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val correoInt = intent.getStringExtra("correo")
        binding.correoInt.text = correoInt

        binding.flecha.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        setup()

    }

    private fun setup() {
        title = "Autenticacion"
        binding.signUpButton.setOnClickListener {
            if (binding.correoInt.text.isNotEmpty() && binding.pass.text!!.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.correoInt.text.toString(),
                    binding.pass.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = android.content.Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else {
                        showAlert()
                    }
                }
            }
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