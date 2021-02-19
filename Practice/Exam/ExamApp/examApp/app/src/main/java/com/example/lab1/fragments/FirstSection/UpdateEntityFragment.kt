package com.example.lab1.fragments.FirstSection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Entity
import com.example.lab1.viewmodel.EntityViewModel
import kotlinx.android.synthetic.main.fragment_buy_item.*
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

        view.attr1.setText(args.currentEntity.number.toString())
        view.attr2.setText(args.currentEntity.address.toString())
        view.attr3.setText(args.currentEntity.status.toString())
        view.attr4.setText(args.currentEntity.count.toString())

        view.buyItemButton.setOnClickListener {
            delete()
        }
        return view
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


    private fun delete() {

        var service = RetrofitInstance.api
        var call: Call<Entity> = service.deleteBook(args.currentEntity.id)

        call.enqueue(object : Callback<Entity> {
            override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                if (response.isSuccessful) {
                    // entityViewModel.delete(args.currentEntity)
                    Toast.makeText(
                        requireContext(),
                        " ${args.currentEntity.number} was successfully deleted.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong. The response from server has not been successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<Entity>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong. The server cannot be accessed.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })

        prograssBar.visibility = View.VISIBLE
        findNavController().navigate(R.id.action_updateEntityFragment_to_sectionsFragment)

    }

}