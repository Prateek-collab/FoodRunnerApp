package com.prateek.foodrunner.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.prateek.foodrunner.*
import com.prateek.foodrunner.fragment.FaqFragment
import com.prateek.foodrunner.fragment.FavouritesFragment
import com.prateek.foodrunner.fragment.HomeFragment
import com.prateek.foodrunner.fragment.ProfileFragment

class UserActivity : AppCompatActivity() {

    lateinit var drawerLayout:DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var btnLogout:Button
    lateinit var sharedPreferences:SharedPreferences

    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frame)
        navigationView=findViewById(R.id.navigationView)
        btnLogout = findViewById(R.id.btnLogout)

        setUpToolbar()

        openHome()

        val actionBarDrawerToggle= ActionBarDrawerToggle(
            this@UserActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId){
                R.id.home -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            FavouritesFragment()
                        )
                        .commit()
                    supportActionBar?.title="Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            ProfileFragment()
                        )
                        .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.faq -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FaqFragment()
                    )
                        .commit()
                    supportActionBar?.title="About"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
        /*Log out from the application. Remember to clear the shared preferences when you log out of the app*/
        btnLogout.setOnClickListener {
            startActivity(Intent(this@UserActivity, LoginActivity::class.java))
            sharedPreferences.edit().clear().apply()
            finish()
        }

    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Home"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openHome(){
        val fragment= HomeFragment()
        val transaction=supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frame,fragment)
        transaction.commit()
        supportActionBar?.title="Home"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed(){
        val frag=supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is HomeFragment -> openHome()

            else -> super.onBackPressed()
        }
    }
}