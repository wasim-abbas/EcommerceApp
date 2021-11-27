package com.example.ecommerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.ecommerceapp.Adapter.*
import com.example.ecommerceapp.Model.AllProductModel
import com.example.ecommerceapp.Model.CateroryModelClass
import com.example.ecommerceapp.Model.ElectronicsModel
import com.example.ecommerceapp.Model.JeweleryModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ItemClicked {

    var mAdapter = HorizontalAdapter(this)
    val allProductAdapter = AllProductAdapter(this)
    val jeweleyAdapter = JeweleryAdapter(this)
    val electronicAdapter = ElectronicsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HRecycV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        VRView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        init()
    }

    fun init() {
        CoroutineScope(Dispatchers.IO).launch { fetchData() }
        CoroutineScope(Dispatchers.IO).launch { fetchAllProduct() }
    }


    fun fetchData() {
        // Instantiate the RequestQueue.
        val myDatalist = ArrayList<CateroryModelClass>()

        val url = "https://fakestoreapi.com/products/categories"
        val jsonObject = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                myDatalist.add(CateroryModelClass("All Category"))

                for (i in 0 until it.length()) {
                    val catJsonObject = it.get(i).toString()
                    myDatalist.add(CateroryModelClass(catJsonObject))
                }
                mAdapter.updateData(myDatalist)
                HRecycV.adapter = mAdapter
                Log.i("pk", "Volly check: ${myDatalist}")
            },
            {
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()

            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonObject)

    }

    fun fetchAllProduct() {
        val allProductArrayList = ArrayList<AllProductModel>()

        val url = "https://fakestoreapi.com/products"
        val jsonArray = JsonArrayRequest(Request.Method.GET, url, null,
            {
                for (i in 0 until it.length()) {
                    val productObject = it.getJSONObject(i)
                    val allproduct = AllProductModel(
                        productObject.getString("title"),
                        productObject.getString("price"),
                        productObject.getString("image")
                    )
                    allProductArrayList.add(allproduct)
                }
                allProductAdapter.updateAllProductList(allProductArrayList)
                VRView.adapter = allProductAdapter
                Log.i("pk", "Volly check: ${allProductArrayList}")
            },
            {
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()
            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonArray)

    }

    fun jewelryData() {
        val jeweleyLsit = java.util.ArrayList<JeweleryModel>()
        val url = "https://fakestoreapi.com/products/category/jewelery"
        val jsonArray = JsonArrayRequest(Request.Method.GET, url, null,
            {
                for (i in 0 until it.length()) {
                    val productObject = it.getJSONObject(i)
                    val allproduct = JeweleryModel(
                        productObject.getString("title"),
                        productObject.getString("price"),
                        productObject.getString("image")
                    )
                    jeweleyLsit.add(allproduct)
                }
                jeweleyAdapter.updarteJeweleyList(jeweleyLsit)
                VRView.adapter = jeweleyAdapter
                Log.i("pk", "Volly check: ${jeweleyLsit}")
            },
            {
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()
            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonArray)
    }

    fun electronicData(){
        val elecricList= ArrayList<ElectronicsModel>()
        val url = "https://fakestoreapi.com/products/category/electronics"
        val jsonArray = JsonArrayRequest(Request.Method.GET, url, null,
            {
                for (i in 0 until it.length()) {
                    val productObject = it.getJSONObject(i)
                    val allproduct = ElectronicsModel(
                        productObject.getString("title"),
                        productObject.getString("price"),
                        productObject.getString("image")
                    )
                    elecricList.add(allproduct)
                }
                electronicAdapter.updateElectronicList(elecricList)
                VRView.adapter = electronicAdapter
                Log.i("pk", "Volly check: ${elecricList}")
            },
            {
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()
            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonArray)

    }

    override fun itemClcikedLisyener(item: CateroryModelClass) {

        when (item.electronics) {
            "All Category" -> CoroutineScope(Dispatchers.IO).launch { fetchAllProduct() }
            "jewelery" -> CoroutineScope(Dispatchers.IO).launch { jewelryData() }
            "electronics" -> CoroutineScope(Dispatchers.IO).launch { electronicData() }
        }
    }

}