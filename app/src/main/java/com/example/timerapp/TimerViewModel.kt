package com.example.timerapp

import android.app.Application
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val handler = Handler(Looper.getMainLooper())
    lateinit var runnable: Runnable


    val timerLiveData = MutableLiveData<String>()
    private val mPlayer by lazy {
        MediaPlayer.create(getApplication(), R.raw.time_up)
    }


    /*
       According to requirements  I  can't use `Timer` or `CountDownTimer`
       so here is my solution
     */
    fun startTimer(duration: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var count = 0
            for (num in duration.toInt() downTo 0) {
                runnable = Runnable {
                    timerLiveData.postValue(num.toString())
                }
                handler.postDelayed(runnable, ++count * 1000L)
            }

            runnable = Runnable { playTimesUpSound() }
            handler.postDelayed(runnable, count * 1000L)
        }
    }

    // properly release MediaPlayer
    override fun onCleared() {
        super.onCleared()
        if (mPlayer.isPlaying) mPlayer.stop()
        mPlayer.release()
    }

    private fun playTimesUpSound() {
        mPlayer.start()
    }
}