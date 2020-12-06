#TimerApp
Test app for O'Relly Media


Hello to everyone!

Here is my solution for the next task :

“# Android Engineer Take Home Task

Below is the description of a take-home task we'd like you to complete. Please don't spend more than 3 hours on this task, and feel free to spend less than that. We've intentionally left requirements ambiguous to allow you the freedom to express yourself in whatever manner you feel is most appropriate.  

## Task Description

Create an Android app that accepts user input.  That user input should be validated as a parsable integer.  This integer will represent the duration for a timer.  The app should provide a UI to start that timer.  While the timer runs (after it’s started), the UI should show the remaining number of seconds.  When the total duration has been met, the UI should show that as well.

* The timer does _not_ need to be an instance of the `Timer` or `CountDownTimer` classes.
* We will compile the source code in Android Studio, and run it on one or more of any available emulators.

In your PR submission, please describe your approach at a high level and why you chose to go that route.
”
##


Since I can’t use ‘Timer’ and ‘CountDownTimer’ I came up with my own solution based on coroutines which ‘sleep’ on 1 sec every iteration:

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

This is probably, not the most efficient and precise solution but I didn't want to use someone's idea and implemented my own one to show how I think

So it uses viewModelScope which automatically stop coroutines if viewModel has died which prevent memory leaks


This method lives in TimerViewModel which help to survive in case of activity recreation because ViewModel  lifecycle  is different then Activity lifecycle

LiveData which provides data to UI also lives in ViewModel which helps automatically continues to feed the view even if the view was recreated. So the app doesn’t lose progress in this case


Due to limited time UI contains only 2 CardViews which presents input and timer views and logically hides from each other.

Timer view show left time and has a progress bar which shows progress more visually

When time is up to app show toast and plays a short melody. MediaPlayer also lives in ViewModel and properly stopped and release in case of end ViewModel lifecycle

Input has limitation  from 1 to 999 die to UI constraints(I didn’t have enough time to implement a more flexible view) and has basic validation

The app was tested on a few devices with   23<=  API <= 30

Hope you like it !

#Please use latest version of Android Studio 4.1+
