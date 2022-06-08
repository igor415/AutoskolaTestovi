package com.varivoda.igor.autokola_testovi2019.ui.test

import android.animation.LayoutTransition
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.common.ConnectionResult
import com.varivoda.igor.autokola_testovi2019.App
import com.varivoda.igor.autokola_testovi2019.MainActivity
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.data.model.FinishImage
import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences
import com.varivoda.igor.autokola_testovi2019.databinding.FragmentTestBinding
import com.varivoda.igor.autokola_testovi2019.ui.shared.vibratePhone
import com.varivoda.igor.autokola_testovi2019.ui.test.util.CancelExamPopup
import com.varivoda.igor.autokola_testovi2019.ui.test.util.MediaPlayerHelper
import java.lang.Exception


class TestFragment : Fragment() {

    lateinit var binding: FragmentTestBinding
    private val viewModel by viewModels<TestViewModel>{
        TestViewModelFactory((requireContext().applicationContext as App).mainRepository)
    }
    private var cancelExamPopup: AlertDialog? = null
    private var isTimerCalled = false
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var mediaPlayerHelper: MediaPlayerHelper
    private lateinit var preferences: Preferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        preferences = (requireContext().applicationContext as App).preferences
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestionListForTestId(TestFragmentArgs.fromBundle(requireArguments()).testId)
        mediaPlayerHelper = MediaPlayerHelper(this.lifecycle, requireContext())
        observeElapsedTime()
        observeCurrentQuestion()
        observeIsLastQuestion()
        observeQuestionResult()
        observeCheckBoxState()
        observeCloseTest()
        observeShowFinishState()
        val layoutTransition = binding.testContainer.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            cancelExamPopup?.cancel()
            cancelExamPopup = CancelExamPopup.getPopup(requireActivity(),requireContext(), false){
                findNavController().popBackStack()
            }
            cancelExamPopup?.show()
        }
        loadInterstitial()
    }

    private fun observeShowFinishState() = viewModel.showFinishState.observe(viewLifecycleOwner, Observer {
        if(it==null) return@Observer
        binding.finishTitle.text = if(viewModel.totalPositiveAnswers > 33 && !viewModel.crossroadFailed){
            binding.setFinishImage(FinishImage(R.drawable.ic_trophy_icon_icons_com_67206))
            "Čestitamo položili ste ispit sa rezultatom ${viewModel.totalPositiveAnswers}/38!"
        }else{
            binding.setFinishImage(FinishImage(R.drawable.ic_cancel_96921))
            if(viewModel.crossroadFailed){
                "Nažalost, niste položili ispit jer ste pogriješili na raskrižju!\nTočno ste odgovorili na ${viewModel.totalPositiveAnswers} pitanja!"
            }else{
                "Nažalost, niste položili ispit!\nTočno ste odgovorili na ${viewModel.totalPositiveAnswers} pitanja!"
            }

        }

    })

    private fun observeCloseTest() = viewModel.closeTest.observe(viewLifecycleOwner, Observer {
        if(it==null) return@Observer
        viewModel.clearCloseTest()
        findNavController().popBackStack()
    })

    private fun loadInterstitial(){
        try {
            var adRequest = AdRequest.Builder().build()

            InterstitialAd.load(requireContext(),"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
        }catch (ex: Exception){
            println(ex.localizedMessage)
        }

    }

    private fun observeCheckBoxState() = viewModel.checkBoxState.observe(viewLifecycleOwner, Observer {
        if(it==null) return@Observer
    })

    private fun observeQuestionResult() = viewModel.questionResult.observe(viewLifecycleOwner, Observer {
        if(it==null) return@Observer
        binding.rightAnswer.visibility = View.VISIBLE
        if(it.first == ""){
            if(!preferences.getSoundSwitch()) mediaPlayerHelper.correct.start()
            binding.rightAnswer.text = "Točan odgovor!"
            binding.rightAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.mainBlueColor))
            binding.rightAnswer.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_correct_apply_done_icon_176355,0,0)
        }else{
            this.vibratePhone(500)
            binding.rightAnswer.text = "Netočno! ${it.first}"
            binding.rightAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.redColorButton))
            binding.rightAnswer.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_wrong_close_remove_icon_176368,0,0)
        }
        viewModel.setRightAnswerShowing(true)
    })

    private fun observeIsLastQuestion() = viewModel.isLastQuestion.observe(viewLifecycleOwner, Observer {
        if(it==null) return@Observer
        binding.confirm.text = getString(R.string.close_exam)
    })

    private fun observeCurrentQuestion() {
        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer {
            if(it==null) return@Observer
            binding.rightAnswer.visibility = View.GONE
            viewModel.setRightAnswerShowing(false)
            viewModel.setImageResource(requireContext().resources.getIdentifier(
                it.first.imageResource,
                "drawable",
                requireActivity().packageName
            ))
            if(it.first.ordinalNumber in listOf(7,15,25,34)){
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                    loadInterstitial()
                } else {
                    loadInterstitial()
                }
            }
        })
    }

    private fun observeElapsedTime() {
        viewModel.currentTimeString.observe(viewLifecycleOwner, Observer {
            if(it==null) return@Observer
            if(it == "00:00" && !isTimerCalled){
                isTimerCalled = true
                cancelExamPopup?.cancel()
                cancelExamPopup = CancelExamPopup.getPopup(requireActivity(),requireContext(), true){
                    findNavController().popBackStack()
                }
                cancelExamPopup?.show()
            }
            if(it == "00:03" && !preferences.getSoundSwitch()){
                mediaPlayerHelper.countdown.start()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.showBottomLayout()
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideBottomLayout()
    }

}