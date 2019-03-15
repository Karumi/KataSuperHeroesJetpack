package com.karumi.jetpack.superheroes

import android.app.Application
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.data.repository.LocalSuperHeroDataSource
import com.karumi.jetpack.superheroes.data.repository.RemoteSuperHeroDataSource
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SuperHeroesApplication : Application(), KodeinAware {
    override var kodein = Kodein.lazy {
        import(appDependencies())
        import(androidModule(this@SuperHeroesApplication))
    }

    fun override(overrideModule: Kodein.Module) {
        kodein = Kodein.lazy {
            import(appDependencies())
            import(androidModule(this@SuperHeroesApplication))
            import(overrideModule, allowOverride = true)
        }
    }

    private fun appDependencies(): Kodein.Module = module {
        bind<SuperHeroRepository>() with provider {
            SuperHeroRepository(instance(), instance())
        }
        bind<LocalSuperHeroDataSource>() with singleton {
            LocalSuperHeroDataSource(instance())
        }
        bind<RemoteSuperHeroDataSource>() with provider {
            RemoteSuperHeroDataSource(instance())
        }
        bind<ExecutorService>() with provider {
            Executors.newCachedThreadPool()
        }
    }
}