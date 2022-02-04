package com.example.allinoneapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allinoneapp.R
import com.example.allinoneapp.databinding.ActivityCallandSmsBinding
import com.example.allinoneapp.databinding.ActivityCircleAcivityBinding
import com.example.allinoneapp.model.Language
import com.example.allinoneapp.utilClass.RvAdapter

class circleAcivity : AppCompatActivity() {
    lateinit var binding: ActivityCircleAcivityBinding
    private var languageList = ArrayList<Language>()
    private lateinit var rvAdapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCircleAcivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.layoutManager = LinearLayoutManager(this)

        // attach adapter to the recycler view
        rvAdapter = RvAdapter(languageList)
        binding.rvList.adapter = rvAdapter

        // create new objects
        // add some row data
        val language1 = Language(
                "J",
            "Java",
            "Java is an Object Oriented Programming language." +
                    " Java is used in all kind of applications like Mobile Applications (Android is Java based), " +
                    "desktop applications, web applications, client server applications, enterprise applications and many more. ",
            false,
            false
        )
        val language2 = Language(
            "K",
            "Kotlin",
            "Kotlin is a statically typed, general-purpose programming language" +
                    " developed by JetBrains, that has built world-class IDEs like IntelliJ IDEA, PhpStorm, Appcode, etc.",
            false,
            false
        )
        val language3 = Language(
            "P",
            "Python",
            "Python is a high-level, general-purpose and a very popular programming language." +
                    " Python programming language (latest Python 3) is being used in web development, Machine Learning applications, " +
                    "along with all cutting edge technology in Software Industry.",
            false,
            false
        )
        val language4 = Language(
            "C",
            "CPP",
            "C++ is a general purpose programming language and widely used now a days for " +
                    "competitive programming. It has imperative, object-oriented and generic programming features. ",
            false,
            false
        )

        // add items to list
        languageList.add(language1)
        languageList.add(language2)
        languageList.add(language3)
        languageList.add(language4)

        rvAdapter.notifyDataSetChanged()

    }

    // on destroy of view make the binding reference to null
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}