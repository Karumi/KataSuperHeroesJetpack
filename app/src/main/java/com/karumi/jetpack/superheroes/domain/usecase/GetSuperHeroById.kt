package com.karumi.jetpack.superheroes.domain.usecase

import androidx.lifecycle.LiveData
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class GetSuperHeroById(private val superHeroesRepository: SuperHeroRepository) {
    operator fun invoke(id: String): LiveData<SuperHero?> = superHeroesRepository.get(id)
}