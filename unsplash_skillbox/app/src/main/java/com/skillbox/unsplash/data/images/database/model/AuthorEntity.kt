package com.skillbox.unsplash.data.images.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.database.contract.AuthorContract
import com.skillbox.unsplash.data.images.database.contract.AvatarContract
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = AuthorContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = AvatarEntity::class,
            parentColumns = [AvatarContract.Columns.ID],
            childColumns = [AuthorContract.Columns.AVATAR_ID]
        )
    ]
)
@Parcelize
data class AuthorEntity(
    @PrimaryKey
    @ColumnInfo(name = AuthorContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = AuthorContract.Columns.AVATAR_ID)
    val avatar_id: String,
    @ColumnInfo(name = AuthorContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = AuthorContract.Columns.NICKNAME)
    val nickName: String,
) : Parcelable