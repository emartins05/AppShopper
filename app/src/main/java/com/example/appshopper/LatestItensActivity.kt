package com.example.appshopper

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.RecyclerView
import com.example.appshopper.model.Users
import com.example.appshopper.registration.ChangeDataReg
import com.example.appshopper.registration.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_itens.*
import kotlinx.android.synthetic.main.data_row_latest_itens.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LatestItensActivity : AppCompatActivity() {

    var userIsLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_itens)
        verifyUserIsLoggedIn()
        //Se o usuário estiver logado, vamos consumir os dados da API
        if(userIsLoggedIn){
            // Se o usuário estiver logado preenchemos a lista e monitoramos os possíveis favoritos
            fetchUsers()
        }
    }

    companion object{
        val USER_KEY = "USER_KEY"
    }

     private fun fetchUsers(){
         var auxiliar: String
         var auxiliar2: String
         val adapter = GroupAdapter<ViewHolder>()

         val retrofit = Retrofit.Builder()
             .baseUrl("https://jsonplaceholder.typicode.com/users/")
             //.baseUrl("https://jsonplaceholder.typicode.com/users/")
             .addConverterFactory(GsonConverterFactory.create())
             .build()

         val api = retrofit.create(ApiService::class.java)
            api.fetchAllUsers().enqueue(object : Callback<List<Users>>{
             override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                 for (i in 0..40){
                     auxiliar = "${response.body()!![i].title}"
                     auxiliar2= "${response.body()!![i].thumbnailUrl}"

                     adapter.add(UserItem("${auxiliar2}","${auxiliar}"))

                 }



                 adapter.setOnItemClickListener { item, view ->
                     val userItem = item as UserItem
                     val intent = Intent(view.context, performAddFavActivity::class.java)
                   //  intent.putExtra(USER_KEY,"${userItem.title}")
                    intent.putExtra(USER_KEY,"${userItem.title}")
                   //  intent.putExtra( USER_KEY, userItem)
                     startActivity(intent)
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

            R.id.edditUser_regData ->{
                val intent  = Intent(this, ChangeDataReg::class.java)
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

        val searchItem = menu?.findItem(R.id.search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val editext = searchView.findViewById<SearchView>(R.id.search)
            editext.queryHint = "Search here"
        }

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItens = if (menu != null) menu.findItem(R.id.search) else null
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextFocusChangeListener(object:SearchView.OnQueryTextListener,
            View.OnFocusChangeListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               searchView.clearFocus()
                searchView.setQuery("",false)
                searchItem.collapseActionView()
                Toast.makeText(this@LatestItensActivity, "Buscando por ${query}", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onFocusChange(v: View?, hasFocus: Boolean) {
               //nothing to do
            }
        })

        return super.onCreateOptionsMenu(menu)
    }



class UserItem(val thumbnailURL: String, val title : String): Item<ViewHolder>(){


    override fun bind(viewHolder: ViewHolder, position: Int) {
       // gettingApiDatas(viewHolder)
        viewHolder.itemView.title_name_row_latest_itens.text = title
       // Log.d("nota", "URL ->>>>>>>>>>>>>>>>>${thumbnailURL}")
        Picasso.get().load(thumbnailURL).into(viewHolder.itemView.imageView)

    }
    override fun getLayout(): Int {
       return R.layout.data_row_latest_itens
    }


}

}




