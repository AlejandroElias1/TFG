package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMain4Binding

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
    }




}