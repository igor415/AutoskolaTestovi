package com.varivoda.igor.autokola_testovi2019

import android.animation.*
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.varivoda.igor.autokola_testovi2019.databinding.ActivityMainBinding
import com.varivoda.igor.autokola_testovi2019.notifications.createChannel


class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private var lastSelected: CardView? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) { //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        this.createChannel()

        navController = Navigation.findNavController(this, R.id.myNavHostFragment)

        binding.advice.setOnClickListener {
            if(lastSelected != binding.adviceParent){
                changeSelectedLayout(binding.advice)
                navController!!.currentDestination?.let { navController!!.popBackStack(it.id, true) }
                navController!!.navigate(R.id.adviceFragment)
                setUnselectedLayout(binding.advice, listOf(binding.home, binding.settingsFragment))
            }
        }
        binding.home.setOnClickListener {
            if(lastSelected != binding.homeParent){
                changeSelectedLayout(binding.home)
                navController!!.currentDestination?.let { navController!!.popBackStack(it.id, true) }
                navController!!.navigate(R.id.homeFragment)
                setUnselectedLayout(binding.home, listOf(binding.advice, binding.settingsFragment))
            }
        }
        binding.settingsFragment.setOnClickListener {
            if(lastSelected != binding.settingsFragmentParent){
                changeSelectedLayout(binding.settingsFragment)
                navController!!.currentDestination?.let { navController!!.popBackStack(it.id, true) }
                navController!!.navigate(R.id.settingsFragment2)
                setUnselectedLayout(binding.settingsFragment, listOf(binding.advice, binding.home))
            }
        }
    }

    private fun startColorAnimation(textView: CardView){
        val colorFrom: Int = Color.parseColor("#01102D")
        val colorTo: Int = Color.parseColor("#F6F9AA")
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 350

        colorAnimation.addUpdateListener { animatorItem ->

            textView.setCardBackgroundColor(animatorItem.animatedValue as Int)
        }

        val colorAnimationReverse = ValueAnimator.ofObject(ArgbEvaluator(), colorTo, colorFrom)
        colorAnimationReverse.duration = 250
        colorAnimationReverse.addUpdateListener { item ->
            lastSelected?.setCardBackgroundColor(item.animatedValue as Int)
        }
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                binding.advice.isEnabled = false
                binding.home.isEnabled = false
                binding.settingsFragment.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.advice.isEnabled = true
                binding.home.isEnabled = true
                binding.settingsFragment.isEnabled = true
            }
        })
        colorAnimationReverse.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                binding.advice.isEnabled = false
                binding.home.isEnabled = false
                binding.settingsFragment.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.advice.isEnabled = true
                binding.home.isEnabled = true
                binding.settingsFragment.isEnabled = true
                lastSelected = textView
            }
        })
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(colorAnimation, colorAnimationReverse)
        animatorSet.start()
    }


    fun showHomeActivated(){
        changeSelectedLayout(binding.home)
        setUnselectedLayout(binding.home, listOf(binding.advice, binding.settingsFragment))
    }

    private fun setUnselectedLayout(selectedTextView: TextView, list: List<TextView>) {
        if(selectedTextView.id == R.id.advice){
            binding.home.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_home_color_white,
                0,
                0,
                0
            )
            binding.settingsFragment.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_settings_white,
                0,
                0,
                0
            )
        }else if(selectedTextView.id == R.id.home){
            binding.advice.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_home_color_white,
                0,
                0,
                0
            )
            binding.settingsFragment.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_settings_white,
                0,
                0,
                0
            )
        }else{
            binding.advice.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_home_color_white,
                0,
                0,
                0
            )
            binding.home.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_home_color_white,
                0,
                0,
                0
            )
        }
        list.forEach { textView ->
            when(textView.id){
                R.id.advice -> {
                    binding.adviceParent.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.mainBlueColor
                        )
                    )
                    textView.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.bottomBarUnselectedColor
                        )
                    )
                }
                R.id.home -> {
                    binding.homeParent.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.mainBlueColor
                        )
                    )
                    textView.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.bottomBarUnselectedColor
                        )
                    )
                }
                R.id.settingsFragment -> {
                    binding.settingsFragmentParent.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.mainBlueColor
                        )
                    )
                    textView.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.bottomBarUnselectedColor
                        )
                    )
                }
            }
        }
    }

    private fun changeSelectedLayout(textView: TextView?) {
        textView ?: return
        when(textView.id){
            R.id.advice -> {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_home_icon_blue,
                    0,
                    0,
                    0
                )
                //textView.setText(R.string.bottom_navigation_archive)
                textView.setTextColor(ContextCompat.getColor(this, R.color.loginBackgroundColor))
                startColorAnimation(binding.adviceParent)


            }
            R.id.home -> {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_home_icon_blue,
                    0,
                    0,
                    0
                )
                //textView.setText(R.string.bottom_navigation_scanner)
                textView.setTextColor(ContextCompat.getColor(this, R.color.loginBackgroundColor))
                startColorAnimation(binding.homeParent)

            }
            R.id.settingsFragment -> {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_settings_blue,
                    0,
                    0,
                    0
                )
                //textView.setText(R.string.bottom_navigation_settings)
                textView.setTextColor(ContextCompat.getColor(this, R.color.loginBackgroundColor))
                startColorAnimation(binding.settingsFragmentParent)

            }
        }


    }


    fun hideBottomLayout(){
        binding.bottomLayout.visibility = View.GONE
    }

    fun showBottomLayout(){
        binding.bottomLayout.visibility = View.VISIBLE
    }

   /*override fun onResume() {
        super.onResume()
        val LaunchIntent =
            this.getPackageManager().getLaunchIntentForPackage("com.trilix.voucherterminalmobile.demo")
        LaunchIntent?.putExtra("isExitable",true)
       LaunchIntent?.putExtra("exitAppPackage","com.salesperitias.sp101kiosk")
       LaunchIntent?.putExtra("maxIdleTime",10)
        startActivity(LaunchIntent)
    }*/
}