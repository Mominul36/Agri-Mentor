package com.example.agrimentor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.agrimentor.Activity.LoginActivity
import com.example.agrimentor.Activity.MyProductActivity
import com.example.agrimentor.Fragments.HomeFragment
import com.example.agrimentor.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    // LinearLayout items
    lateinit var navHome: LinearLayout
    lateinit var navMarket: LinearLayout
    lateinit var navCropCare: LinearLayout
    lateinit var navAdvisor: LinearLayout
    lateinit var navProfile: LinearLayout

    // ImageView items
    lateinit var iconHome: ImageView
    lateinit var iconMarket: ImageView
    lateinit var iconCropCare: ImageView
    lateinit var iconAdvisor: ImageView
    lateinit var iconProfile: ImageView

    // TextView items
    lateinit var txtHome: TextView
    lateinit var txtMarket: TextView
    lateinit var txtCropCare: TextView
    lateinit var txtAdvisor: TextView
    lateinit var txtProfile: TextView


    lateinit var binding: ActivityMainBinding
    var flag: Boolean = false
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        initVariable()
        setFragment(HomeFragment())
        setNavigationItemColor(navHome)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        // Set up custom navigation icon on the right side
        binding.rightNavIcon.setOnClickListener {
            if (binding.drawerlayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerlayout.closeDrawer(GravityCompat.END)
            } else {
                binding.drawerlayout.openDrawer(GravityCompat.END)
            }
        }

        // Set up navigation item selection listener
       binding.navView.setNavigationItemSelectedListener(this)




// Set click listeners for each navigation item
        navHome.setOnClickListener {
            setNavigationItemColor(navHome)
        }

        navMarket.setOnClickListener {
            setNavigationItemColor(navMarket)
        }

        navCropCare.setOnClickListener {
            setNavigationItemColor(navCropCare)
        }

        navAdvisor.setOnClickListener {
            setNavigationItemColor(navAdvisor)
        }

        navProfile.setOnClickListener {
            setNavigationItemColor(navProfile)
        }










    }

    fun setFragment(fragment: Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager
        val frammentTransition : FragmentTransaction = fragmentManager.beginTransaction()

        if(!flag){
            frammentTransition.add(R.id.frame,fragment)
            flag = true
        }
        else{
            frammentTransition.replace(R.id.frame,fragment)
        }
        frammentTransition.addToBackStack(null)
        frammentTransition.commit()
    }

    private fun initVariable() {
        // LinearLayout items
        navHome = findViewById(R.id.nav_home)
        navMarket = findViewById(R.id.nav_market)
        navCropCare = findViewById(R.id.nav_crop_care)
        navAdvisor = findViewById(R.id.nav_advisor)
        navProfile = findViewById(R.id.nav_profile)

// ImageView items
        iconHome = findViewById(R.id.icon_home)
        iconMarket = findViewById(R.id.icon_market)
        iconCropCare = findViewById(R.id.icon_crop_care)
        iconAdvisor = findViewById(R.id.icon_advisor)
        iconProfile = findViewById(R.id.icon_profile)

// TextView items
        txtHome = findViewById(R.id.txt_home)
        txtMarket = findViewById(R.id.txt_market)
        txtCropCare = findViewById(R.id.txt_crop_care)
        txtAdvisor = findViewById(R.id.txt_advisor)
        txtProfile = findViewById(R.id.txt_profile)
    }

    private fun setNavigationItemColor(layout: LinearLayout?) {
        // Reset all items to default color and icon
        setAllNavigationItemBlack()
        val newWidth = 50
        val newHeight = 50
        val params = LinearLayout.LayoutParams(newWidth, newHeight)

        // Set selected item color and icon based on the passed layout
        when (layout) {
            navHome -> {
                txtHome.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconHome.setImageResource(R.drawable.home2)
                iconHome.layoutParams = params
            }
            navMarket -> {
                txtMarket.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconMarket.setImageResource(R.drawable.cart2)
            }
            navCropCare -> {
                txtCropCare.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconCropCare.setImageResource(R.drawable.crop_care2)
            }
            navAdvisor -> {
                txtAdvisor.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconAdvisor.setImageResource(R.drawable.message2)
            }
            navProfile -> {
                txtProfile.setTextColor(ContextCompat.getColor(this, R.color.base_color))
                iconProfile.setImageResource(R.drawable.profile_user2)
            }
        }
    }



    private fun setAllNavigationItemBlack() {
        // Set color for all TextViews
        txtHome.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtMarket.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtCropCare.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtAdvisor.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtProfile.setTextColor(ContextCompat.getColor(this, R.color.black))

        // Set image resources for all ImageViews
        iconHome.setImageResource(R.drawable.home)
        iconMarket.setImageResource(R.drawable.cart)
        iconCropCare.setImageResource(R.drawable.crop_care)
        iconAdvisor.setImageResource(R.drawable.message)
        iconProfile.setImageResource(R.drawable.profile_user)

    }

    override fun onBackPressed() {
        if (binding.drawerlayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerlayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myproduct -> {
//                supportFragmentManager.beginTransaction().replace(R.id.content_frame, HomeFragment()).commit()
            startActivity(Intent(this,MyProductActivity::class.java))
            }
            R.id.nav_profile -> {
//                supportFragmentManager.beginTransaction().replace(R.id.content_frame, ProfileFragment()).commit()
            }
            R.id.nav_settings -> {
//                supportFragmentManager.beginTransaction().replace(R.id.content_frame, SettingsFragment()).commit()
            }
            R.id.nav_logout -> {
                auth.signOut()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
        binding.drawerlayout.closeDrawer(GravityCompat.END)
        return true
    }



}
