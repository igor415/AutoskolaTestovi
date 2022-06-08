package com.varivoda.igor.autokola_testovi2019.data.pref

import android.content.SharedPreferences

class PreferencesImpl(private val sharedPreferences: SharedPreferences): Preferences {


    override fun saveToastDesign(string: String) {
        sharedPreferences.edit().putString("toastDesign", string).apply()
    }

    override fun getToastDesign(): String {
        return sharedPreferences.getString("toastDesign","Default") ?: "Default"
    }

    override fun saveSoundSwitch(boolean: Boolean) {
        sharedPreferences.edit().putBoolean("SoundSwitch", boolean).apply()
    }

    override fun getSoundSwitch(): Boolean {
        return sharedPreferences.getBoolean("SoundSwitch",false)
    }

}