package com.varivoda.igor.autokola_testovi2019.ui.splash

import androidx.constraintlayout.motion.widget.MotionLayout
import com.varivoda.igor.autokola_testovi2019.R

abstract class CustomMotionLayoutListener: MotionLayout.TransitionListener {
    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
    }

    override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        if(currentId == R.id.last){
            doOnEnd(motionLayout, currentId)
        }
    }

    override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {
    }

    abstract fun doOnEnd(motionLayout: MotionLayout?, currentId: Int)
}