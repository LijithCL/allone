package com.example.allinoneapp.utilClass

import android.R

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes


class PersonAdapter(private val people: List<Person>, @param:LayoutRes private val rowLayout: Int) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.fullName.text = person.fullName
    }

    override fun getItemCount(): Int {
        return people.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullName: TextView

        init {
            fullName = view.findViewById(com.example.allinoneapp.R.id.tv_text)
        }
    }
}