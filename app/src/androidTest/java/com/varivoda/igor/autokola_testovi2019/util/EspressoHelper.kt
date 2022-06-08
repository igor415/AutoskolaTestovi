package com.varivoda.igor.autokola_testovi2019.util

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import timber.log.Timber


fun matchesBackgroundColor(expectedResourceId: Int): Matcher<View?>? {
    return object : BoundedMatcher<View?, View>(View::class.java) {
        var actualColor = 0
        var expectedColor = 0
        var message: String? = null
        override fun matchesSafely(item: View): Boolean {
            if (item.getBackground() == null) {
                message = item.getId().toString() + " does not have a background"
                return false
            }
            val resources: Resources = item.getContext().getResources()
            expectedColor = ResourcesCompat.getColor(resources, expectedResourceId, null)
            try {
                actualColor = (item.getBackground() as ColorDrawable).color
            } catch (e: Exception) {
                actualColor = (item.getBackground() as GradientDrawable).color!!.defaultColor
            } finally {
                if (actualColor == expectedColor) {
                    Timber.i(
                        "Success...: Expected Color " + String.format(
                            "#%06X",
                            0xFFFFFF and expectedColor
                        ) + " Actual Color " + String.format("#%06X", 0xFFFFFF and actualColor)
                    )
                }
            }
            return actualColor == expectedColor
        }

        override fun describeTo(description: Description) {
            if (actualColor != 0) {
                message = ("Background color did not match: Expected "
                        + String.format(
                    "#%06X",
                    (0xFFFFFF and expectedColor)
                ) + " was " + String.format("#%06X", (0xFFFFFF and actualColor)))
            }
            description.appendText(message)
        }
    }
}