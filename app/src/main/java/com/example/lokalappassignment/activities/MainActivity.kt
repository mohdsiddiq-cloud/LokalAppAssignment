package com.example.lokalappassignment.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lokalappassignment.R
import com.example.lokalappassignment.fragments.BookmarksFragment
import com.example.lokalappassignment.fragments.JobsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_jobs -> {
                    loadFragment(JobsFragment())
                    true
                }
                R.id.navigation_bookmarks -> {
                    loadFragment(BookmarksFragment())
                    true
                }
                else -> false
            }
        }


        if (savedInstanceState == null) {
            loadFragment(JobsFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}
