package com.prateek.foodrunner.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.prateek.foodrunner.R
import com.prateek.foodrunner.adapter.HomeRecyclerAdapter
import com.prateek.foodrunner.model.Restaurant
import com.prateek.foodrunner.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.collections.HashMap

class HomeFragment : Fragment() {

    lateinit var recyclerHome:RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter:HomeRecyclerAdapter

    lateinit var progressLayout: RelativeLayout

    lateinit var progressBar:ProgressBar


    val restaurantInfoList = arrayListOf<Restaurant>()

    val ratingComparator= Comparator<Restaurant>{ restaurant1, restaurant2 ->
        if(restaurant1.restaurantRating.compareTo(restaurant2.restaurantRating,true)==0){
            restaurant1.restaurantName.compareTo(restaurant2.restaurantName,true)
        }else{
            restaurant1.restaurantRating.compareTo(restaurant2.restaurantRating,true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)

        recyclerHome=view.findViewById(R.id.recyclerHome)

        layoutManager=LinearLayoutManager(activity)

        progressLayout=view.findViewById(R.id.progressLayout)

        progressBar=view.findViewById(R.id.progressBar)

        progressLayout.visibility=View.VISIBLE

        val queue= Volley.newRequestQueue(activity as Context)

        val url="http://13.235.250.119/v2/restaurants/fetch_result/"

        if (ConnectionManager().checkConnectivity(activity as Context)) {

            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                    //Here we handle Responses

                    try {

                        progressLayout.visibility = View.GONE

                        val success = it.getBoolean("success")

                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val restaurantJsonObject = data.getJSONObject(i)
                                val restaurantObject = Restaurant(
                                    restaurantJsonObject.getString("book_id"),
                                    restaurantJsonObject.getString("name"),
                                    /*restaurantJsonObject.getString("author"),*/
                                    restaurantJsonObject.getString("rating"),
                                    restaurantJsonObject.getString("price"),
                                    restaurantJsonObject.getString("image")
                                )
                                restaurantInfoList.add(restaurantObject)
                                recyclerAdapter = HomeRecyclerAdapter(activity as Context, restaurantInfoList)

                                recyclerHome.adapter = recyclerAdapter

                                recyclerHome.layoutManager = layoutManager
                            }


                        } else {
                            Toast.makeText(
                                activity as Context,
                                "Some Error Occurred!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } catch (e: JSONException) {

                        Toast.makeText(
                            activity as Context,
                            "Some Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }, Response.ErrorListener {

                    //Here we handle Errors
                    if (activity != null) {
                        Toast.makeText(
                            activity as Context,
                            "Volley Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "d0d081b962c56c"
                        return headers
                    }

                }

            queue.add(jsonObjectRequest)

        }else{
            val dialog= AlertDialog.Builder(activity as Context)
            dialog.setTitle("Internet Error")
            dialog.setMessage("Internet Connection not Found")

            dialog.setPositiveButton("Open Settings"){text,listener ->
                val settingIntent= Intent (Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }

            dialog.setNegativeButton("Exit"){text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==R.id.action_sort){
            Collections.sort(restaurantInfoList,ratingComparator)
            restaurantInfoList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()

        return super.onOptionsItemSelected(item)
    }
}