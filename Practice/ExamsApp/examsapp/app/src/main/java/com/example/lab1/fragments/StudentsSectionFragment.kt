package com.example.lab1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import kotlinx.android.synthetic.main.fragment_sections.view.*
import kotlinx.android.synthetic.main.fragment_students_section.view.*

class StudentsSectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_students_section, container, false)

        view.viewDrafts.setOnClickListener{
            findNavController().navigate(R.id.action_studentsSectionFragment_to_viewDraftsFragment)
        }

        return view
    }

}