package com.example.agrimentor.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrimentor.R
import com.example.agrimentor.model.SellProductModel


class SellProductAdapter(
    private val sellProductList: List<SellProductModel>
) : RecyclerView.Adapter<SellProductAdapter.SellProductViewHolder>() {




    class SellProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val addButton: ImageView = itemView.findViewById(R.id.addButton)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_market_product, parent, false)
        return SellProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SellProductViewHolder, position: Int) {
        val currentSellProduct = sellProductList[position]

        holder.productName.text = currentSellProduct.productName
        holder.productPrice.text = "৳"+ currentSellProduct.rate + " / "+ currentSellProduct.unit;

        var name : String = currentSellProduct.productName.toString()

        if(name=="Carrot"){
            holder.productImage.setImageResource(R.drawable.carrotsell)
        }
        else if(name=="Potato"){
            holder.productImage.setImageResource(R.drawable.potatosell)
        }
        else if(name=="Tomato"){
            holder.productImage.setImageResource(R.drawable.tomatosell)
        }
        else if(name=="Onion"){
            holder.productImage.setImageResource(R.drawable.onionsell)
        }





        holder.addButton.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return sellProductList.size
    }





}