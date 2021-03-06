package com.example.shoppingliststartcodekotlin.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(var name:String = "", var quantity:Int = 0,  @get:Exclude var id: String = ""): Parcelable  {}
