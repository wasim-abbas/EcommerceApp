package com.example.ecommerceapp

import com.example.ecommerceapp.Adapter.HorizontalAdapter
import com.example.ecommerceapp.Model.AllProductModel
import com.example.ecommerceapp.Model.CateroryModelClass

interface ItemClicked {
    fun itemClcikedLisyener(item: CateroryModelClass,position: HorizontalAdapter.Category)
    fun singleitemcClicked(singleItem: AllProductModel)
}