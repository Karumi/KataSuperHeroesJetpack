package com.karumi.jetpack.superheroes.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class], version = SuperHeroesDatabase.version)
abstract class SuperHeroesDatabase : RoomDatabase() {
    abstract fun superHeroesDao(): SuperHeroDao

    companion object {
        const val version = 2
        fun build(context: Context): SuperHeroesDatabase =
            Room.databaseBuilder(context, SuperHeroesDatabase::class.java, "superheroes-db")
                .addMigrations(*Migrations.all)
                .build()
    }
}

object Migrations {
    val from1To2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE `superheroes_temp` (
                `superhero_id` TEXT NOT NULL,
                `superhero_name` TEXT NOT NULL,
                `superhero_photo` TEXT,
                `superhero_isAvenger` INTEGER NOT NULL DEFAULT 0,
                `superhero_description` TEXT NOT NULL,
                PRIMARY KEY(`superhero_id`)
            )
        """
            )
            database.execSQL(
                """
            INSERT INTO `superheroes_temp`(
                `superhero_id`,
                `superhero_name`,
                `superhero_photo`,
                `superhero_isAvenger`,
                `superhero_description`
            ) SELECT
                `id`,
                `name`,
                `photo`,
                `isAvenger`,
                `description`
            FROM `superheroes`"""
            )
            database.execSQL("DROP TABLE superheroes")
            database.execSQL("ALTER TABLE `superheroes_temp` RENAME TO `superheroes`")
        }
    }

    val all = arrayOf(from1To2)
}