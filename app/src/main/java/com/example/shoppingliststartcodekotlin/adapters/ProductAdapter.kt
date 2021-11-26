package com.example.shoppingliststartcodekotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository


class ProductAdapter(var products: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private val titles = arrayOf("Chapter One","Chapter Two", "Chapter Three", "Chapter Four",
        "Chapter Five", "Chapter Six", "Chapter Seven",
        "Chapter Eight")
    private val details = arrayOf("Item one details", "Item two details",
        "Item three details", "Item four details",
        "Item five details", "Item six details",
        "Item seven details", "Item eight details")

    private val images = intArrayOf(
        R.drawable.theone, R.drawable.eros,
        R.drawable.chanelhmsp,
        R.drawable.lanuit, R.drawable.chmen,
        R.drawable.android_image_6, R.drawable.android_image_7,
        R.drawable.android_image_8)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        var product = Repository.products.get(position)

        holder.itemTitle.text = product.name
        holder.itemDetail.text = product.type
        holder.itemImage.setImageResource(images[position])

    }

    override fun getItemCount(): Int {
        return Repository.products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var itemDelete: Button

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
            itemDelete= itemView.findViewById(R.id.item_delete)
            itemDelete.setOnClickListener { v: View ->
                val position = adapterPosition
                Repository.deleteProduct(position)
                notifyItemRemoved(position) //this line notify the adapter
            }
        }


    }
}
