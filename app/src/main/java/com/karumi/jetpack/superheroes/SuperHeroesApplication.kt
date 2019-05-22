package com.karumi.jetpack.superheroes

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import androidx.work.WorkManager
import com.karumi.jetpack.superheroes.common.SuperHeroesDatabase
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.data.repository.LocalSuperHeroDataSource
import com.karumi.jetpack.superheroes.data.repository.RemoteSuperHeroDataSource
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.data.repository.SuperHeroesBoundaryCallback
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import com.karumi.jetpack.superheroes.worker.ThanosWorker
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SuperHeroesApplication : Application(), KodeinAware {
    override var kodein = Kodein.lazy {
        import(appDependencies())
        import(androidModule(this@SuperHeroesApplication))
    }

    override fun onCreate() {
        super.onCreate()
        startThanosWork()
    }

    private fun startThanosWork() {
        val work = PeriodicWorkRequest.Builder(
            ThanosWorker::class.java,
            MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS
        ).build()
        WorkManager.getInstance().enqueue(work)
    }

    fun override(overrideModule: Kodein.Module) {
        kodein = Kodein.lazy {
            import(appDependencies())
            import(androidModule(this@SuperHeroesApplication))
            import(overrideModule, allowOverride = true)
        }
    }

    private fun appDependencies(): Kodein.Module = module {
        bind<SuperHeroesDatabase>() with singleton {
            SuperHeroesDatabase.build(this@SuperHeroesApplication)
        }
        bind<SuperHeroDao>() with provider {
            val database: SuperHeroesDatabase = instance()
            database.superHeroesDao()
        }
        bind<SuperHeroRepository>() with provider {
            SuperHeroRepository(instance(), instance(), instance())
        }
        bind<SuperHeroesBoundaryCallback>() with provider {
            SuperHeroesBoundaryCallback(instance(), instance())
        }
        bind<LocalSuperHeroDataSource>() with provider {
            LocalSuperHeroDataSource(instance(), instance())
        }
        bind<RemoteSuperHeroDataSource>() with provider {
            RemoteSuperHeroDataSource(instance())
        }
        bind<Executor>() with provider {
            Executors.newCachedThreadPool()
        }
        bind<DKodein>() with provider { this }
    }
}