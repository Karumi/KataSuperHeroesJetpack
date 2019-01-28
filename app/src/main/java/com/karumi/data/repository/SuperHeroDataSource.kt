package com.karumi.data.repository

import com.karumi.domain.model.DomainError
import com.karumi.domain.model.SuperHero
import org.funktionale.either.Either

interface SuperHeroDataSource {
    fun get(key: String): Either<DomainError, SuperHero>
    fun getAll(): Either<DomainError, List<SuperHero>>
    fun isUpdated(): Boolean
    fun populate(superHeroes: List<SuperHero>) {}
    fun contains(key: String): Boolean = true
}