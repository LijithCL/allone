package com.example.allinoneapp.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allinoneapp.utilClass.PeopleRepo
import com.example.allinoneapp.utilClass.Person
import com.example.allinoneapp.utilClass.PersonAdapter
import com.example.allinoneapp.utilClass.RecyclerSectionItemDecoration
import com.example.allinoneapp.utilClass.RecyclerSectionItemDecoration.SectionCallback
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class StickyHeader : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.allinoneapp.R.layout.activity_sticky_header)

        recyclerView = findViewById<View>( com.example.allinoneapp.R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val people: List<Person> = PeopleRepo().getPeople().sortedByDescending { LocalDate.parse(it.date.toString(), dateTimeFormatter) }
        recyclerView.adapter = PersonAdapter(people,  com.example.allinoneapp.R.layout.list_item)

        Log.e("list", people.toString())
        Toast.makeText(this,people.toString(),Toast.LENGTH_LONG).show()

//        for (peoplelist in people.iterator()){
//            Log.e("list", peoplelist.date)
//        }

        val sectionItemDecoration = RecyclerSectionItemDecoration(
            resources.getDimensionPixelSize(com.example.allinoneapp.R.dimen.recycler_section_header_height),
            true,
            getSectionCallback(people)!!
        )
        recyclerView.addItemDecoration(sectionItemDecoration)

//        val dates = listOf("30-03-2012", "28-03-2013", "31-03-2012", "02-04-2012")
//        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//
//        val result = dates.sortedByDescending {
//            LocalDate.parse(it, dateTimeFormatter)
//        }
//
//        Log.e("date", result.toString())

    }

    private fun getSectionCallback(people: List<Person>): SectionCallback? {
        return object : SectionCallback {
            override fun isSection(position: Int): Boolean {
                return (position == 0 || people[position].date !== people[position -1].date)
            }

            override fun getSectionHeader(position: Int): String {
                return people[position]
                    .date
//                    .subSequence(
//                        0,
//                        1
//                    )
            }
        }
    }
}