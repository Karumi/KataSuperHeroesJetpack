package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.ui.view.AcceptanceTest
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock

class SuperHeroDetailActivityTest : AcceptanceTest<SuperHeroDetailActivity>(
    SuperHeroDetailActivity::class.java
) {

    @Mock
    private lateinit var repository: SuperHeroRepository

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
        whenever(runBlocking { repository.get(superHeroId) }).thenReturn(superHero)
        return superHero
    }

    private fun startActivity(superHero: SuperHero): SuperHeroDetailActivity {
        val args = Bundle()
        args.putString("super_hero_id_key", superHero.id)
        return startActivity(args)
    }

    override val testDependencies = Module(allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}