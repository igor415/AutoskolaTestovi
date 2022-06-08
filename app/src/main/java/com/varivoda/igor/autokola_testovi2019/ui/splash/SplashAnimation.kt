package com.varivoda.igor.autokola_testovi2019.ui.splash

import android.animation.*
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.varivoda.igor.autokola_testovi2019.R

class SplashAnimation {

    fun create(textView: TextView, context: Context, doEndWork: () -> Unit){
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2.5f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            textView, scaleX, scaleY)
        animator.startDelay = 1500
        animator.duration = 800
        animator.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                doEndWork.invoke()
            }
        })

        val colorFrom: Int = Color.WHITE
        val colorTo: Int = ContextCompat.getColor(context, R.color.mainBlueColor)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 800
        colorAnimation.startDelay = 1500

        colorAnimation.addUpdateListener { animatorItem ->

            textView.setTextColor(animatorItem.animatedValue as Int) }

        val rotateAnimator = ObjectAnimator.ofFloat(textView, View.ROTATION, 0f, 25f)
        rotateAnimator.duration = 200
        rotateAnimator.repeatCount = 1
        rotateAnimator.repeatMode = ObjectAnimator.REVERSE
        rotateAnimator.startDelay = 1500

        val rotateAnimatorReverse = ObjectAnimator.ofFloat(textView, View.ROTATION, 0f, -25f)
        rotateAnimatorReverse.duration = 200
        rotateAnimatorReverse.repeatCount = 1
        rotateAnimatorReverse.repeatMode = ObjectAnimator.REVERSE

        rotateAnimator.addListener(object: AnimatorListenerAdapter(){

            override fun onAnimationEnd(animation: Animator?) {
                rotateAnimatorReverse.start()
            }
        })



        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator,colorAnimation, rotateAnimator)
        animatorSet.start()
    }
}