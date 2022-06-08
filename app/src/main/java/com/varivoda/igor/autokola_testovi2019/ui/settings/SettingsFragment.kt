package com.varivoda.igor.autokola_testovi2019.ui.settings

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.varivoda.igor.autokola_testovi2019.App
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences
import com.varivoda.igor.autokola_testovi2019.databinding.FragmentSettingsBinding
import com.varivoda.igor.autokola_testovi2019.ui.shared.toast


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    private var toastDesign: AlertDialog? = null
    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        preferences = (requireContext().applicationContext as App).preferences
        binding.pref = preferences
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.versionApp.text = try {
            requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0).versionName
        }catch (ex: Exception){
            ""
        }
        binding.soundSwitch.isChecked = preferences.getSoundSwitch()
        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferences.saveSoundSwitch(isChecked)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            requireActivity().finish()
        }

        binding.link1.setOnClickListener {
            openExternalWebsite("https://play.google.com/store/apps/developer?id=Igor+Varivoda")
        }
        binding.rateApp.setOnClickListener {
            val uri = Uri.parse("market://details?id=" + requireActivity().packageName)
            val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(myAppLinkToMarket)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), " unable to find market app", Toast.LENGTH_LONG).show()
            }
        }

        binding.toastDesignContainer.setOnClickListener {
            toastDesign?.cancel()
            toastDesign = ToastDesignPopup().open(requireContext(), requireActivity(), preferences.getToastDesign()){
                preferences.saveToastDesign(it)
                binding.pref = preferences
                context?.toast("Uspješno ste promijenili temu obavijesti.", preferences,true)
            }
            toastDesign?.show()
        }

    }

    private fun openExternalWebsite(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}