package com.karumi.jetpack.superheroes.domain.usecase

import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class GetSuperHeroById(private val superHeroesRepository: SuperHeroRepository) {
    operator fun invoke(id: String): SuperHero? = superHeroesRepository[id]
}