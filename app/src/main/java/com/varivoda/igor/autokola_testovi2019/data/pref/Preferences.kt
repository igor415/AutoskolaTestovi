package com.varivoda.igor.autokola_testovi2019.data.pref

interface Preferences {
    fun saveToastDesign(string: String)
    fun getToastDesign(): String
    fun saveSoundSwitch(boolean: Boolean)
    fun getSoundSwitch(): Boolean
}