package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.mockito.Mock

class EditSuperHeroActivityTest :
    AcceptanceTest<EditSuperHeroActivity>(EditSuperHeroActivity::class.java) {

    companion object {
        private const val ANY_ID = "#1"
    }

    @Mock
    private lateinit var repository: SuperHeroRepository

    @Test
    fun showsJustOneSuperHero() {
        val superHero = givenThereIsASuperHero()

        val activity = startActivity(superHero)

        compareScreenshot(activity)
    }

    private fun givenThereIsASuperHero(): SuperHero {
        val superHero = SuperHero(
            ANY_ID,
            "TestSuper Hero",
            null,
            true,
            ""
        )
        whenever(repository.get(ANY_ID)).thenReturn(superHero)
        return superHero
    }

    private fun startActivity(superHero: SuperHero): EditSuperHeroActivity {
        val args = Bundle()
        args.putString("super_hero_id_key", superHero.id)
        return startActivity(args)
    }

    override val testDependencies = Kodein.Module("Test dependencies", allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}