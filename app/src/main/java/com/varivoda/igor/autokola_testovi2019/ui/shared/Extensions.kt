package com.varivoda.igor.autokola_testovi2019.ui.shared

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences
import com.varivoda.igor.autokola_testovi2019.databinding.ToastDesignPopupBinding
import com.varivoda.igor.autokola_testovi2019.databinding.ToastLayoutBinding


inline val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

@Suppress("Deprecation")
fun FragmentActivity.setFullSizeDialog(dialogView: View){
    val mainWindow: Window = this.window
    val metrics = DisplayMetrics()
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        this.display?.getRealMetrics(metrics)
    }else{
        mainWindow.windowManager?.defaultDisplay?.getRealMetrics(metrics)
    }
    dialogView.minimumHeight = (metrics.heightPixels * 1f).toInt()
    dialogView.minimumWidth = (metrics.widthPixels * 1f).toInt()

}

fun AlertDialog.setWindowAttributes(){
    this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val window = this.window
    val wlp = window!!.attributes
    wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
    window.attributes = wlp
}

@Suppress("Deprecation")
fun Context?.toast(text: String, preferences: Preferences, changeTitle: Boolean = false){
    val toast = Toast(this)
    val binding = DataBindingUtil.inflate<ToastLayoutBinding>( LayoutInflater.from(this),R.layout.toast_layout, null, false)

    val tvMessage = binding.textview
    tvMessage.text = text
    if(changeTitle){
        binding.toastTitle.visibility = View.GONE
    }else{
        binding.toastTitle.visibility = View.VISIBLE
    }
    when(preferences.getToastDesign()){
        "Default" -> {    binding.toastContainer.setBackgroundResource(R.drawable.toast_outline)
            binding.toastTitle.setTextColor(Color.WHITE)
            tvMessage.setTextColor(Color.WHITE)
        }
        "Dnevna" -> {    binding.toastContainer.setBackgroundResource(R.drawable.light_toast_outline)
            binding.toastTitle.setTextColor(Color.parseColor("#01102D"))
            tvMessage.setTextColor(Color.parseColor("#01102D"))
        }
        "Noćna" -> {    binding.toastContainer.setBackgroundResource(R.drawable.dark_toast_outline)
            binding.toastTitle.setTextColor(Color.WHITE)
            tvMessage.setTextColor(Color.WHITE)

        }
    }
    toast.duration = Toast.LENGTH_LONG
    toast.view = binding.root
    toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM,100,0)
    toast.show()
}

@Suppress("Deprecation")
fun Fragment.vibratePhone(value: Long) {
    val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(value, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(300)
    }
}