package com.karumi.jetpack.superheroes.ui.presenter

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.karumi.jetpack.superheroes.common.weak
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import java.util.concurrent.ExecutorService

class SuperHeroDetailPresenter(
    view: View,
    private val getSuperHeroById: GetSuperHeroById,
    private val executor: ExecutorService
) : SuperHeroDetailListener, LifecycleObserver {

    private val view: View? by weak(view)

    private lateinit var id: String

    fun preparePresenter(id: String) {
        this.id = id
    }

    @OnLifecycleEvent(ON_RESUME)
    fun onResume() {
        view?.showLoading()
        refreshSuperHero()
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun onDestroy() {
        executor.shutdownNow()
    }

    override fun onEditSelected() {
        view?.openEditSuperHero(id)
    }

    private fun refreshSuperHero() = executor.submit {
        val superHero = getSuperHeroById(id) ?: return@submit
        view?.hideLoading()
        view?.showSuperHero(superHero)
    }

    interface View {
        fun hideLoading()
        fun showLoading()
        fun showSuperHero(superHero: SuperHero)
        fun openEditSuperHero(superHeroId: String)
    }
}

interface SuperHeroDetailListener {
    fun onEditSelected()
}