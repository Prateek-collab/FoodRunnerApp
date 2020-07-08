package com.prateek.foodrunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayActivity : AppCompatActivity() {

    var FullName:String?="User"
    var Email:String?="User"
    var MobileNumber:String?="User"
    var Address:String?="User"
    var Password:String?="Password"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        FullName=intent.getStringExtra("Name")
        Email=intent.getStringExtra("Name")
        MobileNumber=intent.getStringExtra("Name")
        Address=intent.getStringExtra("Name")
        Password=intent.getStringExtra("Password")

        val result=findViewById<TextView>(R.id.txtmessage)

        result.text="Name:"+FullName+"\nEmail:"+Email+"\nMobile Number:"+MobileNumber+"\nAddress:"+Address+"\nPassword:"+Password
    }
}