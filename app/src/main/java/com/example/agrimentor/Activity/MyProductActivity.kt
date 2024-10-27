package com.example.agrimentor.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.agrimentor.Fragments.SellActiveOrderFragment
import com.example.agrimentor.Fragments.SellPreviousOrderFragment
import com.example.agrimentor.Fragments.SellProductFragment
import com.example.agrimentor.R
import com.example.agrimentor.databinding.ActivityMyProductBinding

class MyProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyProductBinding
    var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setFragment(SellProductFragment())
        setAllButtonDefault()
        setColorButtonBackground(binding.txtproduct)

        binding.txtproduct.setOnClickListener{
            setAllButtonDefault()
            setColorButtonBackground(binding.txtproduct)
            setFragment(SellProductFragment())

        }
        binding.txtactiveorder.setOnClickListener{
            setAllButtonDefault()
            setColorButtonBackground(binding.txtactiveorder)
            setFragment(SellActiveOrderFragment())
        }

        binding.txtpreviousorder.setOnClickListener{
            setAllButtonDefault()
            setColorButtonBackground(binding.txtpreviousorder)
            setFragment(SellPreviousOrderFragment())
        }



        binding.backbutton.setOnClickListener{
            finish()
        }


    }

    @SuppressLint("ResourceAsColor")
    private fun setColorButtonBackground(txtview: TextView) {
        txtview.setBackgroundResource(R.drawable.change_button_background2)
        txtview.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    @SuppressLint("ResourceAsColor")
    private fun setAllButtonDefault() {
        binding.txtproduct.setBackgroundResource(R.drawable.change_button_background)
        binding.txtactiveorder.setBackgroundResource(R.drawable.change_button_background)
        binding.txtpreviousorder.setBackgroundResource(R.drawable.change_button_background)

        binding.txtproduct.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.txtactiveorder.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.txtpreviousorder.setTextColor(ContextCompat.getColor(this, R.color.black))

    }


    fun setFragment(fragment: Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager
        val frammentTransition : FragmentTransaction = fragmentManager.beginTransaction()

        if(!flag){
            frammentTransition.add(R.id.frame,fragment)
            flag = true
        }
        else{
            frammentTransition.replace(R.id.frame,fragment)
        }
        frammentTransition.addToBackStack(null)
        frammentTransition.commit()
    }
}