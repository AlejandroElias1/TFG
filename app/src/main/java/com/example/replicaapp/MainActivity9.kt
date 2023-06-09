package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMain9Binding

class MainActivity9 : AppCompatActivity() {
    lateinit var binding: ActivityMain9Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)
        binding = ActivityMain9Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView9.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }
    }
}