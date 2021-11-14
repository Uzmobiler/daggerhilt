package uz.mobiler.daggerhilt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.mobiler.daggerhilt.database.dao.UserDao
import uz.mobiler.daggerhilt.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}