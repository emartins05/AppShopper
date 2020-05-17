package com.example.appshopper

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Configurando o botão para cadastrar um novo usuário

        //vamos fazer o primeiro commit

        register_button_register.setOnClickListener {

            Log.d("RegisterActivity", "Try do show login activity ")
            performRegister()
        }

        //Configurando o Listener para realizar o login
        already_have_accout_textview.setOnClickListener {

            Log.d("RegisterActivity", "Try do show login activity ")

                val intent = Intent(this, LoginActivity::class.java )
                startActivity(intent)

        }
        selectphoto_button_register.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type= "image/*"
            startActivityForResult(intent, 0)
        }
    }
    var selectedPhotoUri:Uri?= null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == 0 && resultCode == Activity.RESULT_OK && data !== null){
            //Verificando qual foi a imagem selecionada

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)
            selectphoto_button_register.alpha = 0f
          //  val bitmapDrawable = BitmapDrawable(bitmap)
           // selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun performRegister(){

        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()
        val username = username_edittext_register.text.toString()
        val dateofbirth = dateofbirth_edittext_register.text.toString()

        ///Validando todos os campos para prosseguir no cadastro do novo usuário no  banco
        if(selectedPhotoUri == null){
            Toast.makeText(this, "Por favor, insira uma foto para fazer o cadastro!", Toast.LENGTH_SHORT).show()
            return
        }

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
                    "RegisterActivity",
                    "Successfully created user with uid  ${it.result!!.user!!.uid}")
                uploadImageToFirebaseStorage()
            }

            //Tratando as Exceções
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failed to create user ${it.message}")
                Toast.makeText( this, "Erro ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage(){
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        if(selectedPhotoUri == null ) return

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File Location: $it")
                    saveUserToFirebaseDataBase(it.toString())

                }
            }
            .addOnFailureListener {
                //do some handler excepetion here
            }

    }

    private fun saveUserToFirebaseDataBase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User( uid, username_edittext_register.text.toString(),dateofbirth_edittext_register.text.toString(),password_edittext_register.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Finally we save the user on Firebase Database")

                val intent = Intent(this,LatestItensActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
    }


}
class User(val uid:String, val username: String, val dateofbirth:String, val password: String, val profileImageUrl: String)
