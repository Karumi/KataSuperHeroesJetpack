package com.karumi.jetpack.superheroes.worker

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.testing.WorkManagerTestInitHelper
import com.karumi.jetpack.superheroes.SuperHeroesApplication
import com.karumi.jetpack.superheroes.common.SuperHeroesDatabase
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroEntity
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import java.util.concurrent.Executor

class ThanosWorkerTest {

    private lateinit var database: SuperHeroesDatabase

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<SuperHeroesApplication>()
        WorkManagerTestInitHelper.initializeTestWorkManager(app)
        database = Room.inMemoryDatabaseBuilder(app, SuperHeroesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        app.override(Kodein.Module("Test dependencies", allowSilentOverride = true) {
            bind<SuperHeroesDatabase>() with instance(database)
            bind<Executor>() with instance(Executor {
                getInstrumentation().runOnMainSync { it.run() }
            })
        })
        database.superHeroesDao().deleteAll()
    }

    @Test
    fun thanosWorkerDoNotDeleteAnyoneIfSuperHeroesAreLessThanTwo() {
        val superHeroes = insertSuperHeroes(1)

        enqueueWorker()

        assertEquals(superHeroes, getInsertedSuperHeroes())
    }

    @Test
    fun thanosWorkerDeletesHalfTheSuperHeroesWhenThereAreMoreThanTwo() {
        insertSuperHeroes(10)

        enqueueWorker()

        assertEquals(5, getInsertedSuperHeroes().size)
    }

    @Test
    fun thanosWorkerDeletesHalfTheSuperHeroesRoundingDownWhenThereIsAnOddNumberOfSuperHeroes() {
        insertSuperHeroes(11)

        enqueueWorker()

        assertEquals(6, getInsertedSuperHeroes().size)
    }

    private fun enqueueWorker() = getInstrumentation().runOnMainSync {
        val work = OneTimeWorkRequest.Builder(ThanosWorker::class.java).build()
        WorkManager.getInstance().enqueue(work)
    }

    private fun insertSuperHeroes(numberOfSuperHeroes: Int): List<SuperHero> {
        val superHeroes = (1..numberOfSuperHeroes)
            .map { SuperHero("#$it", "SH $it", null, false, "") }

        database.superHeroesDao().insertAll(superHeroes.map { SuperHeroEntity(it) })

        return superHeroes
    }

    private fun getInsertedSuperHeroes(): List<SuperHero> =
        database.superHeroesDao().getAllSuperHeroes().map { it.superHero }
}