package com.lacklab.app.codetest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pass")
data class MigoPass(
    @ColumnInfo(name = "pass_type")
    val passType: String,
    val number: Long,
    val prices: Double,
    @ColumnInfo(name = "passe_status")
    val passeStatus: String,
    @ColumnInfo(name = "pass_activation")
    val passActivation: String? = null,
    @ColumnInfo(name = "insertion_date")
    val insertionDate: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "expiration_time")
    val expirationTime: Calendar? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}