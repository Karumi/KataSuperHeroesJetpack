package com.karumi.jetpack.superheroes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroEntity
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import java.util.concurrent.ExecutorService

class LocalSuperHeroDataSource(
    private val dao: SuperHeroDao,
    private val executor: ExecutorService
) {
    fun getAllSuperHeroes(
        pageSize: Int,
        boundaryCallback: PagedList.BoundaryCallback<SuperHero>
    ): LiveData<PagedList<SuperHero>> {
        val superHeroesFactory = dao.getAll().map { it.toSuperHero() }
        return LivePagedListBuilder(superHeroesFactory, pageSize)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    fun get(id: String): LiveData<SuperHero?> =
        Transformations.map(dao.getById(id)) { it?.toSuperHero() }

    fun saveAll(all: List<SuperHero>) = executor.execute {
        dao.insertAll(all.map { it.toEntity() })
    }

    fun save(superHero: SuperHero): SuperHero {
        executor.execute { dao.update(superHero.toEntity()) }
        return superHero
    }

    private fun SuperHeroEntity.toSuperHero(): SuperHero = superHero
    private fun SuperHero.toEntity(): SuperHeroEntity = SuperHeroEntity(this)
}