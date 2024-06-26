package com.skillbox.unsplash.data.remote.retrofit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skillbox.unsplash.data.local.db.entities.UserEntity
import com.skillbox.unsplash.data.local.db.entities.user.UserContract

@Dao
interface UserApi {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Delete
    fun deleteUsers(users: List<UserEntity>)

    @Query("DELETE FROM ${UserContract.TABLE_NAME}")
    fun clearAll();
}