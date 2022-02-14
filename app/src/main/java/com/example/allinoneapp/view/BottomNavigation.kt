package com.example.allinoneapp.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.allinoneapp.R
import com.example.allinoneapp.databinding.ActivityBottomNavigationBinding
import com.example.allinoneapp.view.Fragment.HomeFragment
import com.example.allinoneapp.view.Fragment.ProfileFragment
import com.example.allinoneapp.view.Fragment.SearchFragment
import com.example.allinoneapp.view.Fragment.SettingsFragment

class BottomNavigation : AppCompatActivity() {
    lateinit var binding: ActivityBottomNavigationBinding
    val fm: FragmentManager = supportFragmentManager
    val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background=null
        binding.bottomNavigationView.menu.getItem(2).isEnabled=false

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.messagedialog)

        val homePage: Fragment = HomeFragment()
        val profilePage: Fragment = ProfileFragment()
        val settingsPage: Fragment = SettingsFragment()
        val searchPage: Fragment = SearchFragment()


        fragmentTransaction.replace(R.id.layout, homePage)
        fragmentTransaction.commit()

//        binding.page.text = "Home"
        binding.bottomNavigationView.setOnNavigationItemSelectedListener  {
            when(it.itemId){
                R.id.home -> {
//                    binding.page.text = "Home"
//                    val fm: FragmentManager = supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
                    fragmentTransaction.replace(R.id.layout, homePage)
                    fragmentTransaction.commit()
                }
                R.id.profile -> {
//                    binding.page.text = "Profile"
//                    val fm: FragmentManager = supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
                    fragmentTransaction.replace(R.id.layout, profilePage)
                    fragmentTransaction.commit()
                }
                R.id.settings -> {
//                    binding.page.text = "Settings"
//                    val fm: FragmentManager = supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
                    fragmentTransaction.replace(R.id.layout, settingsPage)
                    fragmentTransaction.commit()
                }
                R.id.search -> {
//                    binding.page.text = "Search"
//                    val fm: FragmentManager = supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
                    fragmentTransaction.replace(R.id.layout, searchPage)
                    fragmentTransaction.commit()
                }
            }
            true
        }

        binding.fab.setOnClickListener {
            dialog.show()
        }
        dialog.findViewById<CardView>(R.id.dialog_message).setOnClickListener {
            dialog.dismiss()
        }

    }
}