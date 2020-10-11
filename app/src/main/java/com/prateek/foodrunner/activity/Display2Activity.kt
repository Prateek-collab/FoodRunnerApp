package com.prateek.foodrunner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.prateek.foodrunner.R

class Display2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display2)

        val Email=intent.getStringExtra("Email")
        val MobileNumber=intent.getStringExtra("Number")

        val display=findViewById<TextView>(R.id.txtMessage2)

        display.text="Email:"+Email+"\nMobile Number:"+MobileNumber
    }
}