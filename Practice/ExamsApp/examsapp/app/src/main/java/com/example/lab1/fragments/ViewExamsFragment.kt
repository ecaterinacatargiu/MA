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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Exam
import com.example.lab1.model.ExamBackup
import com.example.lab1.model.ExamsListDTO
import com.example.lab1.viewmodel.ExamViewModel
import kotlinx.android.synthetic.main.fragment_view_exams.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewExamsFragment : Fragment() {

    private lateinit var examViewModel: ExamViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_exams, container, false)

        val adapter = ViewExamsAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        examViewModel = ViewModelProvider(this).get(ExamViewModel::class.java)


        if (isNetworkAvailable(context) == true) {
            Toast.makeText(requireContext(), "INTERNET ON!", Toast.LENGTH_LONG).show()

            examViewModel.readBackup.observe(viewLifecycleOwner, Observer { books ->
                sendBackupToServer(books)
            })


            getBooksFromServer()

            examViewModel.readAllData.observe(viewLifecycleOwner, Observer { book ->
                adapter.setData(book)
            })
        }
        else
        {
            Toast.makeText(requireContext(), "INTERNET OFF!", Toast.LENGTH_LONG).show()

            examViewModel.readAllData.observe(viewLifecycleOwner, Observer { exam ->
                adapter.setData(exam)
            })
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

    fun getBooksFromServer()
    {
        var service = RetrofitInstance.api
        var call = service.getExams()

        call.enqueue(object : Callback<List<Exam>> {
            override fun onResponse(call: Call<List<Exam>>, response: Response<List<Exam>>) {
                if (response.isSuccessful)
                {
                    val responseBooks = response.body()!!

                    var booksList = ArrayList<Exam>()
                    for (book in responseBooks.toList())
                    {
                        booksList.add(book);
                        examViewModel.addBook(book);
                    }
                } else
                {
                    Toast.makeText(requireContext(), "Response is not successful", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Exam>>, t: Throwable) {
                Toast.makeText(requireContext(), "Cannot connect to server", Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun sendBackupToServer(examsBackup: List<ExamBackup>)
    {
        var service = RetrofitInstance.api

        for (exam in examsBackup)
        {
            var examFromBackup : Exam
            var id = exam.id
            var name = exam.name
            var group = exam.group
            var details = exam.details
            var status = exam.status
            var students = exam.students
            var type = exam.type


            examFromBackup = Exam(id, name, group, details, status, students, type)

            var call : Call<Exam> = service.addExam(examFromBackup)

            call.enqueue(object : Callback<Exam> {
                override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Book from backup added successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "The response was not successful.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Exam>, t: Throwable) {
                    Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG)
                        .show()
                }
            })
            examViewModel.deleteBookFromBackup(exam);

        }

    }
}