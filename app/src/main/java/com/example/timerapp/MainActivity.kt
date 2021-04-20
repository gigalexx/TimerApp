package com.example.timerapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.timerapp.databinding.ActivityMainBinding


private const val IS_TIMER_VISIBLE = "isTimerVisible"
private const val DURATION = "duration"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TimerViewModel
    private var mIsTimerVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel =
                ViewModelProvider(this, MainFactory(application)).get(TimerViewModel::class.java)

        viewModel.timerLiveData.observe(this, {
            binding.tvTimer.text = it
            binding.progressBar.progress = it.toInt()
            isTimesUp(it)

        })


        initListeners()

        if (savedInstanceState != null && savedInstanceState.getBoolean(IS_TIMER_VISIBLE)) {
            flipCard(true)
            val duration = savedInstanceState.getString(DURATION)
            if (duration != null && duration.isNotEmpty()) binding.progressBar.max =
                    duration.toInt()
        }
    }

    private fun isTimesUp(it: String?) {
        if (it!!.toInt() == 0) {
            flipCard(false)
            showTimesUpToast()
        }
    }

    private fun initListeners() {
        binding.btnStart.setOnClickListener {
            if (isValidInput()) {
                viewModel.startTimer(binding.etInput.text.toString())
                binding.progressBar.max = binding.etInput.text.toString().toInt()
                flipCard(true)
            } else {
                showError()
            }
        }

        binding.etInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etLayout.error = null
            }
        }
    }

    private fun showError() {
        binding.etLayout.error = "You need to enter duration > 0"
    }

    private fun showTimesUpToast() {
        Toast.makeText(this, "Time's up!", Toast.LENGTH_SHORT).show()
    }

    private fun flipCard(toTimer: Boolean) {

        if (toTimer) {
            binding.cvGreeting.visibility = View.GONE
            binding.cvTimer.visibility = View.VISIBLE
            binding.etInput.text?.clear()
            mIsTimerVisible = true
        } else {
            binding.cvGreeting.visibility = View.VISIBLE
            binding.cvTimer.visibility = View.GONE
            mIsTimerVisible = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isFinishing
        outState.putBoolean(IS_TIMER_VISIBLE, mIsTimerVisible)
        if (mIsTimerVisible) outState.putString(DURATION, binding.progressBar.max.toString())
    }

    private fun isValidInput(): Boolean {
        val inputText = binding.etInput.text.toString()
        return inputText.isNotEmpty() && inputText != "0"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            Log.d("Event: ", "Activity died")
        } else Log.d("Event: ", "Activity rotated")
    }
}

