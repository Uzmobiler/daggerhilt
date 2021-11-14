package uz.mobiler.daggerhilt.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import uz.mobiler.daggerhilt.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertUsers(list: List<UserEntity>)

    @Query("select * from userentity")
    suspend fun getUsers(): List<UserEntity>
}