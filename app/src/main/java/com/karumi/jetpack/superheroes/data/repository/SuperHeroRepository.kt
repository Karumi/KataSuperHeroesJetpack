package com.karumi.jetpack.superheroes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class SuperHeroRepository(
    private val local: LocalSuperHeroDataSource,
    private val remote: RemoteSuperHeroDataSource,
    private val boundaryCallback: SuperHeroesBoundaryCallback
) {
    companion object {
        const val PAGE_SIZE = 4
    }

    fun getAllSuperHeroes(): LiveData<PagedList<SuperHero>> =
        local.getAllSuperHeroes(PAGE_SIZE, boundaryCallback)

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