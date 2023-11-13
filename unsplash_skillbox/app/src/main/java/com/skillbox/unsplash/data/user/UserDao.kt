package com.skillbox.unsplash.data.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.skillbox.unsplash.data.images.room.model.UserRoomModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserRoomModel>)

    @Delete
    fun deleteUsers(users: List<UserRoomModel>)
}