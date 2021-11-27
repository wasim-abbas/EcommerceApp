package com.example.ecommerceapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.Model.ElectronicsModel
import com.example.ecommerceapp.R
import java.util.ArrayList

class ElectronicsAdapter(context: Context) :
    RecyclerView.Adapter<ElectronicsAdapter.ElectronicViewHolder>() {
    val electonicList = ArrayList<ElectronicsModel>()

    inner class ElectronicViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val electricTitle = item.findViewById<TextView>(R.id.electronicsTvtitle)
        val electronicPrice = item.findViewById<TextView>(R.id.electronicstvDollar)
        val electImage = item.findViewById<ImageView>(R.id.electronicimageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectronicViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.electronic_item_view, parent, false)
        return ElectronicViewHolder(v)
    }

    override fun onBindViewHolder(holder: ElectronicViewHolder, position: Int) {
        val currentItem = electonicList[position]
        holder.electricTitle.text = currentItem.title
        holder.electronicPrice.text = currentItem.price
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.electImage)
    }

    fun updateElectronicList(electriclist: ArrayList<ElectronicsModel>) {
        electonicList.clear()
        electonicList.addAll(electriclist)
        notifyDataSetChanged()
    }

    override fun getItemCount() = electonicList.size
}