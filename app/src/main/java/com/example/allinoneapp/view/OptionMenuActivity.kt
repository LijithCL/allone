package com.example.allinoneapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.allinoneapp.databinding.ActivityOptionMenuBinding
import android.widget.Toast

import android.graphics.Color
import android.view.Menu
import android.view.MenuItem


class OptionMenuActivity : AppCompatActivity() {

    lateinit var binding:ActivityOptionMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar);
        binding.toolbar.overflowIcon?.setTint(Color.WHITE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.example.allinoneapp.R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.example.allinoneapp.R.id.item1 -> {
                Toast.makeText(applicationContext, "Item 1 Selected", Toast.LENGTH_SHORT).show()
                true
            }
            com.example.allinoneapp.R.id.item2 -> {
                Toast.makeText(applicationContext, "Item 2 Selected", Toast.LENGTH_SHORT).show()
                true
            }
            com.example.allinoneapp.R.id.item3 -> {
                Toast.makeText(applicationContext, "Item 3 Selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}