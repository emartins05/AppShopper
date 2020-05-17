package com.example.appshopper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.kotlinandroidextensions.*
import kotlinx.android.synthetic.main.activity_favoriteds.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item


class FavoritedsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoriteds)

        supportActionBar?.title = "Favoritos"

        val adapter = GroupAdapter<ViewHolder>()

        recyclerview_favoriteds.adapter = adapter


    }
}

class UserItem: Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        TODO("Not yet implemented")

        //will be called in our list for each user object later on...
    }
    override fun getLayout(): Int {
        TODO("Not yet implemented")


    }


}


