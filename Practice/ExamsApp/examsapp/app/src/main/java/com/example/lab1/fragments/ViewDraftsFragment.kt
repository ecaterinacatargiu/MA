package com.example.lab1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Exam
import com.example.lab1.viewmodel.ExamViewModel
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_view_drafts.view.*
import kotlinx.android.synthetic.main.fragment_view_exams.view.*
import kotlinx.android.synthetic.main.fragment_view_exams.view.recyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewDraftsFragment : Fragment() {

    private lateinit var examViewModel: ExamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_drafts, container, false)

        val adapter = ViewDraftsListAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        examViewModel = ViewModelProvider(this).get(ExamViewModel::class.java)

        viewDrafts().observe(viewLifecycleOwner, Observer { book ->
            adapter.setData(book) })

        return view
    }

    fun viewDrafts(): LiveData<List<Exam>> {
        var service = RetrofitInstance.api
        var call: Call<List<Exam>> = service.getDrafts()
        var tt = MutableLiveData<List<Exam>>()

        call.enqueue(object : Callback<List<Exam>> {
            override fun onResponse(call: Call<List<Exam>>, response: Response<List<Exam>>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Draft successfully got", Toast.LENGTH_LONG).show()


                    tt.postValue(response.body())

                } else {
                    Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Exam>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Connection to server failed.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        return tt
    }
}
