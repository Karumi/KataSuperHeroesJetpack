package com.karumi.jetpack.superheroes.domain.usecase

import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class GetSuperHeroes(private val superHeroesRepository: SuperHeroRepository) {
    suspend operator fun invoke(): List<SuperHero> = superHeroesRepository.getAllSuperHeroes()
}