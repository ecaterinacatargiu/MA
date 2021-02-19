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
import androidx.navigation.fragment.navArgs
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Exam
import kotlinx.android.synthetic.main.fragment_exam.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamFragment : Fragment() {

    private val args by navArgs<ExamFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_exam, container, false)
        
        getExam(args.currentExam.id)

        view.nameInput.setText(args.currentExam.name)
        view.groupInput.setText(args.currentExam.group)
        view.detailsInput.setText(args.currentExam.details)
        view.statusInput.setText(args.currentExam.status)
        view.studentsInput.setText(args.currentExam.students.toString())
        view.typeInput.setText(args.currentExam.type)


        view.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_examFragment_to_sectionsFragment)
        }

        return view
    }

    private fun getExam(id : Int)
    {
        if(isNetworkAvailable(context)==true) {

            var service = RetrofitInstance.api
            var call: Call<Exam> = service.getExam(id)

            call.enqueue(object : Callback<Exam> {
                override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Exam successfully got",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "The response was not successful.", Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Exam>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Connection to server failed.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
        else {
            Toast.makeText(requireContext(), "INTERNET OFF!", Toast.LENGTH_LONG).show()
        }
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