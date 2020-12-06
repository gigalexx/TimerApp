package com.example.timerapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BaseMainActivityTest {

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun checkThatTimerViewAppear() {
        onView(withId(R.id.cv_timer)).check(matches(not(isDisplayed())))

        onView(withId(R.id.et_input)).perform(typeText("10"))
        onView(withId(R.id.btn_start)).perform(click())

        onView(withId(R.id.cv_timer)).check(matches((isDisplayed())))

    }

}