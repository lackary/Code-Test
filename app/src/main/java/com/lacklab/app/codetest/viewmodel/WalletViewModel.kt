package com.lacklab.app.codetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lacklab.app.codetest.data.MigoPass
import com.lacklab.app.codetest.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject internal constructor(
    private val walletRepository: WalletRepository
) : ViewModel(){
    val passesDay: LiveData<List<MigoPass>> =
        walletRepository.getPasses("day").asLiveData()
    val passesHour: LiveData<List<MigoPass>> =
        walletRepository.getPasses("hour").asLiveData()
}