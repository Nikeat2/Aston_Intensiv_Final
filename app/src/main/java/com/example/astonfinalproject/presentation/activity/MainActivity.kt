package com.example.astonfinalproject.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.presentation.headlines.ui.HEADLINES_FRAGMENT
import com.example.astonfinalproject.presentation.headlines.ui.HeadlinesFragment
import com.example.astonfinalproject.presentation.saved.SavedFragment
import com.example.astonfinalproject.presentation.sources.presentation.SourcesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val INITIAL_FRAGMENT = "INITIAL_FRAGMENT "


class MainActivity : AppCompatActivity() {

    private val headlinesFragment = HeadlinesFragment.newInstance()
    private val savedFragment = SavedFragment.newInstance()
    private val sourcesFragment = SourcesFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, headlinesFragment)
            .addToBackStack(HEADLINES_FRAGMENT)
            .commit()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigationHeadlines -> {
                    loadFragment(headlinesFragment)
                    true
                }

                R.id.navigationSaved -> {
                    loadFragment(savedFragment)
                    true
                }

                R.id.navigationSources -> {
                    loadFragment(sourcesFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(
            HEADLINES_FRAGMENT)
            .commit()
    }

}