package com.example.appshopper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Configurando o botão para cadastrar um novo usuário

        //vamos fazer o primeiro commit

        register_button_register.setOnClickListener {

        }

        //Configurando o Listener para realizar o login
        already_have_accout_textview.setOnClickListener {

                Log.d("RegisterActivity", "Try do show login activity ")

                val intent = Intent(this, LoginActivity::class.java )
                startActivity(intent)

        }
        selectphoto_button_register.setOnClickListener {

        }

    }

    private fun performRegister(){

        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()
        val username = username_edittext_register.text.toString()
        val dateofbirth = dateofbirth_edittext_register.text.toString()

        ///Validando todos os campos para prosseguir no cadastro do novo usuário no  banco
        if(email.isEmpty() || password.isEmpty() || username.isEmpty() || dateofbirth.isEmpty()){
            Toast.makeText(this, "Por favor, preencha todos os campos para concluir seu cadastro!", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password )
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener }
                //else if successful
                Log.d(
                    "Main",
                    "Successfully created user with uid  ${it.result!!.user!!.uid}")
            }
            //Tratando as Exceções
            .addOnFailureListener {
                Log.d("Main", "Failed to create user ${it.message}")
                Toast.makeText( this, "Erro ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
