package uz.mobiler.daggerhilt.repository

import kotlinx.coroutines.flow.flow
import uz.mobiler.daggerhilt.database.dao.UserDao
import uz.mobiler.daggerhilt.database.entity.UserEntity
import uz.mobiler.daggerhilt.networking.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    suspend fun getUsers() = flow { emit(apiService.getUsers()) }

    suspend fun insertUsers(list: List<UserEntity>) = userDao.insertUsers(list)

    suspend fun getDbUsers() = userDao.getUsers()
}