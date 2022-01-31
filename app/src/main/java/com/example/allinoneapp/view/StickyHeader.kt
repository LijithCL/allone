package com.example.allinoneapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allinoneapp.utilClass.PeopleRepo
import com.example.allinoneapp.utilClass.Person
import com.example.allinoneapp.utilClass.PersonAdapter
import com.example.allinoneapp.utilClass.RecyclerSectionItemDecoration
import com.example.allinoneapp.utilClass.RecyclerSectionItemDecoration.SectionCallback


class StickyHeader : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.allinoneapp.R.layout.activity_sticky_header)

        recyclerView = findViewById<View>( com.example.allinoneapp.R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val people: List<Person> = PeopleRepo().getPeople().sorted()
        recyclerView.adapter = PersonAdapter(people,  com.example.allinoneapp.R.layout.list_item)

        val sectionItemDecoration = RecyclerSectionItemDecoration(
            resources.getDimensionPixelSize(com.example.allinoneapp.R.dimen.recycler_section_header_height),
            true,
            getSectionCallback(people)!!
        )
        recyclerView.addItemDecoration(sectionItemDecoration)

    }

    private fun getSectionCallback(people: List<Person>): SectionCallback? {
        return object : SectionCallback {
            override fun isSection(position: Int): Boolean {
                return (position == 0 || people[position].fullName.get(0) !== people[position -1].fullName.get(0))
            }

            override fun getSectionHeader(position: Int): CharSequence {
                return people[position]
                    .fullName
                    .subSequence(
                        0,
                        1
                    )
            }
        }
    }
}