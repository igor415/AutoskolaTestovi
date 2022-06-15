package com.varivoda.igor.autokola_testovi2019.ui.test.util

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.varivoda.igor.autokola_testovi2019.R

/*
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.varivoda.igor.autokola_testovi2019.R

class MediaPlayerHelper(lifecycle: Lifecycle, val context: Context): LifecycleObserver {

    lateinit var countdown: MediaPlayer
    lateinit var correct: MediaPlayer

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun createSounds(){
        correct = MediaPlayer.create(context, R.raw.correct)
        countdown = MediaPlayer.create(context, R.raw.countdown)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun releaseSounds(){
        if(::countdown.isInitialized)  countdown.release()
        if(::correct.isInitialized)  correct.release()
    }
}*/

class MediaPlayerHelper (lifecycle: Lifecycle, val context: Context): DefaultLifecycleObserver{

    lateinit var countdown: MediaPlayer
    lateinit var correct: MediaPlayer

    init {
        lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        correct = MediaPlayer.create(context, R.raw.correct)
        countdown = MediaPlayer.create(context, R.raw.countdown)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        if(::countdown.isInitialized)  countdown.release()
        if(::correct.isInitialized)  correct.release()
    }


}