package com.example.lab1.fragments.FirstSection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import kotlinx.android.synthetic.main.fragment_first_section.view.*
import kotlinx.android.synthetic.main.fragment_update_entity.view.*


class FirstSectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_first_section, container, false)

        view.addEntityButton.setOnClickListener{
            findNavController().navigate(R.id.action_firstSectionFragment_to_addEntityFragment)
        }

        view.viewEntitiesButton.setOnClickListener{
            findNavController().navigate(R.id.action_firstSectionFragment_to_viewEntitiesFragment)
        }

        return view
    }
}