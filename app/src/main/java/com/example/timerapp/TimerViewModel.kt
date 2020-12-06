package com.example.timerapp

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class TimerViewModel(application: Application) : AndroidViewModel(application) {

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
            for (num in duration.toInt() downTo 0) {
                ensureActive()
                timerLiveData.postValue(num.toString())
                delay(1000)
            }
            playTimesUpSound()
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