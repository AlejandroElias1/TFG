package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMain5Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity5 : AppCompatActivity() {
    lateinit var binding: ActivityMain5Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val correo = intent.getStringExtra("correo")



        binding.flecha.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        setup(correo!!)


    }
    private fun setup(correo:String){
        binding.button4.setOnClickListener {
            if(binding.pass1.text!!.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(correo, binding.pass1.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = android.content.Intent(this, MainActivity6::class.java)
                            startActivity(intent)
                        }else{
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