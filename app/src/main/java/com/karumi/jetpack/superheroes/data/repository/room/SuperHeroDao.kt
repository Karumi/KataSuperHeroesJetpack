package com.karumi.jetpack.superheroes.data.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuperHeroDao {
    @Query("SELECT * FROM superheroes ORDER BY id ASC")
    fun getAll(): List<SuperHeroEntity>

    @Query("SELECT * FROM superheroes WHERE id = :id")
    fun getById(id: String): SuperHeroEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(superHeroes: List<SuperHeroEntity>)

    @Query("DELETE FROM superheroes")
    fun deleteAll()
}