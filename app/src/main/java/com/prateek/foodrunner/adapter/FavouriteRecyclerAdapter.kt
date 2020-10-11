package com.prateek.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prateek.foodrunner.R
import com.prateek.foodrunner.database.RestaurantEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val restaurantList: List<RestaurantEntity>): RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_row,parent,false)

        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size

    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val restaurant=restaurantList[position]

        holder.txtRestaurantName.text=restaurant.restaurantName
        /*holder.txtBookAuthor.text=book.bookAuthor*/
        holder.txtRestaurantPrice.text=restaurant.restaurantPrice
        holder.txtRestaurantRating.text=restaurant.restaurantRating
        Picasso.get().load(restaurant.restaurantImage).error(R.drawable.default_book_cover).into(holder.imgRestaurantImage)

    }

    class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtRestaurantName: TextView =view.findViewById(R.id.txtFavRestaurantTitle)
        /*val txtBookAuthor: TextView =view.findViewById(R.id.txtFavBookAuthor)*/
        val txtRestaurantPrice: TextView =view.findViewById(R.id.txtFavRestaurantPrice)
        val txtRestaurantRating: TextView =view.findViewById(R.id.txtFavRestaurantRating)
        val imgRestaurantImage: ImageView =view.findViewById(R.id.imgFavRestaurantImage)
        val llContent: LinearLayout =view.findViewById(R.id.llFavContent)
    }
}