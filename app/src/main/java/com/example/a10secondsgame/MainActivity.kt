package com.example.a10secondsgame
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var running: Boolean = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private val handler = Handler()
    private lateinit var timeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<ImageButton>(R.id.startButton)
        val stopButton = findViewById<ImageButton>(R.id.stopButton)
        val restartButton = findViewById<ImageButton>(R.id.restartButton)
        timeTextView = findViewById(R.id.timeTextView)

        timeTextView.text = "00:00:000"

        startButton.setOnClickListener {
            if (!running) {
                startButton.visibility = View.VISIBLE
                stopButton.visibility = View.VISIBLE
                running = true
                startTime = SystemClock.elapsedRealtime() - elapsedTime

                timeTextView.text = "--:--:---"

                handler.removeCallbacks(updateTimer)
            }
        }

        stopButton.setOnClickListener {
            if (!running) {
                startButton.visibility = View.VISIBLE
                stopButton.visibility = View.VISIBLE
                running = true
                startTime = SystemClock.elapsedRealtime() - elapsedTime
            } else {
                startButton.visibility = View.VISIBLE
                stopButton.visibility = View.VISIBLE
                running = false
                elapsedTime = SystemClock.elapsedRealtime() - startTime

                // Display the exact time when stopped
                val minutes = (elapsedTime / 60000).toInt()
                val seconds = ((elapsedTime / 1000) % 60).toInt()
                val milliseconds = (elapsedTime % 1000).toInt()
                val timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
                timeTextView.text = timeString
            }
        }

        restartButton.setOnClickListener {
            startButton.visibility = View.VISIBLE
            stopButton.visibility = View.INVISIBLE
            running = false
            elapsedTime = 0

            timeTextView.text = "00:00:000"
        }
    }

    private val updateTimer: Runnable = object : Runnable {
        override fun run() {
            if (running) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime
                val minutes = (elapsedTime / 60000).toInt()
                val seconds = ((elapsedTime / 1000) % 60).toInt()
                val milliseconds = (elapsedTime % 1000).toInt()
                val timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
                timeTextView.text = timeString
                handler.postDelayed(this, 0)
            }
        }
    }
}



