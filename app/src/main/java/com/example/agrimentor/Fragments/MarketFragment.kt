package com.example.agrimentor.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrimentor.Adapters.SellProductAdapter
import com.example.agrimentor.R
import com.example.agrimentor.databinding.FragmentHomeBinding
import com.example.agrimentor.databinding.FragmentMarketBinding
import com.example.agrimentor.model.SellProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MarketFragment : Fragment() {
    lateinit var binding: FragmentMarketBinding
    lateinit var database: DatabaseReference
    lateinit var adapter: SellProductAdapter
    lateinit var sellProductList: ArrayList<SellProductModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("SellProduct")



        binding.sellProductRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.sellProductRecyclerView.setHasFixedSize(true)

        sellProductList = ArrayList()

        adapter = SellProductAdapter(sellProductList)
        binding.sellProductRecyclerView.adapter = adapter


        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sellProductList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(SellProductModel::class.java)
                    if (user != null) {
                        sellProductList.add(user)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })








        return binding.root
    }

}