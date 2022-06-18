package com.varivoda.igor.autokola_testovi2019.util

import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences

class FakePreferences: Preferences {

    var sound = true
    var toast = "Dnevna"

    override fun saveToastDesign(string: String) {
        toast = string
    }

    override fun getToastDesign(): String {
        return toast
    }

    override fun saveSoundSwitch(boolean: Boolean) {
        sound = boolean
    }

    override fun getSoundSwitch(): Boolean {
        return sound
    }
}