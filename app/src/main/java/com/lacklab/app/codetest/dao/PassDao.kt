package com.lacklab.app.codetest.dao

import androidx.room.*
import com.lacklab.app.codetest.data.MigoPass
import kotlinx.coroutines.flow.Flow

@Dao
interface PassDao {

    @Insert
    suspend fun insertPasses(passes: List<MigoPass>)

    @Insert
    suspend fun insertPass(pass: MigoPass)

    @Delete
    suspend fun deletePass(pass: MigoPass)

    @Update
    suspend fun updatePass(pass: MigoPass)

    @Query("SELECT * FROM pass WHERE pass_type = :passType ORDER BY id DESC" )
    fun getPasses(passType: String): Flow<List<MigoPass>>

    @Query("SELECT * FROM pass WHERE id = :id")
    suspend fun getPass(id: Long): MigoPass
}