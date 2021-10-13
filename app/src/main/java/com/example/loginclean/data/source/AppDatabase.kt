package com.example.loginclean.data.source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlumnosEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun alumnosDao(): AlumnosDao
}