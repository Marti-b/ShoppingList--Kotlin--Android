package com.example.shoppingliststartcodekotlin.data

import android.util.Log
import androidx.lifecycle.MutableLiveData

object Repository {
    var products = mutableListOf<Product>()

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()


    fun getData(): MutableLiveData<MutableList<Product>> {
        if (products.isEmpty())
            createTestData()
        productListener.value = products //we inform the listener we have new data
        return productListener
    }

    fun createTestData()
    {
        //add some products to the products list - for testing purposes
        Log.d("Repository","create testdata")
        products.add(Product(name="Dolce Gabbana The One", type = "Eau de Parfum"))
        products.add(Product(name="Versace Eros", type = "Eau de Toilette"))
        products.add(Product(name="Chanel Allure Homme Sport", type = "Eau de Toilette"))
        products.add(Product(name="YSL La Nuit de L'Homme", type = "Eau de Toilette"))
        products.add(Product(name="CH Man Prive", type = "Eau de Toilette"))
    }
    fun addProducts(product : Product){
        products.add(product)
    }
    fun deleteProduct(index:Int){
        products.removeAt(index)
        productListener.value = products
    }
    fun deleteAllProducts(){
        products.clear()
        productListener.value = products
    }

}