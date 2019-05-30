package uc.seng440.project2_team18

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import uc.seng440.project2_team18.Models.Achievement.Achievement
import uc.seng440.project2_team18.Models.Achievement.AchievementRepository


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

        //TODO Sets the start fragment
//        if(savedInstanceState == null) {
//
//            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, BookFragment()).commit()
//            nav_view.setCheckedItem(R.id.nav_myBooks)
//            toolbar.title = getString(R.string.menu_my_books)
//        }

        //Follow this https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24

        val achievementRepository = AchievementRepository(applicationContext)

        if (achievementRepository.getAchievementByTitle("Recreation Centre").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Recreation Centre", "Bronze", "Let's go get fit!"))
        }
        if (achievementRepository.getAchievementByTitle("Foundry").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Foundry", "Bronze", "Thursday mono?"))
        }
        if (achievementRepository.getAchievementByTitle("Central Library").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Central Library", "Bronze", "Tallest building at UC?"))
        }
        if (achievementRepository.getAchievementByTitle("EPS Library").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("EPS Library", "Bronze", "Has the most STEM books!"))
        }
        if (achievementRepository.getAchievementByTitle("Macmillan Brown Library").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Macmillan Brown Library", "Bronze", "Find the cultural heritage collections."))
        }
        if (achievementRepository.getAchievementByTitle("The Book Shop").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("The Book Shop", "Bronze", "Let's purchase books and stationary?"))
        }
        if (achievementRepository.getAchievementByTitle("Meremere Building").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Meremere Building", "Bronze", "Old Law and commerce building?"))
        }
        if (achievementRepository.getAchievementByTitle("Erskine").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Erskine", "Bronze", "Where do the tech geniuses live?"))
        }
        if (achievementRepository.getAchievementByTitle("Engineering Core").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Engineering Core", "Bronze", "Most purple place on campus?"))
        }
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                CustomMapsFragment()
            ).commit()
            nav_view.setCheckedItem(R.id.nav_map)
            toolbar.title = getString(R.string.menu_map)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Check permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 3)
        }


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
