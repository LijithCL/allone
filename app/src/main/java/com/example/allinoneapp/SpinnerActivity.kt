package com.example.allinoneapp

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivitySpinnerBinding


class SpinnerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding :ActivitySpinnerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner.onItemSelectedListener = this

        val categories: MutableList<String> = ArrayList()
        categories.add("Select Number")
        categories.add("One")
        categories.add("Two")
        categories.add("Three")
        categories.add("Four")
        categories.add("Five")
        categories.add("Six")
        categories.add("Seven")
        categories.add("Eight")
        categories.add("Nine")
        categories.add("Ten")

        val dataAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, categories)

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = dataAdapter


    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

        val item = parent.getItemAtPosition(position).toString()
        if (item!="Select Number")
        {
                Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
        // TODO Auto-generated method stub
//        binding.spinner.prompt = "select"
    }

}