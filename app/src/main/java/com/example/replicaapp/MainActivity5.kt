package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMain5Binding

class MainActivity5 : AppCompatActivity() {
    lateinit var binding: ActivityMain5Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flecha.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.button4.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity6::class.java)
            startActivity(intent)
        }

    }
}