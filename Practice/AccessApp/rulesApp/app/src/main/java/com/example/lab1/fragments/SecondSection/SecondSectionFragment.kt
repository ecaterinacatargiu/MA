package com.example.lab1.fragments.SecondSection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import kotlinx.android.synthetic.main.fragment_second_section.view.*

class SecondSectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_second_section, container, false)

       view.viewInRangeButton.setOnClickListener{
           findNavController().navigate(R.id.action_secondSectionFragment_to_viewInRangeFragment)
       }

        view.viewByLevelButton.setOnClickListener{
            findNavController().navigate(R.id.action_secondSectionFragment_to_viewByLevelFragment)
        }

        return view
    }

}