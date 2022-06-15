package com.varivoda.igor.autokola_testovi2019.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.varivoda.igor.autokola_testovi2019.App
import com.varivoda.igor.autokola_testovi2019.MainActivity
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.databinding.FragmentHomeBinding
import com.varivoda.igor.autokola_testovi2019.ui.home.adapter.HomeAdapter
import com.varivoda.igor.autokola_testovi2019.ui.home.adapter.HomeClickListener


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>{
        HomeViewModelFactory((requireContext().applicationContext as App).mainRepository)
    }
    //private lateinit var mainViewModel: MainViewModel
   // private lateinit var mainViewModelFactory: MainViewModelFactory

    private val homeAdapter = HomeAdapter(HomeClickListener { id ->
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTestFragment(id))
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mainViewModelFactory = MainViewModelFactory(requireContext())
        //mainViewModel = ViewModelProvider(this,mainViewModelFactory).get(MainViewModel::class.java)
        binding.testsRecyclerView.adapter = homeAdapter
        observeTests()
        viewModel.getAllTests()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            requireActivity().finish()
        }
        viewModel.allTestsModified.observe(viewLifecycleOwner, Observer {
            if(it==null)return@Observer
            println(it)
        })
    }

    private fun observeTests() {
        viewModel.allTests.observe(viewLifecycleOwner, Observer {
            if(it==null) return@Observer
            homeAdapter.addHeaderAndSubmitList(it)
        })
    }

    override fun onStart() {
        super.onStart()
        (activity as? MainActivity)?.showBottomLayout()
    }




}