package com.lacklab.app.codetest.repository

import android.util.Log
import com.lacklab.app.codetest.MainApplication
import com.lacklab.app.codetest.api.MigoPrivateService
import com.lacklab.app.codetest.api.MigoincService
import com.lacklab.app.codetest.dao.PassDao
import com.lacklab.app.codetest.data.MigoPass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val passDao: PassDao,
    private val migoincService: MigoincService,
    private val migoincPrivateService: MigoPrivateService
) {
    fun getPasses(passType: String): Flow<List<MigoPass>> {
        Log.d("WalletRepository", "getPasses() type:$passType")
        return passDao.getPasses(passType)
    }

    suspend fun getPass(id: Long): MigoPass = passDao.getPass(id)

//    fun getPass(id: Long): Flow<MigoPass> {
//        Log.d("WalletRepository", "getPass() id: $id")
//        return passDao.getPass(id)
//    }

    suspend fun insertPass(pass: MigoPass) = passDao.insertPass(pass)

    suspend fun updatePass(pass: MigoPass) = passDao.updatePass(pass)

    fun getAPIStatus(): Flow<String> {
        return try {
            flow {
                val response = if (MainApplication.connectType == "MOBILE") {
                    migoincService.getStatus()
                } else {
                    migoincPrivateService.getStatus()
                }
                emit(response.body()?.message)
            } as Flow<String>
        } catch (e: SocketTimeoutException) {
            flow {
                emit("FAILED")
            }
        } catch (e: Exception) {
            flow {
                emit("FAILED")
            }
        }
    }
}