package com.skillbox.unsplash.data.images.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.database.contract.AuthorContract
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Entity(tableName = AuthorContract.TABLE_NAME)
@Parcelize
data class AuthorEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = AuthorContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = AuthorContract.Columns.AVATAR_ID)
    val avatar_id: String,
    @ColumnInfo(name = AuthorContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = AuthorContract.Columns.NICKNAME)
    val nickName: String,
) : Parcelable