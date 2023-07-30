package com.skillbox.unsplash.data.images.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.database.contract.PreviewContact
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity(tableName = PreviewContact.TABLE_NAME)
@Parcelize
data class PreviewEntity(
    @PrimaryKey
    @ColumnInfo(name = PreviewContact.Columns.ID)
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = PreviewContact.Columns.RAW)
    val raw: String,
    @ColumnInfo(name = PreviewContact.Columns.FULL)
    val full: String,
    @ColumnInfo(name = PreviewContact.Columns.REGULAR)
    val regular: String,
    @ColumnInfo(name = PreviewContact.Columns.SMALL)
    val small: String,
    @ColumnInfo(name = PreviewContact.Columns.THUMB)
    val thumb: String
) : Parcelable
