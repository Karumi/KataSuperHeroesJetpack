package com.karumi.jetpack.superheroes.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class GetSuperHeroes(private val superHeroesRepository: SuperHeroRepository) {
    operator fun invoke(): LiveData<PagedList<SuperHero>> =
        superHeroesRepository.getAllSuperHeroes()
}