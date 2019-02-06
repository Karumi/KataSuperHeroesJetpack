package com.karumi.jetpack.superheroes.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.karumi.jetpack.superheroes.data.repository.LocalSuperHeroDataSource
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.erased.instance

class ThanosWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params), KodeinAware {

    override val kodein by closestKodein(context)
    private val localSuperHeroesDataSource: LocalSuperHeroDataSource by instance()

    override fun doWork(): Result {
        localSuperHeroesDataSource.deleteRandomHalf()
        return Result.success()
    }
}