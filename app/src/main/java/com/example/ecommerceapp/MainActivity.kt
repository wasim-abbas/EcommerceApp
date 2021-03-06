package com.example.ecommerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.ecommerceapp.Adapter.*
import com.example.ecommerceapp.Model.AllProductModel
import com.example.ecommerceapp.Model.CateroryModelClass
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.horizon_carview_layout.*
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
                    alertDiloge()
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
                alertDilogeNoInternet()
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "No Data Found ", Toast.LENGTH_SHORT).show()

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
                    alertDiloge()
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

                        hud.dismiss()
                        Log.i("pk", "Volly check: ${allProductArrayList}")
                    }
                }
            },
            {

                alertDilogeNoInternet()
                hud.dismiss()
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()
            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonArray)
        VRView.adapter = allProductAdapter


    }

    override fun itemClcikedLisyener(
        item: CateroryModelClass,
        position: HorizontalAdapter.Category
    ) {


        when (item.category) {
            "All Category" -> verticalRecycviewData(urlproducts)
            "jewelery" -> verticalRecycviewData(urlproducts + "/category/jewelery")
            "electronics" -> verticalRecycviewData(urlproducts + "/category/electronics")
            "men's clothing" -> verticalRecycviewData(urlproducts + "/category/men's clothing")
            "women's clothing" -> verticalRecycviewData(urlproducts + "/category/women's clothing")
        }
    }


    override fun singleitemcClicked(singleItem: AllProductModel) {
        val rate = singleItem.rating
        val subStr = rate.substring(8, 11)
        val intent = Intent(this, SingleItemActivity::class.java)
        intent.putExtra("price", singleItem.price)
        intent.putExtra("title", singleItem.title)
        intent.putExtra("description", singleItem.description)
        intent.putExtra("cat", singleItem.category)
        intent.putExtra("img", singleItem.image)
        intent.putExtra("rate", subStr)
        startActivity(intent)
        Log.d("ok", "$subStr")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val infater: MenuInflater = menuInflater
        infater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainFilter -> verticalRecycviewData(urlproducts + "?sort=desc")
            R.id.menu5 -> verticalRecycviewData(urlproducts + "?limit=5")
            R.id.menu10 -> verticalRecycviewData(urlproducts + "?limit=10")
            R.id.menu15 -> verticalRecycviewData(urlproducts + "?limit=15")
            R.id.menuAll -> verticalRecycviewData(urlproducts)
        }
        return super.onOptionsItemSelected(item)
    }

    fun alertDilogeNoInternet() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Error")
        alertDialog.setMessage("No Internet connection")
        alertDialog.setPositiveButton("OK") { di, which ->
            di.cancel()
        }
        alertDialog.setCancelable(true)
        alertDialog.create()
        alertDialog.show()

    }

    fun alertDiloge() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("No Data Found")
        alertDialog.setMessage("Connection time out")
        alertDialog.setPositiveButton("OK") { di, which ->
            di.cancel()
        }
        alertDialog.setCancelable(true)
        alertDialog.create()
        alertDialog.show()

    }
}
