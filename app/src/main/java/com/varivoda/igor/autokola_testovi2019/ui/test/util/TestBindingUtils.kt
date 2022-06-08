package com.varivoda.igor.autokola_testovi2019.ui.test.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity

@BindingAdapter("imageDrawable")
fun ImageView.setDrawableFromStringIdentifier(imageResource: Int){
    if(imageResource == 0){
        this.visibility = View.GONE
    }else{
        this.visibility = View.VISIBLE
        this.setImageResource(imageResource)
    }
}

@BindingAdapter("imgRes")
fun ImageView.setImageResourceFile(resource: Int) {
    this.setImageResource(resource)
}
