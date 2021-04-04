package com.example.github.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.github.contract.UserContract
import com.example.github.model.FavoriteInfo

@Database(entities = [FavoriteInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favorite(): UserContract.Favorite

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database-favorite"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}