package com.example.ecommerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_single_item.*

class SingleItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_item)
        getIntentData()
    }

    private fun getIntentData() {
        tvCategory.text = intent.getStringExtra("cat")
        val img = intent.getStringExtra("img")
        Glide.with(this).load(img).into(imgView)
        val price = intent.getStringExtra("price")
        tvPrice.text = "$$price"
        TvitemName.text = intent.getStringExtra("title")
        tvItemDEscription.text = intent.getStringExtra("description")
        val rate = intent.getStringExtra("rate")
        if (rate != null) {
            ratingBar.rating= rate.toFloat()
        }
    }
}