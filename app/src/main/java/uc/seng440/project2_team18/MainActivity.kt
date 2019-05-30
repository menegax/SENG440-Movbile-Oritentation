package uc.seng440.project2_team18

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import uc.seng440.project2_team18.Models.Achievement.AchievementRepository
import uc.seng440.project2_team18.Models.User.UserRepository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val REDRAW_REQUEST = 1  // The request code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Check permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 3)
        }

        //TODO Sets the start fragment
//        if(savedInstanceState == null) {
//
//            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, BookFragment()).commit()
//            nav_view.setCheckedItem(R.id.nav_myBooks)
//            toolbar.title = getString(R.string.menu_my_books)
//        }

        //Follow this https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24

        val userRepository = UserRepository(applicationContext)
        val achievementRepository =
            AchievementRepository(applicationContext)

//        achievementRepository.insertAchievement(Achievement("Erskine", "Bronze"))
//        achievementRepository.insertAchievement(Achievement("Engineering Core", "Bronze"))
//
//        val firstName = "Andy"
//        val lastName = "French"
//
//        userRepository.insertUser(User(3, firstName, lastName))


//        val user = userRepository.getUser("Andy", "French")

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
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    CustomMapsFragment()
                ).commit()
                toolbar.title = getString(R.string.menu_map)
            }
            R.id.nav_achievements -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    AchievementsFragment()
                ).commit()
                toolbar.title = getString(R.string.menu_achievements)
            }
            R.id.nav_photos -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    PhotosFragment()
                ).commit()
                toolbar.title = getString(R.string.menu_photos)
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
