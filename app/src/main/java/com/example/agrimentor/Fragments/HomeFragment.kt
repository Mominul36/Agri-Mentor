package com.example.agrimentor.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agrimentor.Activity.CultivationPotatoActivity
import com.example.agrimentor.Activity.CultivationRiceActivity
import com.example.agrimentor.Activity.CultivationTomatoActivity
import com.example.agrimentor.R
import com.example.agrimentor.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        binding.rice.setOnClickListener{
            startActivity(Intent(requireContext(),CultivationRiceActivity::class.java))

        }


        binding.tomato.setOnClickListener{
            startActivity(Intent(requireContext(), CultivationTomatoActivity::class.java))

        }

        binding.potato.setOnClickListener{
            startActivity(Intent(requireContext(), CultivationPotatoActivity::class.java))

        }











        return binding.root
    }


}