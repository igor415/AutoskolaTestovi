package com.varivoda.igor.autokola_testovi2019.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val mainRepositoryInterface: MainRepositoryInterface,
                            private val workManager: WorkManager): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(mainRepositoryInterface, workManager) as T
        }
        throw UnsupportedOperationException("")
    }
}