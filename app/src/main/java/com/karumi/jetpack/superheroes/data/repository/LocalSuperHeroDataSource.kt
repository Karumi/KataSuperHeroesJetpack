package com.karumi.jetpack.superheroes.data.repository

import com.karumi.jetpack.superheroes.domain.model.SuperHero
import java.util.concurrent.ExecutorService

class LocalSuperHeroDataSource(
    private val executor: ExecutorService
) {
    companion object {
        private const val BIT_TIME = 250L
    }

    private val superHeroes: MutableMap<String, SuperHero> = mutableMapOf()

    fun getAllSuperHeroes(): List<SuperHero> {
        waitABit()
        return superHeroes.values.toList()
    }

    fun get(id: String): SuperHero? {
        waitABit()
        return superHeroes[id]
    }

    fun saveAll(all: List<SuperHero>) = executor.execute {
        waitABit()
        superHeroes.clear()
        superHeroes.putAll(all.associateBy { it.id })
    }

    fun save(superHero: SuperHero): SuperHero {
        executor.execute {
            waitABit()
            superHeroes[superHero.id] = superHero
        }
        return superHero
    }

    private fun waitABit() {
        Thread.sleep(BIT_TIME)
    }
}