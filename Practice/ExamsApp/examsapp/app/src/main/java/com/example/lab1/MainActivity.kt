package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment4))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment4)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}