package com.karumi.jetpack.superheroes.data.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SuperHeroDao {
    @Query("SELECT * FROM superheroes ORDER BY superhero_id ASC")
    fun getAll(): LiveData<List<SuperHeroEntity>>

    @Query("SELECT * FROM superheroes WHERE superhero_id = :id")
    fun getById(id: String): LiveData<SuperHeroEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(superHeroes: List<SuperHeroEntity>)

    @Update
    fun update(superHero: SuperHeroEntity)

    @Query("DELETE FROM superheroes")
    fun deleteAll()
}