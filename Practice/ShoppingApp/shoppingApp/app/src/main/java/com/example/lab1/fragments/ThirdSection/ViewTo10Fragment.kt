package com.example.lab1.fragments.ThirdSection

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.fragments.FirstSection.ViewEntitiesAdapter
import com.example.lab1.model.Entity
import com.example.lab1.viewmodel.EntityViewModel
import kotlinx.android.synthetic.main.fragment_view_entities.view.*
import kotlinx.android.synthetic.main.fragment_view_to10.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewTo10Fragment : Fragment() {

    private lateinit var entityViewModel: EntityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_to10, container, false)

        val adapter = ViewTo10Adapter()
        val recyclerView = view.recyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        entityViewModel = ViewModelProvider(this).get(EntityViewModel::class.java)

        viewMostBought().observe(viewLifecycleOwner, Observer { entities ->
            var top = adapter.top10(entities)
            adapter.setData(top) })

        return view
    }

    fun viewMostBought(): LiveData<List<Entity>>
    {
        var service = RetrofitInstance.api
        var call: Call<List<Entity>> = service.getBought()
        var tt = MutableLiveData<List<Entity>>()
        var sortedList = MutableLiveData<List<Entity>>()
        var unsortedList = ArrayList<Entity>()


        call.enqueue(object : Callback<List<Entity>> {
            override fun onResponse(call: Call<List<Entity>>, response: Response<List<Entity>>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Entites successfully got", Toast.LENGTH_LONG).show()

                    unsortedList = (response.body() as ArrayList<Entity>?)!!
                    unsortedList.sortByDescending{ entity: Entity -> entity.quantity }

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