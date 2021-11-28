package com.example.ecommerceapp

import android.content.Intent
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
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ItemClicked {
    companion object {
        const val urlproducts = "https://fakestoreapi.com/products"
    }

    var mAdapter = HorizontalAdapter(this)
    val allProductAdapter = AllProductAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HRecycV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        VRView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        horizontalrecycviewData()
        verticalRecycviewData(urlproducts)
    }

    fun horizontalrecycviewData() {
        // Instantiate the RequestQueue.
        val myDatalist = ArrayList<CateroryModelClass>()

        val url = "https://fakestoreapi.com/products/categories"
        val jsonObject = JsonArrayRequest(
            Request.Method.GET, url, null,
            {

                if (it == null) {
                    Toast.makeText(this, "No Data Found ", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Connection Time out", Toast.LENGTH_SHORT).show()
                } else {
                    myDatalist.add(CateroryModelClass("All Category"))

                    for (i in 0 until it.length()) {
                        val catJsonObject = it.get(i).toString()
                        myDatalist.add(CateroryModelClass(catJsonObject))
                    }
                    mAdapter.updateData(myDatalist)
                    HRecycV.adapter = mAdapter
                    Log.i("pk", "Volly check: ${myDatalist}")
                }
            },
            {
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()

            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonObject)

    }

    fun verticalRecycviewData(url: String) {
        val hud = KProgressHUD.create(this@MainActivity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setMaxProgress(100)
            .show()
        hud.setProgress(90)

        val allProductArrayList = ArrayList<AllProductModel>()
        val jsonArray = JsonArrayRequest(Request.Method.GET, url, null,
            {
                if (it == null) {
                    Toast.makeText(this, "No Data Found ", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Connection Time out", Toast.LENGTH_SHORT).show()
                } else {
                    for (i in 0 until it.length()) {
                        val productObject = it.getJSONObject(i)
                        val allproduct = AllProductModel(
                            productObject.getLong("id"),
                            productObject.getString("title"),
                            productObject.getString("price"),
                            productObject.getString("description"),
                            productObject.getString("category"),
                            productObject.getString("image"),
                            productObject.getString("rating")
                        )
                        allProductArrayList.add(allproduct)
                        allProductAdapter.updateAllProductList(allProductArrayList)
                        VRView.adapter = allProductAdapter
                        hud.dismiss()
                        Log.i("pk", "Volly check: ${allProductArrayList}")
                    }
                }
            },
            {
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()
            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonArray)

    }

    override fun itemClcikedLisyener(item: CateroryModelClass) {


        when (item.category) {
            "All Category" -> verticalRecycviewData(urlproducts)
            "jewelery" -> verticalRecycviewData(urlproducts + "/category/jewelery")
            "electronics" -> verticalRecycviewData(urlproducts + "/category/electronics")
            "men's clothing" -> verticalRecycviewData(urlproducts + "/category/men's clothing")
            "women's clothing" -> verticalRecycviewData(urlproducts + "/category/women's clothing")
        }
    }

    override fun singleitemcClicked(singleItem: AllProductModel) {
        val rate=singleItem.rating
        val subStr= rate.substring(8,11)
        val intent = Intent(this, SingleItemActivity::class.java)
        intent.putExtra("price",singleItem.price)
        intent.putExtra("title", singleItem.title)
        intent.putExtra("description", singleItem.description)
        intent.putExtra("cat", singleItem.category)
        intent.putExtra("img", singleItem.image)
        intent.putExtra("rate",subStr)
        startActivity(intent)
        Log.d("ok","$subStr")
    }

}
