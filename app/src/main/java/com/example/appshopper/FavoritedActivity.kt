package com.example.appshopper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.kotlinandroidextensions.*
import kotlinx.android.synthetic.main.activity_favorited.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.*
import com.xwray.groupie.ViewHolder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.data_row_favorited.view.*

class FavoritedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorited)
        supportActionBar?.title = "Favoritos"
        val uid = FirebaseAuth.getInstance().uid ?:""
        val adapter = GroupAdapter<com.xwray.groupie.ViewHolder>()


        recyclerview_favorited.adapter = adapter

        fetchUsers(uid)
    }

    private fun fetchUsers( id: String){
       val ref =  FirebaseDatabase.getInstance().getReference("/favorites")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach(){
                    val valor = it.getValue(UserFavAux::class.java)
                    if(valor!=null && id == valor.uid){
                        Log.d("teste","valor do uid recebido ${id}")
                        Log.d("teste","valor do uid do objeto recebido ${valor.uid}")
                    adapter.add(UserItemFavorite(valor))}
                }
                recyclerview_favorited.adapter=adapter
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

class UserItemFavorite (val usuario : UserFavAux) : Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView.text= usuario.title
    }

    override fun getLayout(): Int {
        return R.layout.data_row_favorited
    }
    
}

class UserFavAux( val title:String,val uid:String) {
    constructor() : this( "","")
}

