package com.karumi.jetpack.superheroes.data.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SuperHeroDao {
    @Query("SELECT * FROM superheroes")
    suspend fun getAll(): List<SuperHeroEntity>

    @Query("SELECT * FROM superheroes WHERE id = :id")
    suspend fun getById(id: String): SuperHeroEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(superHeroes: List<SuperHeroEntity>)

    @Update
    suspend fun update(superHero: SuperHeroEntity)

    @Query("DELETE FROM superheroes")
    suspend fun deleteAll()
}