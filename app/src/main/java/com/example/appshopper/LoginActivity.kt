package com.example.appshopper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loggin_button_login.setOnClickListener {
            val email = email_eddittext_login.text.toString()
            val password = password_edittext_register.text.toString()

            Log.d("Login", "***Attempt to login with email/pw: $email/***")
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }


    }


}