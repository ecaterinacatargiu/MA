package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lab1.model.Entity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment4))

        val clientWebsoket = OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).build()
        val request = Request.Builder().url("ws://10.0.2.2:2019").build()

        val wsListener = EchoWebSocketListener()
        clientWebsoket.newWebSocket(request, wsListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment4)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    inner class EchoWebSocketListener() : WebSocketListener() {

        lateinit var webSocket: WebSocket

        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            this.webSocket = webSocket
            webSocket.send("Hello, there!")
            webSocket.send("What's up?")
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            Log.d("ALELUIA", "Receiving : ${text!!}")

            GlobalScope.launch(Dispatchers.Main) {
                var entity = Entity(
                    id = JSONObject(text).getString("id").toInt(),
                    name = JSONObject(text).getString("name"),
                    level = JSONObject(text).getString("level").toInt(),
                    status = JSONObject(text).getString("status"),
                    from = JSONObject(text).getString("from").toInt(),
                    to = JSONObject(text).getString("to").toInt()
                );

                Toast.makeText(applicationContext, "AM PRIMIT" + entity, Toast.LENGTH_LONG).show()
            }
        }

        fun send(message: String) {
            webSocket.send(message)
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
            Log.d("", "Receiving bytes : ${bytes!!.hex()}")
        }

    }
}