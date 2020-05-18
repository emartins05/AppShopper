package com.example.appshopper.registration

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appshopper.LatestItensActivity
import com.example.appshopper.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity: AppCompatActivity(){

    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        //Log.d("Login", "***Attempt to login with email/pw: ${auth}/***")
        loggin_button_login.setOnClickListener {
           val email = email_eddittext_login.text.toString()
          val password = password_eddittext_login.text.toString()


            //Procedendo para o Login do usuário:

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_LONG).show()}
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d("login","*************************************************Loggin Successfull")
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this,
                            LatestItensActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                    }else
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                })

            }



        }

        back_to_register_textview.setOnClickListener {
            finish()
        }


    }


}