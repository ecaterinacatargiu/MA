package com.example.lab1.fragments.SecondSection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab1.API.RetrofitInstance
import com.example.lab1.R
import com.example.lab1.fragments.FirstSection.UpdateEntityFragmentArgs
import com.example.lab1.model.Entity
import com.example.lab1.model.Idu
import com.example.lab1.viewmodel.EntityViewModel
import kotlinx.android.synthetic.main.fragment_buy_item.*
import kotlinx.android.synthetic.main.fragment_update_entity.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BuyItemFragment : Fragment() {

    private val args by navArgs<SecondSectionFragmentArgs>()

    private lateinit var entityViewModel : EntityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_buy_item, container, false)

        entityViewModel = ViewModelProvider(this).get(EntityViewModel::class.java)

        view.attr1.setText(args.currentEntity.name.toString())
        view.attr2.setText(args.currentEntity.quantity.toString())
        view.attr3.setText(args.currentEntity.price.toString())
        view.attr4.setText(args.currentEntity.status.toString())

        view.buyItemButton.setOnClickListener {
            buy(args.currentEntity.id)
        }
        return view
    }


    fun buy(id: Int)
    {
        var service = RetrofitInstance.api
        var call: Call<Entity> = service.buy(Idu(id))

        call.enqueue(object : Callback<Entity> {
            override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Item successfully bought.", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Entity>, t: Throwable) {
                Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG).show()
            }
        })

        prograssBar.visibility = VISIBLE
        findNavController().navigate(R.id.action_buyItemFragment_to_sectionsFragment)
    }


}