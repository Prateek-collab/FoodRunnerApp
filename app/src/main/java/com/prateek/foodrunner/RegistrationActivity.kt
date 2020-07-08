package com.prateek.foodrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnRegister=findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener{
            val fullName=etFullName.text.toString()
            val email=etEmail2.text.toString()
            val mobileNumber=etMobileNumber2.text.toString()
            val address=etAddress.text.toString()
            val password=etPassword.text.toString()

            val intent= Intent(this@RegistrationActivity,DisplayActivity::class.java)
            startActivity(intent)
            intent.putExtra("Name",fullName)
            intent.putExtra("Email",email)
            intent.putExtra("MobileNumber",mobileNumber)
            intent.putExtra("Address",address)
            intent.putExtra("Password",password)
            finish()
        }


    }
}