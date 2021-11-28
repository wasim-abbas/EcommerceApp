package com.example.ecommerceapp

import com.example.ecommerceapp.Model.AllProductModel
import com.example.ecommerceapp.Model.CateroryModelClass

interface ItemClicked {
    fun itemClcikedLisyener(item: CateroryModelClass)
    fun singleitemcClicked(singleItem: AllProductModel)
}