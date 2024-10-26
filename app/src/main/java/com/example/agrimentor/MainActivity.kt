package com.example.agrimentor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.agrimentor.Activity.LoginActivity
import com.example.agrimentor.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar on the right
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Remove title

        // Initialize Firebase auth
        auth = FirebaseAuth.getInstance()

        // Initialize DrawerLayout
        drawerLayout = binding.drawerLayout

        // Set up ActionBarDrawerToggle to handle opening/closing the drawer
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Setup navigation view and listener
        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        // Close drawer if open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle Home click
               // supportFragmentManager.beginTransaction().replace(R.id.content_frame, HomeFragment()).commit()
            }
            R.id.nav_profile -> {
                // Handle Profile click
                //supportFragmentManager.beginTransaction().replace(R.id.content_frame, ProfileFragment()).commit()
            }
            R.id.nav_settings -> {
                // Handle Settings click
                //supportFragmentManager.beginTransaction().replace(R.id.content_frame, SettingsFragment()).commit()
            }
            R.id.nav_logout -> {
                // Handle Logout click
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        // Close drawer after item is selected
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
