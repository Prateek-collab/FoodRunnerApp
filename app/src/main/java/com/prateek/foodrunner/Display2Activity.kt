package com.prateek.foodrunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Display2Activity : AppCompatActivity() {

    var Email:String?="User"
    var MobileNumber:String?="User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display2)

        Email=intent.getStringExtra("Email")
        MobileNumber=intent.getStringExtra("Number")

        val display=findViewById<TextView>(R.id.txtmessage)

        display.text="Email:"+Email+"\nMobile Number:"+MobileNumber
    }
}