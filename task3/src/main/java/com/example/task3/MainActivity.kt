package com.example.task3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    val ERROR = "Exception"
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var addr: String
        var image: Bitmap? = null
        val imageView: ImageView = findViewById(R.id.imageView)
        val editText: EditText? = findViewById(R.id.editText)
        val download: Button = findViewById(R.id.download)
        val clear: Button = findViewById(R.id.clear)
        clear.setOnClickListener {editText!!.setText("")}
        download.setOnClickListener {
             lifecycle.coroutineScope.launchWhenResumed {
                withContext(Dispatchers.IO) {
                    try {
                        addr = editText?.text.toString()
                        val input = URL(addr).openStream()
                        image = BitmapFactory.decodeStream(input)
                    } catch (e: Exception) {
                        Log.e(ERROR, e.message!!)
                    }
                }
                if (image != null) imageView.setImageBitmap(image)
                else imageView.setImageResource(android.R.color.transparent)
            }
        }
    }

}

