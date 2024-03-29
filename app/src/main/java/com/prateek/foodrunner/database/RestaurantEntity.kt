package com.prateek.foodrunner.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "restaurants")

data class RestaurantEntity(
    @PrimaryKey val restaurant_id:Int,
    @ColumnInfo(name="restaurant_name")val restaurantName:String,
    @ColumnInfo(name="restaurant_price")val restaurantPrice:String,
    @ColumnInfo(name="restaurant_rating")val restaurantRating:String,
    /*@ColumnInfo(name="book_desc")val bookDescription:String,*/
    @ColumnInfo(name="restaurant_image")val restaurantImage:String
)