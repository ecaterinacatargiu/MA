package com.example.lab1.fragments.FirstSection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Entity
import com.example.lab1.viewmodel.EntityViewModel
import kotlinx.android.synthetic.main.fragment_first_section.view.*
import kotlinx.android.synthetic.main.fragment_update_entity.*
import kotlinx.android.synthetic.main.fragment_update_entity.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateEntityFragment : Fragment() {

    private val args by navArgs<UpdateEntityFragmentArgs>()

    private lateinit var entityViewModel : EntityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update_entity, container, false)

        entityViewModel = ViewModelProvider(this).get(EntityViewModel::class.java)

        getEntity(args.currentEntity.id)

        view.attr1.setText(args.currentEntity.name.toString())
        view.attr2.setText(args.currentEntity.level.toString())
        view.attr3.setText(args.currentEntity.status.toString())
        view.attr4.setText(args.currentEntity.from.toString())
        view.attr5.setText(args.currentEntity.to.toString())

        view.updateEntityButton.setOnClickListener {
            update()
        }

        return view
    }


    private fun update()
    {
        val name = attr1.text.toString()
        val level = Integer.parseInt(attr2.text.toString())
        val status = attr3.text.toString()
        val from = Integer.parseInt(attr4.text.toString())
        val to = Integer.parseInt(attr5.text.toString())

        if(isNetworkAvailable(context))
        {
            var service = RetrofitInstance.api

            val updatedEntity = Entity(args.currentEntity.id, name, level, status, from, to)
            var call : Call<Entity> = service.updateEntity(updatedEntity)

            call.enqueue(object : Callback<Entity> {
                override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                    if(response.isSuccessful)
                    {
                        if (checkInput(name, status)) {

                            val updatedBook = Entity(args.currentEntity.id, name, level, status, from, to)
                            entityViewModel.update(updatedBook);

                            Toast.makeText(requireContext(), "The entity was successfully updated!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_updateEntityFragment_to_sectionsFragment)
                        } else
                        {
                            Toast.makeText(requireContext(), "Invalid data. Try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(requireContext(), "Something went wrong. The response from server has not been successful.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Entity>, t: Throwable) {
                    Toast.makeText(requireContext(), "Something went wrong. The server cannot be accessed.", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else
        {
            Toast.makeText(requireContext(), "Application is offline. The operation is not available.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInput(name: String, status: String) : Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(status))
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

    private fun getEntity(id: Int)
    {
        if(isNetworkAvailable(context)==true) {

            var service = RetrofitInstance.api
            var call: Call<Entity> = service.getEntity(id)

            call.enqueue(object : Callback<Entity> {
                override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Exam successfully got", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Entity>, t: Throwable) {
                    Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG).show()
                }
            })
        }
        else {
            Toast.makeText(requireContext(), "INTERNET OFF!", Toast.LENGTH_LONG).show()
        }
    }

}