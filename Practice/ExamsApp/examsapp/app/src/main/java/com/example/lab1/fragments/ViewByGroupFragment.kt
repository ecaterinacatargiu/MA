package com.example.lab1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Exam
import com.example.lab1.viewmodel.ExamViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_add_exam.*
import kotlinx.android.synthetic.main.fragment_exam.view.*
import kotlinx.android.synthetic.main.fragment_statistics_section.*
import kotlinx.android.synthetic.main.fragment_statistics_section.view.*
import kotlinx.android.synthetic.main.fragment_teacher_section.view.*
import kotlinx.android.synthetic.main.fragment_view_by_group.view.*
import kotlinx.android.synthetic.main.fragment_view_by_group.view.recyclerView
import kotlinx.android.synthetic.main.fragment_view_exams.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewByGroupFragment : Fragment() {

    private lateinit var statistics: StatisticsSectionFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_by_group, container, false)


        val adapter = ViewByGroupListAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        var group = arguments?.getString("grupa")

        viewByGroup(group!!).observe(viewLifecycleOwner, Observer { book ->
            adapter.setData(book) })

        view.backToSectionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_viewByGroupFragment_to_sectionsFragment)
        }

        return view
    }

    fun viewByGroup(group : String) : LiveData<List<Exam>>
    {

        var service = RetrofitInstance.api
        var call: Call<List<Exam>> = service.getByGroup(group)
        var sortedList = MutableLiveData<List<Exam>>()
        var unsortedList = ArrayList<Exam>()


        call.enqueue(object : Callback<List<Exam>>
        {
            override fun onResponse(call: Call<List<Exam>>, response: Response<List<Exam>>) {
                if (response.isSuccessful)
                {
                    Toast.makeText(requireContext(), "Exams by group successfully got", Toast.LENGTH_LONG).show()
                    unsortedList = (response.body() as ArrayList<Exam>?)!!
                    unsortedList.sortBy { exam: Exam -> exam.type }
                    unsortedList.sortByDescending { exam : Exam -> exam.students }

                    sortedList.postValue(unsortedList)

                } else {
                    Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Exam>>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG).show()
            }
        })

        return sortedList
    }


}