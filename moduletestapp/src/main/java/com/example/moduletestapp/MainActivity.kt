package com.example.moduletestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhijith.feature_auth.presentation.component.screen.LoginScreen
import com.example.moduletestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.apply {
            beginTransaction().apply {
                replace(binding.fragmeLayout.id, LoginScreen())
                commitNow()
            }
        }
    }
}