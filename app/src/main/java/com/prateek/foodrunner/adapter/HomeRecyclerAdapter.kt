package com.prateek.foodrunner.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prateek.foodrunner.activity.DescriptionActivity
import com.prateek.foodrunner.R
import com.prateek.foodrunner.model.Restaurant
import com.squareup.picasso.Picasso
import java.util.ArrayList

class HomeRecyclerAdapter(val context: Context, val itemList: ArrayList<Restaurant>): RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row, parent,false)

        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val restaurant=itemList[position]
        holder.txtBookName.text=restaurant.restaurantName
        holder.txtBookPrice.text=restaurant.restaurantPrice
        holder.txtBookRating.text=restaurant.restaurantRating
        /*holder.imgBookImage.setImageResource(book.bookImage)*/
        Picasso.get().load(restaurant.restaurantImage).error(R.drawable.default_book_cover).into(holder.imgBookImage)

        holder.llContent.setOnClickListener {
            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("id",restaurant.restaurantId)
            context.startActivity(intent)
        }

    }

    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtBookName:TextView=view.findViewById(R.id.txtRestaurantName)
        val txtBookPrice:TextView=view.findViewById(R.id.txtRestaurantPrice)
        val txtBookRating:TextView=view.findViewById(R.id.txtRestaurantRating)
        val imgBookImage: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val llContent:LinearLayout=view.findViewById(R.id.llContent)
    }
}