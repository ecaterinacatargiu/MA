package com.example.lab1.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Exam
import com.example.lab1.model.ExamBackup
import com.example.lab1.viewmodel.ExamViewModel
import kotlinx.android.synthetic.main.fragment_add_exam.*
import kotlinx.android.synthetic.main.fragment_add_exam.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class AddExamFragment : Fragment() {

    private lateinit var examViewModel : ExamViewModel

    val clientWebsoket = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .build()

    val request = Request.Builder()
        .url("ws://10.0.2.2:2018")
        .build()
    val wsListener = EchoWebSocketListener()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_exam, container, false)

        examViewModel = ViewModelProvider(this).get(ExamViewModel::class.java)

        view.registerExamButton.setOnClickListener {
            addExam()
        }
        return view

    }


    private fun addExam() {
        var name = nameInput.text.toString()
        var group = groupInput.text.toString()
        var details = detailsInput.text.toString()
        var status = statusInput.text.toString()
        var students = studentsInput.text.toString()
        var type = typeInput.text.toString()


        val exam = Exam(Random.nextInt(), name, group,details, status, Integer.parseInt(students.toString()), type)

        if(isNetworkAvailable(context)==true) {

            var service = RetrofitInstance.api
            var call: Call<Exam> = service.addExam(exam)

            call.enqueue(object : Callback<Exam> {
                override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Exam added successfully", Toast.LENGTH_LONG).show()
                        examViewModel.addBook(exam)

                    } else {
                        Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Exam>, t: Throwable) {
                    Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(requireContext(), "INTERNET OFF!", Toast.LENGTH_LONG).show()
            if(checkInput(name, group, details, status, type))
            {
                val bookBackup = ExamBackup(0, name, group, details, status, Integer.parseInt(students.toString()), type)
                examViewModel.addBookToBackUp(bookBackup)
            }
            else
            {
                Toast.makeText(requireContext(), "Invalid data. Try again.", Toast.LENGTH_LONG).show()
            }

        }
        findNavController().navigate(R.id.action_addExamFragment_to_sectionsFragment)
    }

    private fun checkInput(
        name: String, group: String,
        details: String,
        status: String,
        type: String
    ) : Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(group)  && TextUtils.isEmpty(details) && TextUtils.isEmpty(status)) && TextUtils.isEmpty(type)
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


    inner class EchoWebSocketListener() : WebSocketListener() {

        lateinit var webSocket: WebSocket
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            this.webSocket = webSocket
            webSocket.send("Hello, there!")
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            Log.d("","Receiving : ${text!!}")
            GlobalScope.launch(Dispatchers.Main) {
                var p = Exam(id = JSONObject(text).getString("id").toInt(), name = JSONObject(text).getString("name"), group = JSONObject(text).getString("group"),
                    details = JSONObject(text).getString("details"), status =  JSONObject(text).getString("status"), students =  JSONObject(text).getString("students").toInt(), type = JSONObject(text).getString("type"))
            }
        }


    }

}