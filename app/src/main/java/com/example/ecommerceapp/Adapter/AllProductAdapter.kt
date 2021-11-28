package com.example.ecommerceapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.ItemClicked
import com.example.ecommerceapp.Model.AllProductModel
import com.example.ecommerceapp.R

class AllProductAdapter(val listener: ItemClicked) : RecyclerView.Adapter<AllProductAdapter.AllProductViewHolder>() {

    val allproductList: ArrayList<AllProductModel> = ArrayList()

    inner class AllProductViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val img = item.findViewById<ImageView>(R.id.imgView)
        val tvPrice = item.findViewById<TextView>(R.id.tvPrice)
        val tvitemName = item.findViewById<TextView>(R.id.TvitemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vertical_item_view, parent, false)
       val pos = AllProductViewHolder(v)
        v.setOnClickListener{
            listener.singleitemcClicked(allproductList[pos.adapterPosition])
        }
        return pos
    }

    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) {
      val currentItem = allproductList[position]
      holder.tvPrice.text= "$${currentItem.price}"
        holder.tvitemName.text = currentItem.title
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.img)
    }
    fun updateAllProductList(allProductModel: ArrayList<AllProductModel>)
    {
        allproductList.clear()
        allproductList.addAll(allProductModel)
        notifyDataSetChanged()
    }

    override fun getItemCount()= allproductList.size
}