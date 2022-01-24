package com.example.allinoneapp.utilClass

import androidx.annotation.NonNull
import java.util.*

class Person(val firstName: CharSequence, val lastName: CharSequence) :
    Comparable<Person?> {
    val fullName: String
        get() = java.lang.String.format(
            Locale.getDefault(),
            NAME_DISPLAY,
            lastName,
            firstName
        )

    override fun compareTo(other: Person?): Int {
        return lastName.toString()
            .compareTo(
                other?.lastName
                    .toString()
            )
    }

    companion object {
        private const val NAME_DISPLAY = "%s %s"
    }


}