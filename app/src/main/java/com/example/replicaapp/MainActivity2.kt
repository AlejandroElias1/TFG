package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flecha.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.suscribir.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }
    }
}