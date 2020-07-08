package com.prateek.foodrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var etEmail2: EditText
    lateinit var etMobileNumber2: EditText
    lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etEmail2=findViewById(R.id.etEmail2)
        etMobileNumber2=findViewById(R.id.etMobileNumber2)

        btnNext=findViewById(R.id.btnNext)

        btnNext.setOnClickListener {

            val emailId=etEmail2.text.toString()

            val mobileNumber=etMobileNumber2.text.toString()

            val intent= Intent(this@ForgotPasswordActivity,Display2Activity::class.java)
            startActivity(intent)
            intent.putExtra("Email",emailId)
            intent.putExtra("Number",mobileNumber)
            finish()
        }
    }
}