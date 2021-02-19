package com.example.lab1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Exam
import com.example.lab1.viewmodel.ExamViewModel
import kotlinx.android.synthetic.main.fragment_statistics_section.*
import kotlinx.android.synthetic.main.fragment_statistics_section.view.*
import kotlinx.android.synthetic.main.fragment_students_section.view.*
import kotlinx.android.synthetic.main.fragment_view_by_group.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatisticsSectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_statistics_section, container, false)

        view.viewByGroup.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("grupa", inputGroup.text.toString())
            findNavController().navigate(R.id.action_statisticsSectionFragment_to_viewByGroupFragment, bundle)
        }
        return view
    }



}