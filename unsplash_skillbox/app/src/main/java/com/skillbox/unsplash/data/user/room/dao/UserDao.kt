package com.skillbox.unsplash.data.user.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.skillbox.unsplash.data.user.room.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Delete
    fun deleteUsers(users: List<UserEntity>)
}