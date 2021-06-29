package com.lacklab.app.codetest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lacklab.app.codetest.data.MigoPass
import com.lacklab.app.codetest.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject internal constructor(
    private val walletRepository: WalletRepository
) : ViewModel(){
    val passesDay: LiveData<List<MigoPass>> =
        walletRepository.getPasses("Day").asLiveData()
    val passesHour: LiveData<List<MigoPass>> =
        walletRepository.getPasses("Hour").asLiveData()
    val apiStatus: LiveData<String> =
        walletRepository.getAPIStatus().asLiveData()
    fun insertPass(pass: MigoPass) {
        viewModelScope.launch {
            walletRepository.insertPass(pass)
        }
    }

    fun updatePass(pass: MigoPass) {
        viewModelScope.launch {
            walletRepository.updatePass(pass)
        }
    }
}