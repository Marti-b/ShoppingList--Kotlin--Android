package com.example.shoppingliststartcodekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.databinding.ActivityMainBinding

import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
//import kotlinx.android.synthetic.main.content_main.*
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
   private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
   lateinit var binding : ActivityMainBinding
   lateinit var viewModel : MainViewModel

    private var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_layout)
       // setSupportActionBar(toolbar)
        layoutManager = LinearLayoutManager(this)
       /* binding.recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        binding.recyclerView.adapter = adapter*/


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]



        viewModel.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            updateUI(it)
        })
    }


    fun updateUI(products : MutableList<Product>) {
        val layoutManager = LinearLayoutManager(this)

        /*you need to have a defined a recylerView in your
        xml file - in this case the id of the recyclerview should
        be "recyclerView" - as the code line below uses that */

        //binding.recyclerView.layoutManager = layoutManager

       //adapter = ProductAdapter(products)

      /*connecting the recyclerview to the adapter  */
        //binding.recyclerView.adapter = adapter

    }
}