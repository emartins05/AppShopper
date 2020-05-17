package com.example.appshopper

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_itens.*
import kotlinx.android.synthetic.main.data_row_latest_itens.view.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

class LatestItensActivity : AppCompatActivity() {

    var userIsLoggedIn = false
   // var arrayListAux = ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_itens)
        verifyUserIsLoggedIn()
        //Se o usuário estiver logado, vamos consumir os dados da API
        if(userIsLoggedIn){
            //method that sends a GET request to a Publuic API: https://jsonplaceholder.typicode.com/users/
           // gettingApiDatas()
        }

       // latest_itens_recycle_view.setBackgroundColor(Color.RED)
 /*       val adapter = GroupAdapter<ViewHolder>()


        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())
      //  adapter.add(UserItem())

      latest_itens_recycle_view.adapter = adapter*/
        fetchUsers()
    }
     private fun fetchUsers(){
         var auxiliar: String
         val adapter = GroupAdapter<ViewHolder>()

         val retrofit = Retrofit.Builder()
             .baseUrl("https://jsonplaceholder.typicode.com/users/")
             //.baseUrl("https://jsonplaceholder.typicode.com/users/")
             .addConverterFactory(GsonConverterFactory.create())
             .build()

         val api = retrofit.create(ApiService::class.java)
         api.fetchAllUsers().enqueue(object : Callback<List<Users>>{
             override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                 for (i in 0..8){
                     // Log.d("jsontest"," Resposta do JSON ${response.body()!![i].email}")
                     //   val rnds = (0..10).random()
                     auxiliar = "${response.body()!![i].username}"
                    // Log.d("jsontest"," Username do JSON ${response.body()!![i].username}")
                   //  Log.d("jsontest"," Email do JSON ${response.body()!![i].email}")

                     Log.d("jsontest"," Username do JSON ${auxiliar}")
                     adapter.add(UserItem("${auxiliar}"))
                 }
                 latest_itens_recycle_view.adapter = adapter

             }
             override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                 Log.d("jsontest"," OnFailure ")

             }
         })

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



}

class UserToFetch(val username:String, val email: String){
    constructor() : this ("","")
}

class UserItem(val user: String): Item<ViewHolder>(){


    override fun bind(viewHolder: ViewHolder, position: Int) {
       // gettingApiDatas(viewHolder)
        viewHolder.itemView.title_name_row_latest_itens.text = user
    }
    override fun getLayout(): Int {
       return R.layout.data_row_latest_itens
    }

   /* fun gettingApiDatas(viewHolder: ViewHolder){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/users/")
            //.baseUrl("https://jsonplaceholder.typicode.com/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var auxiliar : String

        val api = retrofit.create(ApiService::class.java)
        api.fetchAllUsers().enqueue(object : Callback<List<Users>>{
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
            viewHolder.itemView.title_name_row_latest_itens.text = "${response.body()!![0].email}"


           /*     for (i in 0..8){
                    // Log.d("jsontest"," Resposta do JSON ${response.body()!![i].email}")
                    //   val rnds = (0..10).random()
                    Log.d("jsontest"," Username do JSON ${response.body()!![i].username}")
                    Log.d("jsontest"," Email do JSON ${response.body()!![i].email}")
                }*/

            }
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.d("jsontest"," OnFailure ")

            }

        })

         }*/

}




