package com.prateek.foodrunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class UserActivity : AppCompatActivity() {

    var userName:String?="User"
    var password:String?="Password"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        userName=intent.getStringExtra("Name")
        password=intent.getStringExtra("Password")

        val result=findViewById<TextView>(R.id.txtResult)

        result.text="Name:"+userName+"\nPassword:"+password
    }
}