package com.varivoda.igor.autokola_testovi2019.ui.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.varivoda.igor.autokola_testovi2019.MainActivity
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.motion.addTransitionListener(object: CustomMotionLayoutListener(){
            override fun doOnEnd(motionLayout: MotionLayout?, currentId: Int) {
                lifecycleScope.launch(Dispatchers.IO){
                    delay(150)
                    withContext(Dispatchers.Main){
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                    }
                }
            }

        })

        lifecycleScope.launch(Dispatchers.IO){
            delay(750)
            withContext(Dispatchers.Main){
                binding.motion.transitionToEnd()
            }
        }
        /*SplashAnimation().create(binding.splashName, requireContext()){
            lifecycleScope.launch(Dispatchers.IO){
                delay(500)
                withContext(Dispatchers.Main){
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }
            }
        }*/
    }

    override fun onStart() {
        super.onStart()
        (activity as? MainActivity)?.hideBottomLayout()
    }

}