package com.karumi.jetpack.superheroes.ui.view

import android.os.Looper
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.data.singleValueLiveData
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.mockito.Mock
import java.util.concurrent.Executors.newSingleThreadExecutor

class MainActivityTest : AcceptanceTest<MainActivity>(MainActivity::class.java) {

    companion object {
        private const val ANY_NUMBER_OF_SUPER_HEROES = 100
    }

    @Mock
    private lateinit var repository: SuperHeroRepository

    @Test
    fun showsEmptyCaseIfThereAreNoSuperHeroes() {
        givenThereAreNoSuperHeroes()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsJustOneSuperHero() {
        givenThereAreSomeSuperHeroes(1)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsSuperHeroesIfThereAreSomeSuperHeroes() {
        givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsAvengersBadgeIfASuperHeroIsPartOfTheAvengersTeam() {
        givenThereAreSomeAvengers(ANY_NUMBER_OF_SUPER_HEROES)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun doesNotShowAvengersBadgeIfASuperHeroIsNotPartOfTheAvengersTeam() {
        givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    private fun compareScreenshot(activity: MainActivity) {
        Thread.sleep(100)
        super.compareScreenshot(activity)
    }

    private fun givenThereAreSomeAvengers(numberOfAvengers: Int): List<SuperHero> =
        givenThereAreSomeSuperHeroes(numberOfAvengers, areAvengers = true)

    private fun givenThereAreSomeSuperHeroes(
        numberOfSuperHeroes: Int = 1,
        areAvengers: Boolean = false
    ): List<SuperHero> {
        val superHeroes = IntRange(0, numberOfSuperHeroes - 1).map { id ->
            val superHeroId = "#$id"
            val superHeroName = "SuperHero - $id"
            val superHeroDescription = "Description Super Hero - $id"
            SuperHero(
                superHeroId,
                superHeroName,
                null,
                areAvengers,
                superHeroDescription
            )
        }

        whenever(repository.getAllSuperHeroes())
            .thenReturn(singleValueLiveData(superHeroes.toPagedList()))

        return superHeroes
    }

    private fun givenThereAreNoSuperHeroes() {
        whenever(repository.getAllSuperHeroes())
            .thenReturn(singleValueLiveData(emptyList<SuperHero>().toPagedList()))
    }

    override val testDependencies = Kodein.Module("Test dependencies", allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }

    private fun List<SuperHero>.toPagedList(): PagedList<SuperHero> =
        PagedList.Builder(object : PositionalDataSource<SuperHero>() {
            override fun loadRange(
                params: LoadRangeParams,
                callback: LoadRangeCallback<SuperHero>
            ) {
                callback.onResult(this@toPagedList)
            }

            override fun loadInitial(
                params: LoadInitialParams,
                callback: LoadInitialCallback<SuperHero>
            ) {
                callback.onResult(
                    this@toPagedList,
                    0,
                    this@toPagedList.size
                )
            }
        }, 100)
            .setNotifyExecutor(newSingleThreadExecutor { Looper.getMainLooper().thread })
            .setFetchExecutor(newSingleThreadExecutor { Looper.getMainLooper().thread })
            .build()
}