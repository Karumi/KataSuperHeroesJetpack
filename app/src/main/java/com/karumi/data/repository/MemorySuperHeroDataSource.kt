package com.karumi.data.repository

import com.karumi.common.TimeProvider
import com.karumi.domain.model.DomainError
import com.karumi.domain.model.NotIndexFoundDomainError
import com.karumi.domain.model.SuperHero
import org.funktionale.either.Either
import java.util.concurrent.TimeUnit

class MemorySuperHeroDataSource(private val timeProvider: TimeProvider) : SuperHeroDataSource {
    companion object {
        private val TIME_UPDATE = TimeUnit.SECONDS.toMillis(1)
    }

    private val cache = LinkedHashMap<String, SuperHero>()
    private var lastUpdate = 0L

    override fun get(key: String): Either<DomainError, SuperHero> =
        cache[key]?.let { Either.right(it) }
            ?: Either.left(NotIndexFoundDomainError(key))

    override fun isUpdated(): Boolean = timeProvider.time - lastUpdate < TIME_UPDATE

    override fun contains(key: String): Boolean = cache.contains(key)

    override fun getAll(): Either<DomainError, List<SuperHero>> =
        Either.right(ArrayList(cache.values))

    override fun populate(superHeroes: List<SuperHero>) {
        lastUpdate = timeProvider.time
        cache.putAll(superHeroes.map { it.id to it })
    }

}