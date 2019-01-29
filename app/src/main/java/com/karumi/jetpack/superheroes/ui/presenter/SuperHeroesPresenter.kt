package com.karumi.jetpack.superheroes.ui.presenter

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.karumi.jetpack.superheroes.common.weak
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import java.util.concurrent.ExecutorService

class SuperHeroesPresenter(
    view: View,
    private val getSuperHeroes: GetSuperHeroes,
    private val executor: ExecutorService
) : LifecycleObserver {

    private val view: View? by weak(view)

    @OnLifecycleEvent(ON_RESUME)
    fun onResume() {
        view?.showLoading()
        refreshSuperHeroes()
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun onDestroy() {
        executor.shutdownNow()
    }

    private fun refreshSuperHeroes() = executor.submit {
        val result = getSuperHeroes()
        view?.hideLoading()
        when {
            result.isEmpty() -> view?.showEmptyCase()
            else -> view?.showSuperHeroes(result)
        }
    }

    fun onSuperHeroClicked(superHero: SuperHero) {
        view?.openDetail(superHero.id)
    }

    interface View {
        fun hideLoading()
        fun showLoading()
        fun showEmptyCase()
        fun showSuperHeroes(superHeroes: List<SuperHero>)
        fun openDetail(id: String)
    }
}