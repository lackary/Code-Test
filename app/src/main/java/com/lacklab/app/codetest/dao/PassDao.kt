package com.lacklab.app.codetest.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lacklab.app.codetest.data.MigoPass
import kotlinx.coroutines.flow.Flow

@Dao
interface PassDao {

    @Insert
    fun insertPasses(passes: List<MigoPass>)

    @Insert
    fun insertPass(pass: MigoPass)

    @Delete
    fun deletePass(pass: MigoPass)

    @Query("SELECT * FROM pass WHERE pass_type = :passType" )
    fun getPasses(passType: String): Flow<List<MigoPass>>
}