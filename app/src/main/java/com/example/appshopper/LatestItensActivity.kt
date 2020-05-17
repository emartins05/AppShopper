package com.example.appshopper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

class LatestItensActivity : AppCompatActivity() {
    var userIsLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_itens)
        verifyUserIsLoggedIn()

        //Se o usuário estiver logado, vamos consumir os dados da API
        if(userIsLoggedIn){

            //method that sends a GET request to a Publuic API: https://jsonplaceholder.typicode.com/users/
            gettingApiDatas()
        }

    }

    //Verificando se o usuário está logado
    private fun verifyUserIsLoggedIn(){

        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){ //se o usuário não estiver logado, inicializar o app com a tela de registro
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }else userIsLoggedIn=true //se o usuário estiver logado

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.favoriteds -> {
            val intent  = Intent(this, FavoritedActivity::class.java)
                startActivity(intent)
            }
           R.id.menu_sign_out ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)


        return super.onCreateOptionsMenu(menu)
    }

    private fun gettingApiDatas(){
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(ApiService::class.java)
        api.fetchAllUsers().enqueue(object : Callback<List<Users>>{
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {

               for (i in 0..9){
                Log.d("jsontest"," Resposta do JSON ${response.body()!![i].email}")}

            }
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.d("jsontest"," OnFailure ")

            }

        })

    }




}
