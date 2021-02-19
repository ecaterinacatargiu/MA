package com.example.lab1.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "examBackup")
data class ExamBackup (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="group") val group: String,
    @ColumnInfo(name="details") val details: String,
    @ColumnInfo(name="status") val status: String,
    @ColumnInfo(name="students") val students: Int,
    @ColumnInfo(name="type") val type: String

): Parcelable