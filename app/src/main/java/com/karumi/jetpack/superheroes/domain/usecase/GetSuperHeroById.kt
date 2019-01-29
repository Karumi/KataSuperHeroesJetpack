package com.karumi.jetpack.superheroes.domain.usecase

import androidx.annotation.WorkerThread
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class GetSuperHeroById(private val superHeroesRepository: SuperHeroRepository) {
    @WorkerThread
    suspend operator fun invoke(id: String): SuperHero? = superHeroesRepository.get(id)
}