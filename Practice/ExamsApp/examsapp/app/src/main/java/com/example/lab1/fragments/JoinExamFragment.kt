package com.example.lab1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.model.Exam
import com.example.lab1.model.Idu
import kotlinx.android.synthetic.main.fragment_exam.view.*
import kotlinx.android.synthetic.main.fragment_join_exam.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinExamFragment : Fragment() {

    private val args by navArgs<JoinExamFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_join_exam, container, false)

        view.nameDraftInput.setText(args.currentDraft.name)

        view.joinButton.setOnClickListener{
            joinExam(args.currentDraft.id)
        }

        return view
    }

    fun joinExam(id: Int)
    {
        var service = RetrofitInstance.api
        var call: Call<Exam> = service.joinExam(Idu(id))

        call.enqueue(object : Callback<Exam> {
            override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Exam successfully joined.", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Exam>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG).show()
            }
        })
    }
}