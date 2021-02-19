package com.example.lab1.fragments.SecondSection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import kotlinx.android.synthetic.main.fragment_view_by_group.view.*
import kotlinx.android.synthetic.main.fragment_view_by_group.view.backToSectionsButton
import kotlinx.android.synthetic.main.fragment_view_by_group.view.recyclerView
import kotlinx.android.synthetic.main.fragment_view_entites_by_range.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.Observer
import com.example.lab1.model.Entity

//LIST CU ENTITATI SI BACK BUTOTON
class ViewEntitesByRangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_entites_by_range, container, false)

        val adapter = ViewInRangeAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        var from = arguments?.getInt("from")
        var to = arguments?.getInt("to")

        if (from != null && to != null) {
            var lista = viewInRange(from, to)
            lista.observe(viewLifecycleOwner, Observer {
                entities ->
                adapter.setData(entities)
            })
        }

        view.backFromRange.setOnClickListener {
            findNavController().navigate(R.id.action_viewEntitesByRangeFragment_to_sectionsFragment)
        }
        return view
    }


    fun viewInRange(from : Int, to: Int) : LiveData<List<Entity>> {
        var service = RetrofitInstance.api
        var call: Call<List<Entity>> = service.getEntities()
        var inRangeList = MutableLiveData<List<Entity>>()
        var allEntitiesList = ArrayList<Entity>()


        call.enqueue(object : Callback<List<Entity>> {
            override fun onResponse(call: Call<List<Entity>>, response: Response<List<Entity>>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Exams by group successfully got",
                        Toast.LENGTH_LONG
                    ).show()
                    allEntitiesList = (response.body() as ArrayList<Entity>?)!!

                    for(entity in allEntitiesList)
                    {
                        if(entity.from.toInt() < from.toInt() && entity.to.toInt() > to.toInt())
                        {
                            allEntitiesList.remove(entity)
                        }
                    }
                    inRangeList.postValue(allEntitiesList)

                } else {
                    Toast.makeText(
                        requireContext(),
                        "The response was not successful.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<com.example.lab1.model.Entity>>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG)
                    .show()
            }
        })




        return inRangeList
    }
}
