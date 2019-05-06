package com.example.project2_team18

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val REDRAW_REQUEST = 1  // The request code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //TODO Sets the start fragment
//        if(savedInstanceState == null) {
//
//            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, BookFragment()).commit()
//            nav_view.setCheckedItem(R.id.nav_myBooks)
//            toolbar.title = getString(R.string.menu_my_books)
//        }

    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.nav_map -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CustomMapsFragment()).commit()
                toolbar.title = getString(R.string.menu_map)
            }
            R.id.nav_achievements -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AchievementsFragment()).commit()
                toolbar.title = getString(R.string.menu_achievements)
            }
            R.id.nav_photos -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PhotosFragment()).commit()
                toolbar.title = getString(R.string.menu_achievements)
            }
            R.id.nav_logout -> {
                Toast.makeText(this, getString(R.string.dont_leave), Toast.LENGTH_LONG).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

//    override fun onListFragmentInteraction(item: Book?) {
//        val intent = Intent(this, IndividualBookActivity::class.java)
//        intent.putExtra("book", item)
//        startActivityForResult(intent, REDRAW_REQUEST)
//        overridePendingTransition(R.anim.enter, R.anim.exit)
//
//
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//        // Check which request we're responding to
//        if (requestCode == REDRAW_REQUEST) {
//            // Make sure the request was successful
//            val myBooksFragment = supportFragmentManager.fragments[0]
//            val view = myBooksFragment.view as RecyclerView
//            val adapter = view.adapter as MyBookRecyclerViewAdapter
//            adapter.customDataSetChanged(this)
//
//        }
//    }

    override fun onStop() {
        super.onStop()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()

    }
}
