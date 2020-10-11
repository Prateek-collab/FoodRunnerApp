package com.prateek.foodrunner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.prateek.foodrunner.R

class DisplayActivity : AppCompatActivity() {

    var FullName:String?="User"
    var Email:String?="User"
    var MobileNumber:String?="User"
    var Address:String?="User"
    var Password:String?="Password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        var intents = intent

        FullName=intents.getStringExtra("Name")
        Email=intents.getStringExtra("Email")
        MobileNumber=intents.getStringExtra("MobileNumber")
        Address=intents.getStringExtra("Address")
        Password=intents.getStringExtra("Password")

        val result=findViewById<TextView>(R.id.txtMessage)

        result.text="Name:"+FullName+"\nEmail:"+Email+"\nMobile Number:"+MobileNumber+"\nAddress:"+Address+"\nPassword:"+Password
    }
}