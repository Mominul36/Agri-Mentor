package com.example.agrimentor.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agrimentor.R
import com.example.agrimentor.databinding.ActivityCultivationRiceBinding
import com.example.agrimentor.databinding.FragmentHomeBinding

class CultivationRiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityCultivationRiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =   ActivityCultivationRiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbutton.setOnClickListener{
            finish()
        }





    }
}