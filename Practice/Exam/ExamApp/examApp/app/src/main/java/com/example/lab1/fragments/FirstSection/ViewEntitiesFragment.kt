package com.example.lab1.fragments.FirstSection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
import com.example.lab1.model.Entity
import com.example.lab1.viewmodel.EntityViewModel
import kotlinx.android.synthetic.main.fragment_view_entities.view.*
import kotlinx.android.synthetic.main.fragment_view_exams.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewEntitiesFragment : Fragment() {

    private lateinit var entityViewModel: EntityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_entities, container, false)

        val adapter = ViewEntitiesAdapter()
        val recyclerView = view.recycleView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        entityViewModel = ViewModelProvider(this).get(EntityViewModel::class.java)

        viewEntities().observe(viewLifecycleOwner, Observer { entities ->
            adapter.setData(entities)
        })

        view.refreshButton.setOnClickListener {
            viewEntities().observe(viewLifecycleOwner, Observer { entities ->
                adapter.setData(entities)
            })

        }

        return view
    }

    fun viewEntities(): LiveData<List<Entity>> {
        var service = RetrofitInstance.api
        var call: Call<List<Entity>> = service.getEntities()
        var tt = MutableLiveData<List<Entity>>()

        call.enqueue(object : Callback<List<Entity>> {
            override fun onResponse(call: Call<List<Entity>>, response: Response<List<Entity>>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Spaces successfully got", Toast.LENGTH_LONG)
                        .show()

                    tt.postValue(response.body())

                } else {
                    Toast.makeText(
                        requireContext(),
                        "The response was not successful.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Entity>>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG)
                    .show()
            }
        })
        return tt
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            try {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected
            } catch (ignored: Exception) {
            }
        }
        return false
    }


}