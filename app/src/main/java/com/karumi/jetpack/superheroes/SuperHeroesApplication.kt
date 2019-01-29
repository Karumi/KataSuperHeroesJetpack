package com.karumi.jetpack.superheroes

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.karumi.jetpack.superheroes.common.SuperHeroesDatabase
import com.karumi.jetpack.superheroes.data.repository.LocalSuperHeroDataSource
import com.karumi.jetpack.superheroes.data.repository.RemoteSuperHeroDataSource
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.data.repository.room.SuperHeroDao
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

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

    private fun appDependencies(): Kodein.Module {
        return Kodein.Module("Application dependencies", allowSilentOverride = true) {
            bind<SuperHeroesDatabase>() with singleton {
                Room.databaseBuilder(
                    this@SuperHeroesApplication,
                    SuperHeroesDatabase::class.java,
                    "superheroes-db"
                ).build()
            }
            bind<SuperHeroDao>() with provider {
                val database: SuperHeroesDatabase = instance()
                database.superHeroesDao()
            }
            bind<SuperHeroRepository>() with provider {
                SuperHeroRepository(instance(), instance())
            }
            bind<LocalSuperHeroDataSource>() with singleton {
                LocalSuperHeroDataSource(instance())
            }
            bind<RemoteSuperHeroDataSource>() with provider {
                RemoteSuperHeroDataSource()
            }
        }
    }
}

fun Context.asApp() = this.applicationContext as SuperHeroesApplication