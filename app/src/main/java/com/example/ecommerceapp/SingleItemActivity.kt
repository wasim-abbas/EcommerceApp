package com.example.ecommerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_single_item.*
import java.lang.NumberFormatException

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
        try {
            if (rate != null) {
                val floor = Math.floor(rate.toDouble())
                Log.d("ok", "floorvalue: $floor")
                ratingBar.rating = floor.toFloat()
            }
        }catch (e: NumberFormatException)
        {
            e.printStackTrace()
        }

        }
    }
