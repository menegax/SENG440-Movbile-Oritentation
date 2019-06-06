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
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import uc.seng440.project2_team18.Models.Achievement.Achievement
import uc.seng440.project2_team18.Models.Achievement.AchievementRepository
import uc.seng440.project2_team18.Models.ChaseLocation.ChaseLocation
import uc.seng440.project2_team18.Models.ChaseLocation.ChaseLocationRepository


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

        //Setup all of the Achievements
        setupAchievements()

        //Setup all of the Locations
        setupLocations()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 200
            )
        } else {
            inflateMapView()
        }
    }

    fun inflateMapView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            CustomMapsFragment()
        ).commit()
        nav_view.setCheckedItem(R.id.nav_map)
        toolbar.title = getString(R.string.menu_map)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 200) {
            inflateMapView()
        }
    }

    fun setupAchievements() {
        val achievementRepository = AchievementRepository(applicationContext)
        achievementRepository.deleteAchievement("The True Engineer!")
        if (achievementRepository.getAchievementByTitle("Recreation Center").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Recreation Center", "Bronze", "Let's go get fit!"))
        }
        if (achievementRepository.getAchievementByTitle("Frother!").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Frother!", "Bronze", "Thursday mono?"))
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
            achievementRepository.insertAchievement(Achievement("Meremere Building", "Bronze", "Old Law and Commerce building?"))
        }
        if (achievementRepository.getAchievementByTitle("Erskine").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Erskine", "Bronze", "Where do the tech geniuses live?"))
        }
        if (achievementRepository.getAchievementByTitle("Engineering Core").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("Engineering Core", "Bronze", "Most purple place on campus?"))
        }
        if (achievementRepository.getAchievementByTitle("The True Engineer!").isEmpty()) {
            achievementRepository.insertAchievement(Achievement("The True Engineer!", "Bronze", "Gold achievements: EPS, Erskine and core."))
        }
    }

    fun setupLocations() {

        val chaseLocationRepository = ChaseLocationRepository(applicationContext)

        val chaseLocationList = chaseLocationRepository.getAllChaseLocations()
        if(chaseLocationList.isEmpty()) {
            chaseLocationRepository.insertChaseLocation(ChaseLocation("Recreation Center", -43.527062, 172.584514, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("Foundry", -43.525059, 172.580228, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("Central Library", -43.523705, 172.582910, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("EPS Library", -43.521359, 172.584686, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("Macmillan Brown Library", -43.523561, 172.585914, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("The Book Shop", -43.524226, 172.581896, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("Meremere Building", -43.525093, 172.584331, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("Erskine", -43.522565, 172.581180, false))
            chaseLocationRepository.insertChaseLocation(ChaseLocation("Engineering Core", -43.521516, 172.583814, false))
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
