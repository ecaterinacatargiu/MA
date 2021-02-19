package com.example.lab1.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Idu(var idu:Int): Parcelable
{
    @SerializedName("id")
    public var id = idu
}