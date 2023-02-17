package com.example.replicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.replicaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sus.setOnClickListener() {
            val intent = android.content.Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        binding.iniciarSes.setOnClickListener() {
            val intent = android.content.Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

}