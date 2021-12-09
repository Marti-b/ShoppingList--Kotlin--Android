package com.example.shoppingliststartcodekotlin.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
object Repository {

    var products : MutableList<Product> = mutableListOf()


    lateinit var myContext : Context
    val db = Firebase.firestore

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()


    fun getData(): MutableLiveData<MutableList<Product>> {
        readDataFromFireBase()
        productListener.value = products
        return productListener
    }

     fun readDataFromFireBase()
    {
        val db = Firebase.firestore
        db.collection("products").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Repository", "${document.id} => ${document.data}")
                    val product = document.toObject<Product>()
                    product.id = document.id  //set the ID in the product class
                    products.add(product)
                }
                productListener.value = products //notify our listener we have new data
            }
            .addOnFailureListener { exception ->
                Log.d("Repository", "Error getting documents: ", exception)
            }
    }


    fun addProducts(product : Product){
        /*products.add(product)*/
        db.collection("products")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d("Error", "DocumentSnapshot written with ID: " + documentReference.id)
            }
            .addOnFailureListener { e -> Log.w("Error", "Error adding document", e) }
    }
    fun deleteProduct(index:Int){
        val product = products[index]
        db.collection("products").document(product.id).delete().addOnSuccessListener {
            Log.d("Snapshot","DocumentSnapshot with id: ${product.id} successfully deleted!")
            products.removeAt(index) //removes it from the list
        }
            .addOnFailureListener { e -> Log.w("Error", "Error deleting document", e) }

    }

    fun deleteAllProducts(){
        val batch = db.batch()
        for (product in products) {
            val ref = db.collection("products").document(product.id)
            batch.delete(ref)
        }
        batch.commit().addOnCompleteListener {}

    }
    fun setContext(cont: Context) {
        myContext = cont

    }
}