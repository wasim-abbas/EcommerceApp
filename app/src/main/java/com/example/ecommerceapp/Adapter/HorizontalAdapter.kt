package com.example.ecommerceapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.Adapter.HorizontalAdapter.*
import com.example.ecommerceapp.ItemClicked
import com.example.ecommerceapp.Model.CateroryModelClass
import com.example.ecommerceapp.R

class HorizontalAdapter(private val listener: ItemClicked) : RecyclerView.Adapter<Category>() {
    val data: ArrayList<CateroryModelClass> = ArrayList()

    inner class Category(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDetail = itemView.findViewById<TextView>(R.id.tvDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Category {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizon_carview_layout, parent, false)
        val pos = Category(view)
        view.setOnClickListener {
            listener.itemClcikedLisyener(data[pos.adapterPosition])
        }

        return pos
    }

    override fun onBindViewHolder(holder: Category, position: Int) {
        val currentItem = data[position]
        holder.tvDetail.text = currentItem.category

    }

    fun updateData(updatedItem: ArrayList<CateroryModelClass>) {
        data.clear()
        data.addAll(updatedItem)
        notifyDataSetChanged()

    }

    override fun getItemCount() = data.size
}

//interface ItemClicked {
//    fun itemClcikedLisyener(item: CateroryModelClass)
//    fun singleitemcClicked(sibgleItem: SingleItemModel)
//}