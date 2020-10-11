package com.prateek.foodrunner.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.prateek.foodrunner.R
import com.prateek.foodrunner.database.RestaurantDatabase
import com.prateek.foodrunner.database.RestaurantEntity
import com.prateek.foodrunner.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtRestaurantName:TextView
    lateinit var txtRestaurantPrice:TextView
    lateinit var txtRestaurantRating:TextView
    lateinit var imgRestaurantImage:ImageView
    /*lateinit var txtRestaurantDescription:TextView*/
    lateinit var btnAddToFav:Button
    lateinit var progressBar:ProgressBar
    lateinit var progressLayout: RelativeLayout

    lateinit var toolbar: Toolbar

    var restaurantId : String?= "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtRestaurantName=findViewById(R.id.txtRestaurantName)
        txtRestaurantPrice=findViewById(R.id.txtRestaurantPrice)
        txtRestaurantRating=findViewById(R.id.txtRestaurantRating)
        imgRestaurantImage=findViewById(R.id.imgRestaurantImage)
        /*txtRestaurantDescription=findViewById(R.id.txtRestaurantDescription)*/
        btnAddToFav=findViewById(R.id.btnAddToFav)
        progressBar=findViewById(R.id.progressBar)
        progressBar.visibility=View.VISIBLE
        progressLayout=findViewById(R.id.progressLayout)
        progressLayout.visibility=View.VISIBLE

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Restaurant Details"

        if(intent!=null){
            restaurantId= intent.getStringExtra("id")
        }else{
            Toast.makeText(this@DescriptionActivity,"Some unexpected error occured",Toast.LENGTH_SHORT).show()
        }
        if(restaurantId=="100"){
            finish()
            Toast.makeText(this@DescriptionActivity,"Some unexpected error occured",Toast.LENGTH_SHORT).show()
        }

        val queue=Volley.newRequestQueue(this@DescriptionActivity)
        val url="http://13.235.250.119/v2/restaurants/fetch_result/"

        val jsonParams= JSONObject()
        jsonParams.put("id",restaurantId)

        if (ConnectionManager().checkConnectivity(this@DescriptionActivity)){
            val jsonRequest = object: JsonObjectRequest (Request.Method.POST,url,jsonParams, Response.Listener {

                try {
                    val success = it.getBoolean("success")
                    if (success) {
                        val restaurantJsonObject = it.getJSONObject("data")
                        progressLayout.visibility = View.GONE

                        val restaurantImageUrl=restaurantJsonObject.getString("image_url")
                        Picasso.get().load(restaurantJsonObject.getString("image_url"))
                            .error(R.drawable.default_book_cover).into(imgRestaurantImage)
                        txtRestaurantName.text = restaurantJsonObject.getString("name")
                        txtRestaurantRating.text = restaurantJsonObject.getString("rating")
                        txtRestaurantPrice.text = restaurantJsonObject.getString("cost_for_one")
                        /*txtRestaurantDescription.text = restaurantJsonObject.getString("description")*/

                        val restaurantEntity=RestaurantEntity(
                            restaurantId?.toInt() as Int,
                            txtRestaurantName.text.toString(),
                            /*txtBookAuthor.text.toString(),*/
                            txtRestaurantPrice.text.toString(),
                            txtRestaurantRating.text.toString(),
                            /*txtRestaurantDescription.text.toString(),*/
                            restaurantImageUrl
                        )

                        val checkFav= DBAsyncTask(applicationContext,restaurantEntity,1).execute()
                        val isFav=checkFav.get()

                        if (isFav){
                            btnAddToFav.text="Remove From Favourites"
                            val favColor=ContextCompat.getColor(applicationContext,R.color.colorFavourite)
                            btnAddToFav.setBackgroundColor(favColor)
                        }else{
                            btnAddToFav.text="Add to Favourites"
                            val noFavColor=ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                            btnAddToFav.setBackgroundColor(noFavColor)
                        }

                        btnAddToFav.setOnClickListener {
                            if(!DBAsyncTask(applicationContext,restaurantEntity,1).execute().get()){
                                val async=DBAsyncTask(applicationContext,restaurantEntity,2).execute()
                                val result=async.get()

                                if(result){
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Restaurant added to favourites",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    btnAddToFav.text="Remove From Favourites"
                                    val favColor=ContextCompat.getColor(applicationContext,R.color.colorFavourite)
                                    btnAddToFav.setBackgroundColor(favColor)
                                }else{
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some Error Occurred!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }else{
                                val async= DBAsyncTask(applicationContext,restaurantEntity,3).execute()
                                val result=async.get()

                                if(result){
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Restaurant removed from favourites",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    btnAddToFav.text="Add to Favourites"
                                    val nofavColor=ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                                    btnAddToFav.setBackgroundColor(nofavColor)
                                }else{
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some Error Occurred!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    } else{
                        Toast.makeText(this@DescriptionActivity,"Some Error Occurred!",Toast.LENGTH_SHORT).show()
                    }

                }catch (e:Exception){

                    Toast.makeText(this@DescriptionActivity,"Some Error Occurred!",Toast.LENGTH_SHORT).show()
                }

            },Response.ErrorListener {
                Toast.makeText(this@DescriptionActivity,"Some Error Occurred!",Toast.LENGTH_SHORT).show()

            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String,String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="d0d081b962c56c"
                    return headers
                }
            }
            queue.add(jsonRequest)
        }else{
            val dialog= AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Internet Error")
            dialog.setMessage("Internet Connection not Found")

            dialog.setPositiveButton("Open Settings"){text,listener ->
                val settingIntent= Intent (Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }

            dialog.setNegativeButton("Exit"){text, listener ->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }

    }

    class DBAsyncTask(val context: Context, val restaurantEntity: RestaurantEntity, val mode:Int) : AsyncTask<Void,Void,Boolean>(){

        /*
        Mode1-Check DB if the book is favourite or not
        Mode2-Save the book in DB as favourite
        Mode3-Remove the favourite book
         */
        val db = Room.databaseBuilder(context,RestaurantDatabase::class.java,"restaurants-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {
            when(mode){
                1->{
                    //Check DB if the book is favourite or not
                    val restaurant:RestaurantEntity?=db.restaurantDao().getRestaurantById(restaurantEntity.restaurant_id.toString())
                    db.close()
                    return restaurant!=null
                }
                2->{
                    //Save the book in DB as favourite
                    db.restaurantDao().insertRestaurant(restaurantEntity)
                    db.close()
                    return true
                }
                3->{
                    //Remove the favourite book
                    db.restaurantDao().deleteRestaurant(restaurantEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
}