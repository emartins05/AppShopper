package com.example.appshopper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xwray.groupie.kotlinandroidextensions.*
import kotlinx.android.synthetic.main.activity_favorited.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.*
import com.xwray.groupie.ViewHolder

class FavoritedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorited)

        supportActionBar?.title = "Favoritos"

        val adapter = GroupAdapter<com.xwray.groupie.ViewHolder>()

        adapter.add(UserItemFavorite())
        adapter.add(UserItemFavorite())
        adapter.add(UserItemFavorite())
        adapter.add(UserItemFavorite())


        recyclerview_favorited.adapter = adapter
    }
}

class UserItemFavorite : Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        //will be called in our list for each user object later on...
    }

    override fun getLayout(): Int {
        return R.layout.data_row_favorited
    }


}

