package com.example.agrimentor.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agrimentor.Activity.AddProductForSellActivity
import com.example.agrimentor.R
import com.example.agrimentor.databinding.FragmentHomeBinding
import com.example.agrimentor.databinding.FragmentSellProductBinding


class SellProductFragment : Fragment() {
    lateinit var binding: FragmentSellProductBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellProductBinding.inflate(inflater, container, false)





        binding.btnsell.setOnClickListener{
            startActivity(Intent(requireContext(),AddProductForSellActivity::class.java))
        }

        return binding.root
    }


}