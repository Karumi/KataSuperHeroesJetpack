package com.karumi.jetpack.superheroes.domain.usecase

import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class GetSuperHeroByName(private val superHeroesRepository: SuperHeroRepository) {

    operator fun invoke(name: String): SuperHero = superHeroesRepository.getByName(name)
}