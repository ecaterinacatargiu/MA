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
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="level") val level: Int,
    @ColumnInfo(name="status") val status: String,
    @ColumnInfo(name="from") val from: Int,
    @ColumnInfo(name="to") val to: Int

): Parcelable