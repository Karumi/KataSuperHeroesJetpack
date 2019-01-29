package com.karumi.jetpack.superheroes.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class], version = 2)
abstract class SuperHeroesDatabase : RoomDatabase() {
    abstract fun superHeroesDao(): SuperHeroDao

    companion object {
        fun build(context: Context): SuperHeroesDatabase =
            Room.databaseBuilder(context, SuperHeroesDatabase::class.java, "superheroes-db")
                .addMigrations(from1To2)
                .build()
    }
}

private val from1To2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE superheroes")
        database.execSQL(
            """
            CREATE TABLE `superheroes` (
                `id` TEXT NOT NULL,
                `superhero_id` TEXT NOT NULL,
                `superhero_name` TEXT NOT NULL,
                `superhero_photo` TEXT,
                `superhero_isAvenger` INTEGER NOT NULL DEFAULT 0,
                `superhero_description` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
        """
        )
    }
}