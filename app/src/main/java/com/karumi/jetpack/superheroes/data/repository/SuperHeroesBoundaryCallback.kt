package com.karumi.jetpack.superheroes.data.repository

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class SuperHeroesBoundaryCallback(
    private val local: LocalSuperHeroDataSource,
    private val remote: RemoteSuperHeroDataSource
) : PagedList.BoundaryCallback<SuperHero>() {

    private var pageIndexAboutToLoad = 0

    override fun onZeroItemsLoaded() {
        pageIndexAboutToLoad = 0
        loadNextPage()
    }

    override fun onItemAtEndLoaded(itemAtEnd: SuperHero) {
        loadNextPage()
    }

    private fun loadNextPage() {
        val remoteSuperHeroesPage =
            remote.getSuperHeroesPage(pageIndexAboutToLoad, SuperHeroRepository.PAGE_SIZE)

        remoteSuperHeroesPage.observeForever(object : Observer<List<SuperHero>> {
            override fun onChanged(superHeroes: List<SuperHero>) {
                local.saveAll(superHeroes)
                remoteSuperHeroesPage.removeObserver(this)
                pageIndexAboutToLoad++
            }
        })
    }
}