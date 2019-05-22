package com.karumi.jetpack.superheroes.data.repository

import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroEntity
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import java.util.concurrent.ExecutorService

class LocalSuperHeroDataSource(
    private val dao: SuperHeroDao,
    private val executor: ExecutorService
) {
    fun getAllSuperHeroes(): List<SuperHero> =
        dao.getAll()
            .map { it.toSuperHero() }

    fun get(id: String): SuperHero? =
        dao.getById(id)?.toSuperHero()

    fun saveAll(all: List<SuperHero>) = executor.execute {
        dao.deleteAll()
        dao.insertAll(all.map { it.toEntity() })
    }

    fun save(superHero: SuperHero): SuperHero {
        executor.execute { dao.update(superHero.toEntity()) }
        return superHero
    }

    private fun SuperHeroEntity.toSuperHero(): SuperHero = superHero

    private fun SuperHero.toEntity(): SuperHeroEntity = SuperHeroEntity(this)
}