package com.example.lab1.fragments.SecondSection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import kotlinx.android.synthetic.main.fragment_view_in_range.*
import kotlinx.android.synthetic.main.fragment_view_in_range.view.*


class ViewInRangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_in_range, container, false)

        view.viewByRangeButton.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("from", Integer.parseInt(fromInput.text.toString()))
            bundle.putInt("to", Integer.parseInt(toInput.text.toString()))

            findNavController().navigate(R.id.action_viewInRangeFragment_to_viewEntitesByRangeFragment, bundle)
        }

        return view
    }

}