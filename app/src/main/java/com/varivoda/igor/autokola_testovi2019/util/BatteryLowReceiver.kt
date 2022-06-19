package com.varivoda.igor.autokola_testovi2019.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences
import com.varivoda.igor.autokola_testovi2019.ui.shared.toast

class BatteryLowReceiver(private val preferences: Preferences): BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        context?.let {
            context.toast("Baterija bi se mogla uskoro isprazniti, priključite punjač za nastavak.",preferences,true)
        }
    }
}