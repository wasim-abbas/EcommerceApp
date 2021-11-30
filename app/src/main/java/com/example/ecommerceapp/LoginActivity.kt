package com.example.ecommerceapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.ecommerceapp.Model.LoginModel
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    val url = "https://fakestoreapi.com/users"
    var userEmail: String? = null
    var userPass: String? = null
    var emial: String? = null
    var pass: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //loginData()
        btnlogin.setOnClickListener(this)
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

    @SuppressLint("WrongConstant")
    fun loginData() {

        val jsonArray = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                if (it == null) {
                    alertDiloge()
                    print("j")
                } else {
                    for (i in 0 until it.length()) {
                        val productObject = it.getJSONObject(i)
                        val allUser = LoginModel(
                            productObject.getString("email"),
                            productObject.getString("password"),
                        )
                        userEmail = allUser.email
                        userPass = allUser.pass

                        if (userEmail != null && userPass != null) {
                            if (userEmail.equals(emial) && userPass.equals(pass)) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
                Toast.makeText(this, "Incorrect Credential", Toast.LENGTH_SHORT).show()
            },
            {
                alertDilogeNoInternet()
                Toast.makeText(this, "Failed $it", Toast.LENGTH_SHORT).show()
            })
        ApiSingleton.getInstance(this).addToRequestQueue(jsonArray)

    }

    override fun onClick(v: View?) {
        emial = edEmail2.text.toString().trim()
        pass = edPassword2.text.toString().trim()
        if (emial.equals("")) {
            edEmail2.error = "Please Enter Email"
        } else if (pass.equals("")) {
            edPassword2.error = "please Enter Password"
        } else {
            val hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show()
            hud.setProgress(90)
            loginData()
            hud.dismiss()
        }

    }
}