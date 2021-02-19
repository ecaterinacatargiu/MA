package com.example.lab1.fragments.FirstSection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
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
import com.example.lab1.model.Entity
import com.example.lab1.model.EntityBackup
import com.example.lab1.viewmodel.EntityViewModel
import kotlinx.android.synthetic.main.fragment_add_entity.*
import kotlinx.android.synthetic.main.fragment_add_entity.view.*
import kotlinx.android.synthetic.main.fragment_first_section.view.*
import kotlinx.android.synthetic.main.fragment_first_section.view.addEntityButton
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


class AddEntityFragment : Fragment() {

    private lateinit var entityViewModel : EntityViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_entity, container, false)

        entityViewModel = ViewModelProvider(this).get(EntityViewModel::class.java)

        view.addNewEntityButton.setOnClickListener {
            add()
        }



        return view
    }

    private fun add() {
        var name = attr1.text.toString()
        var level = attr2.text.toString()
        var status = attr3.text.toString()
        var from = attr4.text.toString()
        var to = attr5.text.toString()


        val entity = Entity(Random.nextInt(), name, Integer.parseInt(level.toString()),status, Integer.parseInt(from.toString()), Integer.parseInt(to.toString()))

        if(isNetworkAvailable(context)==true) {

            var service = RetrofitInstance.api
            var call: Call<Entity> = service.addEntity(entity)

            call.enqueue(object : Callback<Entity> {
                override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Entity added successfully", Toast.LENGTH_LONG).show()
                        entityViewModel.add(entity)

                    } else {
                        Toast.makeText(requireContext(), "The response was not successful.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Entity>, t: Throwable) {
                    Toast.makeText(requireContext(), "Connection to server failed.", Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(requireContext(), "INTERNET OFF!", Toast.LENGTH_LONG).show()
            if(checkInput(name, status))
            {
                val entityBackup = EntityBackup(0, name, Integer.parseInt(level.toString()), status, Integer.parseInt(from.toString()), Integer.parseInt(to.toString()))
                entityViewModel.addToBackUp(entityBackup)
            }
            else
            {
                Toast.makeText(requireContext(), "Invalid data. Try again.", Toast.LENGTH_LONG).show()
            }

        }


//        val clientWebsoket = OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).build()
//        val request = Request.Builder().url("ws://10.0.2.2:2019").build()
//
//        val wsListener = EchoWebSocketListener()
//        clientWebsoket.newWebSocket(request, wsListener) // this provide to make 'Open ws connection'

        //Toast.makeText(requireContext(), wsListener.onMessage(clientWebsoket, entity.toString()), Toast.LENGTH_LONG).show()

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
       // clientWebsoket.dispatcher().executorService().shutdown()

        findNavController().navigate(R.id.action_addEntityFragment_to_sectionsFragment)
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


}