package com.example.appshopper.registration

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast

import com.example.appshopper.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_change_data_reg.*
import java.util.*

class ChangeDataReg : AppCompatActivity() {

    companion object{
        private var URLimg: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title="Alterar Cadastro"
        setContentView(R.layout.activity_change_data_reg)
        button2_update.setOnClickListener {
            Log.d("RegisterActivity", "Try do show login activity ")
            performUpdate()
        }
        selectphoto_button_register_change_data.setOnClickListener {
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
            selectphoto_imageview_update.setImageBitmap(bitmap)
           selectphoto_button_register_change_data.alpha = 0f

        }
    }

    private fun performUpdate(){
        val email =editText9_email_box.text.toString()
        val password = editText10_password_box.text.toString()
        val username = editText5_name_box.text.toString()
        val dateofbirth = editText11_Data_box.text.toString()
        ///Validando todos os campos para prosseguir no cadastro do novo usuário no  banco
        if(email.isEmpty() || password.isEmpty() || username.isEmpty() || dateofbirth.isEmpty()){
            Toast.makeText(this, "Por favor, preencha todos os campos para concluir seu cadastro!", Toast.LENGTH_SHORT).show()
            return
        }


        uploadImageToFirebaseStorage()


        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val usertoUpdateFireBase = UserUpdate(
            uid,
            username,
            dateofbirth,
            email,
            password,
            URLimg
        )

        ref.setValue(usertoUpdateFireBase)
            .addOnSuccessListener {
                Toast.makeText(this, "Seus Dados Foram Atualizados !!", Toast.LENGTH_SHORT).show()
                Log.d("RegisterActivity", "Finally we updated the user on Firebase Database")}

      /*  user!!.updateEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful){
                println("Update Sucess")
                Toast.makeText(this, "Update Realizado", Toast.LENGTH_SHORT).show()}
            else
                Toast.makeText(this, "Erro no Update", Toast.LENGTH_SHORT).show()
            }*/



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

                    URLimg = (it.toString())

                }
            }
            .addOnFailureListener {
                //do some handler excepetion here
            }
    }

}

@Parcelize
class UserUpdate(val uid:String, val username: String, val dateofbirth:String, val email: String, val password: String, val profileImageUrl: String) :
    Parcelable {

    constructor():this("","","","","","")
}
