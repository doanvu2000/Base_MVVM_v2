package com.dd.company.baseapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dd.company.baseapp.roomdb.model.user.UserDao
import com.dd.company.baseapp.roomdb.model.user.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDB :RoomDatabase() {
    abstract fun userDao() : UserDao
}