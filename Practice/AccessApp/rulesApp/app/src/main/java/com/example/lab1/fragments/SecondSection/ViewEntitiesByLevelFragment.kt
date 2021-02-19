package com.example.lab1.fragments.SecondSection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Entity
import kotlinx.android.synthetic.main.fragment_view_by_group.view.*
import kotlinx.android.synthetic.main.fragment_view_by_group.view.recyclerView
import kotlinx.android.synthetic.main.fragment_view_entities_by_level.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewEntitiesByLevelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_entities_by_level, container, false)

        val adapter = ViewByLevelAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        var level = arguments?.getInt("level")

        viewByLevel(level!!).observe(viewLifecycleOwner, Observer { book ->
            adapter.setData(book)
        })


        view.backButtonn.setOnClickListener {
            findNavController().navigate(R.id.action_viewEntitesByRangeFragment_to_sectionsFragment)
        }

        return view
    }


    fun viewByLevel(level : Int) : LiveData<List<Entity>>
    {

        var service = RetrofitInstance.api
        var call: Call<List<Entity>> = service.getLevel(level)
        var sortedList = MutableLiveData<List<Entity>>()
        var unsortedList = ArrayList<Entity>()


        call.enqueue(object : Callback<List<Entity>>
        {
            override fun onResponse(call: Call<List<Entity>>, response: Response<List<Entity>>) {
                if (response.isSuccessful)
                {
                    Toast.makeText(requireContext(), "Entities by level successfully got", Toast.LENGTH_LONG).show()
                    unsortedList = (response.body() as ArrayList<Entity>?)!!
                    unsortedList.sortBy { exam: Entity -> exam.from }
                    unsortedList.sortBy { exam : Entity -> exam.to }
                    unsortedList.sortBy { exam : Entity -> exam.status }

                    sortedList.postValue(unsortedList)

                } else {
                    Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Entity>>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG).show()
            }
        })

        return sortedList
    }

}