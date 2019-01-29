package com.karumi.jetpack.superheroes.data.repository

import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroEntity
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class LocalSuperHeroDataSource(
    private val dao: SuperHeroDao
) {
    suspend fun getAllSuperHeroes(): List<SuperHero> =
        dao.getAll()
            .map { it.toSuperHero() }

    suspend fun get(id: String): SuperHero? =
        dao.getById(id)?.toSuperHero()

    suspend fun saveAll(all: List<SuperHero>) {
        dao.deleteAll()
        dao.insertAll(all.map { it.toEntity() })
    }

    suspend fun save(superHero: SuperHero): SuperHero {
        dao.update(superHero.toEntity())
        return superHero
    }

    private fun SuperHeroEntity.toSuperHero(): SuperHero = superHero

    private fun SuperHero.toEntity(): SuperHeroEntity = SuperHeroEntity(id, this)
}