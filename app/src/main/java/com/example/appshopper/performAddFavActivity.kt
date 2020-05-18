package com.example.appshopper

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_perform_add_favorites.*

class performAddFavActivity : AppCompatActivity() {


    companion object{
        val TAG =" fav"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perform_add_favorites)
        val title = intent.getStringExtra(LatestItensActivity.USER_KEY)
        supportActionBar?.title = "Added to your Favs :)"

        val uid = FirebaseAuth.getInstance().uid ?:""

        title_favorited_done_textview.text = title
     //   title_favorited_done_uid_textview.text= uid
        val data = UserToSaveFav(
            title,
            uid )

     //   Log.d(TAG,"***********************************DATA TITLE${data.title} AND DATA UID${data.uid}")
        performSaveFavoritedsOnFb(title,uid,data)

    }

    private fun performSaveFavoritedsOnFb( titleSavedOnFavorited : String, uid: String, data: UserToSaveFav){

        Log.d(TAG,"***********************************DATA TITLE${data.title} AND DATA UID${data.uid}")
        val reference = FirebaseDatabase.getInstance().getReference("/favorites").push()
      //  val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
       reference.setValue(data)
            .addOnSuccessListener {
                Log.d(TAG,"Saved my favorited on FB:${reference.key}")

            }

      /*  reference.setValue(uid)
            .addOnSuccessListener {
                Log.d(TAG,"Saved my favorited on FB:${reference.key}")

            }*/
    }
}

@Parcelize
class UserToSaveFav (val title : String, val uid: String) : Parcelable{

    constructor():this("","")
}
