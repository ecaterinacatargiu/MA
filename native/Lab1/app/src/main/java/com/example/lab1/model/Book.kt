package com.example.lab1.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//a table in room db
@Parcelize
@Entity(tableName = "book")
data class Book(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="author") val author: String,
    @ColumnInfo(name="year") val year: Int,
    @ColumnInfo(name="description") val description: String,
    @ColumnInfo(name="rating") val rating: Int
): Parcelable