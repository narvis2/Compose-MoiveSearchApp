package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileInfoModel(
    var name: String,
    val size: Long = -1L,
    val mimType: String
): Parcelable
