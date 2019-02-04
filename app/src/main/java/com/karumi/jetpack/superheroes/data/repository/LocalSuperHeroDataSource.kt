package com.karumi.jetpack.superheroes.data.repository

import com.karumi.jetpack.superheroes.domain.model.SuperHero

class LocalSuperHeroDataSource {
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

    fun saveAll(all: List<SuperHero>) {
        waitABit()
        superHeroes.clear()
        superHeroes.putAll(all.associateBy { it.id })
    }

    fun save(superHero: SuperHero): SuperHero {
        waitABit()
        superHeroes[superHero.id] = superHero
        return superHero
    }

    private fun waitABit() {
        Thread.sleep(BIT_TIME)
    }
}