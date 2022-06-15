package com.varivoda.igor.autokola_testovi2019.ui.advice

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.databinding.FragmentAdviceBinding


class AdviceFragment : Fragment() {

    private lateinit var binding: FragmentAdviceBinding
    private lateinit var adView: AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_advice, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            requireActivity().finish()
        }
        adView = AdView(requireContext())
        binding.adViewContainer.addView(adView)
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        loadBanner()
    }

    private fun loadBanner() {
        val adRequest = AdRequest.Builder().build()
        val adSize = getAdSize()
        if (adSize != null) {
            adView.setAdSize(adSize)
        }
        adView.loadAd(adRequest)
    }

    private fun getAdSize(): AdSize? {
        val display: Display = requireActivity().windowManager.getDefaultDisplay()
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density

        val adWidth = (widthPixels / density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireContext(), adWidth)
    }

}