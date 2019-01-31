package com.karumi.jetpack.superheroes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class SuperHeroRepository(
    private val local: LocalSuperHeroDataSource,
    private val remote: RemoteSuperHeroDataSource
) {
    fun getAllSuperHeroes(): LiveData<List<SuperHero>> = MediatorLiveData<List<SuperHero>>().apply {
        val localSource = local.getAllSuperHeroes()
        val remoteSource = remote.getAllSuperHeroes()

        addSource(remoteSource) { superHeroes ->
            removeSource(remoteSource)
            addSource(localSource) { postValue(it) }
            local.saveAll(superHeroes)
        }
    }

    fun get(id: String): LiveData<SuperHero?> = MediatorLiveData<SuperHero?>().apply {
        addSource(local.get(id)) {
            if (it == null) {
                syncSuperHeroFromRemote(id)
            } else {
                postValue(it)
            }
        }
    }

    fun save(superHero: SuperHero): SuperHero {
        local.save(superHero)
        remote.save(superHero)
        return superHero
    }

    private fun MediatorLiveData<SuperHero?>.syncSuperHeroFromRemote(id: String) {
        addSource(remote.get(id)) { superHero ->
            superHero ?: return@addSource
            local.save(superHero)
        }
    }
}