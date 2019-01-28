package com.karumi.jetpack.superheroes.data.repository

import com.karumi.jetpack.superheroes.domain.model.SuperHero
import kotlinx.coroutines.delay

class LocalSuperHeroDataSource {
    companion object {
        private const val BIT_TIME = 250L
    }

    private val superHeroes: MutableMap<String, SuperHero> = mutableMapOf()

    suspend fun getAllSuperHeroes(): List<SuperHero> {
        waitABit()
        return superHeroes.values.toList()
    }

    suspend fun get(id: String): SuperHero? {
        waitABit()
        return superHeroes[id]
    }

    suspend fun saveAll(all: List<SuperHero>) {
        waitABit()
        superHeroes.clear()
        superHeroes.putAll(all.associateBy { it.id })
    }

    suspend fun save(superHero: SuperHero): SuperHero {
        waitABit()
        superHeroes[superHero.id] = superHero
        return superHero
    }

    private suspend fun waitABit() {
        delay(BIT_TIME)
    }
}