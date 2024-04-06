package com.skillbox.unsplash.data.user.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skillbox.unsplash.data.user.room.contract.UserContract
import com.skillbox.unsplash.domain.model.db.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Delete
    fun deleteUsers(users: List<UserEntity>)

    @Query("DELETE FROM ${UserContract.TABLE_NAME}")
    fun clearAll();
}