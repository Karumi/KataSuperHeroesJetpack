package com.karumi.jetpack.superheroes.data.repository

import com.karumi.jetpack.superheroes.domain.model.SuperHero
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class SuperHeroRepository(
    private val local: LocalSuperHeroDataSource,
    private val remote: RemoteSuperHeroDataSource
) {
    suspend fun getAllSuperHeroes(): List<SuperHero> =
        local.getAllSuperHeroes().ifEmpty {
            remote.getAllSuperHeroes()
                .also { local.saveAll(it) }
        }

    suspend fun get(id: String): SuperHero? =
        local.get(id)
            ?: remote.get(id)?.also { local.save(it) }

    suspend fun save(superHero: SuperHero): SuperHero = coroutineScope {
        listOf(
            launch { local.save(superHero) },
            launch { remote.save(superHero) }
        ).joinAll()

        superHero
    }
}