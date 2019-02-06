package com.karumi.jetpack.superheroes.data.repository.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface SuperHeroDao {
    @Query("SELECT * FROM superheroes")
    fun getAllSuperHeroes(): List<SuperHeroEntity>

    @Query("SELECT * FROM superheroes ORDER BY superhero_id ASC")
    fun getAll(): DataSource.Factory<Int, SuperHeroEntity>

    @Query("SELECT * FROM superheroes WHERE superhero_id = :id")
    fun getById(id: String): LiveData<SuperHeroEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(superHeroes: List<SuperHeroEntity>)

    @Update
    fun update(superHero: SuperHeroEntity)

    @Query("DELETE FROM superheroes")
    fun deleteAll()

    @Query("DELETE FROM superheroes WHERE superhero_id IN (:ids)")
    fun deleteAll(ids: List<String>)

    @Transaction
    fun deleteHalf() {
        val superHeroes = getAllSuperHeroes()

        if (superHeroes.size < 2) {
            return
        }

        val randomSuperHeroIds = superHeroes
            .shuffled()
            .take(superHeroes.size / 2)
            .map { it.superHero.id }

        deleteAll(randomSuperHeroIds)
    }
}