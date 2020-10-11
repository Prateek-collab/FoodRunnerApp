package com.prateek.foodrunner.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.prateek.foodrunner.R

class LoginActivity : AppCompatActivity(){

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var txtForgotPassword: TextView
    lateinit var txtRegister: TextView
    val validUserName="prateekranjan14@gmail.com"
    val validPassword="1234"

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        val isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",false)

        setContentView(R.layout.activity_login)

        if (isLoggedIn) {
            val intent = Intent(this@LoginActivity, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
        etUsername=findViewById(R.id.etUsername)
        etPassword=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnLogin)
        txtForgotPassword=findViewById(R.id.txtForgotPassword)
        txtRegister=findViewById(R.id.txtRegister)

        btnLogin.setOnClickListener {

            val userName=etUsername.text.toString()

            val password=etPassword.text.toString()

            if((userName==validUserName)&&(password==validPassword)){

                val intent= Intent(this@LoginActivity,
                    UserActivity::class.java)
                savePreferences()
                startActivity(intent)
            } else{
                Toast.makeText(
                    this@LoginActivity,
                    "Incorrect Credentials",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }

        txtForgotPassword.setOnClickListener {
            val intent= Intent(this@LoginActivity,
                ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        txtRegister.setOnClickListener {
            val intent= Intent(this@LoginActivity,
                RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    fun savePreferences(){
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
    }
}