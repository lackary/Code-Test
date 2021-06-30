package com.lacklab.app.codetest.repository

import android.util.Log
import com.lacklab.app.codetest.MainApplication
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
    fun getPasses(passType: String): Flow<List<MigoPass>> {
        Log.i("WalletRepository", "getPasses()")
        return passDao.getPasses(passType)
    }

    suspend fun insertPass(pass: MigoPass) = passDao.insertPass(pass)

    suspend fun updatePass(pass: MigoPass) = passDao.updatePass(pass)

    fun getAPIStatus(): Flow<String> {
        return try {
            flow {
                val url = if (MainApplication.connectType == "MOBILE") {
                    "https://code-test.migoinc-dev.com/"
                } else {
                    "https://192.168.2.2/"
                }
                Log.i("repository", url)
                val response = migoincService.getStatus()
                emit(response.body()?.message)
            } as Flow<String>
        } catch (e: Exception) {
            flow {
                emit("FAILED")
            }
        }
    }
}