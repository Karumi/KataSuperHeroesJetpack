package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.data.singleValueLiveData
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.mockito.Mock

class SuperHeroDetailFragmentTest : FragmentTest<SuperHeroDetailFragment>() {

    @Mock
    private lateinit var repository: SuperHeroRepository
    override val fragmentBlock = { SuperHeroDetailFragment() }

    @Test
    fun showsAvengersBadgeIfSuperHeroIsPartOfTheAvengersTeam() {
        val superHero = givenThereIsASuperHero(isAvenger = true)

        val activity = startActivity(superHero)

        compareScreenshot(activity)
    }

    @Test
    fun doesNotShowAvengersBadgeIfSuperHeroIsNotPartOfTheAvengersTeam() {
        val superHero = givenThereIsASuperHero(isAvenger = false)

        val activity = startActivity(superHero)

        compareScreenshot(activity)
    }

    private fun givenThereIsASuperHero(isAvenger: Boolean): SuperHero {
        val superHeroId = "#1"
        val superHeroName = "SuperHero"
        val superHeroDescription = "Super Hero Description"
        val superHero = SuperHero(superHeroId, superHeroName, null, isAvenger, superHeroDescription)
        whenever(repository.get(superHeroId)).thenReturn(singleValueLiveData(superHero))
        return superHero
    }

    private fun startActivity(superHero: SuperHero): SuperHeroDetailFragment {
        val args = Bundle().apply {
            putString("superHeroId", superHero.id)
            putString("superHeroName", superHero.name)
        }
        return startFragment(args)
    }

    override val testDependencies = Kodein.Module("Test dependencies", allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}