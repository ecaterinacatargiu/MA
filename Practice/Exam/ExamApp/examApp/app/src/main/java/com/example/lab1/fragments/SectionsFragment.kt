package com.example.lab1.fragments

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
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import kotlinx.android.synthetic.main.fragment_sections.view.*

class SectionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sections, container, false)

        view.toTeacherButton.setOnClickListener{
            findNavController().navigate(R.id.action_sectionsFragment_to_firstSectionFragment)
        }

        if(isNetworkAvailable(context) == true)
        {
            view.toStudentsButon.setOnClickListener{
                findNavController().navigate(R.id.action_sectionsFragment_to_secondSectionFragment)
            }
        }
        else
        {
            Toast.makeText(requireContext(), "You cannot enter this section due to your internet connection.", Toast.LENGTH_LONG).show()

        }

        if(isNetworkAvailable(context) == true)
        {
            view.toStatisticsButton.setOnClickListener{
                findNavController().navigate(R.id.action_sectionsFragment_to_viewTo10Fragment)
            }
        }
        else
        {
            Toast.makeText(requireContext(), "You cannot enter this section due to your internet connection.", Toast.LENGTH_LONG).show()

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

}