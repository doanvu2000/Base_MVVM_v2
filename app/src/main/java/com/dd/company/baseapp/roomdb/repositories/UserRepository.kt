package com.dd.company.baseapp.roomdb.repositories

import com.dd.company.baseapp.roomdb.model.user.UserDao
import com.dd.company.baseapp.roomdb.model.user.UserEntity

class UserRepository constructor(private val userDao: UserDao) {
    suspend fun insertUser(userEntity: UserEntity) = userDao.insert(userEntity)
}