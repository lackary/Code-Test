package com.lacklab.app.codetest.repository

import com.lacklab.app.codetest.api.MigoincService
import com.lacklab.app.codetest.dao.PassDao
import com.lacklab.app.codetest.data.MigoPass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val passDao: PassDao,
    private val migoincService: MigoincService
) {
    fun getPasses(passType: String) = passDao.getPasses(passType)

    suspend fun insertPass(pass: MigoPass) = passDao.insertPass(pass)

    suspend fun updatePass(pass: MigoPass) = passDao.updatePass(pass)

    fun getAPIStatus(): Flow<String> {
        return flow {
            val response = migoincService.getStatus()
            emit(response.body()?.message)
        } as Flow<String>
    }
}