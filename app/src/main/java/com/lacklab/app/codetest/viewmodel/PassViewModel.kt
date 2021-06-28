package com.lacklab.app.codetest.viewmodel

import androidx.lifecycle.ViewModel
import com.lacklab.app.codetest.data.MigoPass
import com.lacklab.app.codetest.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PassViewModel @Inject internal constructor(
    private val walletRepository: WalletRepository
) : ViewModel(){
    fun updatePass(pass: MigoPass) {
        walletRepository.updatePass(pass)
    }
}