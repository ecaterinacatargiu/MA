package com.example.lab1.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "entityBackup")
data class EntityBackup (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name="number") val number: String,
    @ColumnInfo(name="address") val address: String,
    @ColumnInfo(name="status") val status: String,
    @ColumnInfo(name="int") val count: Int
): Parcelable