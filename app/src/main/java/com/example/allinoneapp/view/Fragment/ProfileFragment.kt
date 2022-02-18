package com.example.allinoneapp.view.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.allinoneapp.R
import com.example.allinoneapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        val view =binding.root

        binding.btnSwitch.setOnClickListener {
            if (binding.btnSwitch.isChecked) {
                Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "unclicked", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

}