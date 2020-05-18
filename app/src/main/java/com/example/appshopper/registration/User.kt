package com.example.appshopper.registration

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid:String, val username: String, val dateofbirth:String, val email: String, val password: String, val profileImageUrl: String) : Parcelable{

    constructor():this("","","","","","")

}