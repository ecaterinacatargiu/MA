package com.example.lab1.fragments.SecondSection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import kotlinx.android.synthetic.main.fragment_view_by_level.*
import kotlinx.android.synthetic.main.fragment_view_by_level.view.*
import kotlinx.android.synthetic.main.fragment_view_in_range.*
import kotlinx.android.synthetic.main.fragment_view_in_range.fromInput
import kotlinx.android.synthetic.main.fragment_view_in_range.view.*
import kotlinx.android.synthetic.main.fragment_view_in_range.view.viewByRangeButton


class ViewByLevelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_by_level, container, false)

        view.viewEntitiesByLevelButton.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("level", Integer.parseInt(levelInput.text.toString()))

            findNavController().navigate(R.id.action_viewByLevelFragment_to_viewEntitiesByLevelFragment, bundle)
        }


        return view
    }

}