package com.example.ecommerceapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.Model.JeweleryModel
import com.example.ecommerceapp.R

class JeweleryAdapter (context: Context): RecyclerView.Adapter<JeweleryAdapter.JeweleryViewHolder>() {
    val jeweleyList:ArrayList<JeweleryModel> = ArrayList()

    inner class JeweleryViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title = item.findViewById<TextView>(R.id.electronicsTvtitle)
        val dollar = item.findViewById<TextView>(R.id.electronicstvDollar)
        val image = item.findViewById<ImageView>(R.id.electronicimageView)

    }

    fun updarteJeweleyList(jeweley: ArrayList<JeweleryModel>)
    {
        jeweleyList.clear()
        jeweleyList.addAll(jeweley)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JeweleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jeweley_item_view,parent,false)
       return JeweleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: JeweleryViewHolder, position: Int) {
        val currentItem = jeweleyList[position]
        holder.title.text = currentItem.title
        holder.dollar.text = currentItem.price
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.image)

    }

    override fun getItemCount()= jeweleyList.size
}