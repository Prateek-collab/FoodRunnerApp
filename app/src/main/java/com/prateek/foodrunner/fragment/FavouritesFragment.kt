package com.prateek.foodrunner.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.prateek.foodrunner.R
import com.prateek.foodrunner.adapter.FavouriteRecyclerAdapter
import com.prateek.foodrunner.database.RestaurantDatabase
import com.prateek.foodrunner.database.RestaurantEntity

class FavouritesFragment : Fragment() {

    lateinit var recyclerFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter

    var dbRestaurantList= listOf<RestaurantEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerFavourite=view.findViewById(R.id.recyclerFavourite)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)

        layoutManager= GridLayoutManager(activity as Context,2)

        dbRestaurantList=RetrieveFavourites(activity as Context).execute().get()

        Log.e("TEST","THE SIZE OF LIST IS ${dbRestaurantList.size}")

        if (activity!=null){
            progressLayout.visibility=View.GONE
            recyclerAdapter= FavouriteRecyclerAdapter(activity as Context,dbRestaurantList)
            recyclerFavourite.adapter=recyclerAdapter
            recyclerFavourite.layoutManager=layoutManager
        }
        return view
    }

    class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<RestaurantEntity>>(){
        override fun doInBackground(vararg p0: Void?): List<RestaurantEntity> {

            val db = Room.databaseBuilder(context, RestaurantDatabase::class.java,"books-db").build()


            return db.restaurantDao().getAllRestaurants()
        }

    }
}
