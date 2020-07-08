package com.prateek.foodrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity(){

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var txtForgotPassword: TextView
    lateinit var txtRegister: TextView
    val validUserName="prateekranjan14@gmail.com"
    val validPassword="1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername=findViewById(R.id.etUsername)
        etPassword=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnLogin)
        txtForgotPassword=findViewById(R.id.txtForgotPassword)
        txtRegister=findViewById(R.id.txtRegister)

        btnLogin.setOnClickListener {

            val userName=etUsername.text.toString()

            val password=etPassword.text.toString()

            var nameOfUser="None"
            var passwordOfUser="xyz"

            if((userName==validUserName)&&(password==validPassword)){
                nameOfUser=userName
                passwordOfUser=password
                val intent= Intent(this@LoginActivity,UserActivity::class.java)
                startActivity(intent)
                intent.putExtra("Name",nameOfUser)
                intent.putExtra("Password",passwordOfUser)
                finish()
            }else{
                Toast.makeText(
                    this@LoginActivity,
                    "Incorrect Credentials",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }

        txtForgotPassword.setOnClickListener {
            val intent= Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        txtRegister.setOnClickListener {
            val intent= Intent(this@LoginActivity,RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}