package com.karumi.jetpack.superheroes.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class], version = 1)
abstract class SuperHeroesDatabase : RoomDatabase() {
    abstract fun superHeroesDao(): SuperHeroDao
}