package com.example.shoppingliststartcodekotlin

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.databinding.ActivityMainBinding


import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.shoppingliststartcodekotlin.data.Repository
import com.example.shoppingliststartcodekotlin.data.Repository.products
import com.google.firebase.FirebaseApp



class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
   lateinit var adapter:  ProductAdapter
   lateinit var binding : ActivityMainBinding
   lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_layout)
       // setSupportActionBar(toolbar)
        Repository.setContext(this)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        FirebaseApp.initializeApp(applicationContext)


        viewModel.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            updateUI(it)
        })
        binding.addProductButton.setOnClickListener{
            openAddToListDialog()
        }
        binding.sortNameButton.setOnClickListener {
            products.sortBy { it.name }
            adapter.notifyDataSetChanged()
        }
        binding.sortQuantityButton.setOnClickListener {
            products.sortByDescending { it.quantity }
            adapter.notifyDataSetChanged()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("icon_pressed", "${item.itemId}")
        when (item.itemId) {
           /* R.id.item_about -> {
                Toast.makeText(this, "About item clicked!", Toast.LENGTH_LONG)
                    .show()
                return true
            }*/
            R.id.item_delete_all -> {
                openDeleteAllDialog()
                return true
            }
            /*R.id.item_help -> {
                Toast.makeText(this, "Help item clicked!", Toast.LENGTH_LONG)
                    .show()
                return true
            }
            R.id.item_refresh -> {
                Toast.makeText(this, "Refresh item clicked!", Toast.LENGTH_LONG)
                    .show()
                return true
            }*/
        }

        return false //we did not handle the event

    }
    fun updateUI(products : MutableList<Product>) {
        val layoutManager = LinearLayoutManager(this)

        /*you need to have a defined a recylerView in your
        xml file - in this case the id of the recyclerview should
        be "recyclerView" - as the code line below uses that */

        binding.recyclerView.layoutManager = layoutManager

        adapter = ProductAdapter(products)

      /*connecting the recyclerview to the adapter  */
        binding.recyclerView.adapter = adapter

    }
    fun openAddToListDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.addtolist_dialog)

        val cancelBtn = dialog.findViewById<Button>(R.id.cancel_button)
        val addToListBtn = dialog.findViewById<Button>(R.id.add_button)
        val itemName = dialog.findViewById<EditText>(R.id.addItemName)
        val itemQuantity = dialog.findViewById<EditText>(R.id.addItemQuantity)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addToListBtn.setOnClickListener {
            val name : String = itemName.text.toString()
            val quantity : String = itemQuantity.text.toString()
            val quantityInt : Int = quantity.toInt()
            if (name.isNotEmpty() && quantity.isNotEmpty()) {
                Repository.addProducts(Product(name, quantityInt))
                Toast.makeText(applicationContext, "Item added to the list", Toast.LENGTH_LONG).show()
                products.clear()
                viewModel.getData()
                dialog.dismiss()
            } else {
                Toast.makeText(applicationContext, "Item cannot be added, please don't leave any fields empty", Toast.LENGTH_LONG).show()
            }

        }
        dialog.show()
    }
    fun openDeleteAllDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.delete_alert_dialog)

        val cancelBtn = dialog.findViewById<Button>(R.id.cancelDelete_button)
        val deleteAllButton = dialog.findViewById<Button>(R.id.deleteAll_button)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        deleteAllButton.setOnClickListener {
            Repository.deleteAllProducts()
            products.clear()

            dialog.dismiss()
        }
       dialog.show()
    }
}