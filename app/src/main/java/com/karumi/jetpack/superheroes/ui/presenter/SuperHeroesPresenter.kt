package com.karumi.jetpack.superheroes.ui.presenter

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.karumi.jetpack.superheroes.common.async
import com.karumi.jetpack.superheroes.common.weak
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SuperHeroesPresenter(
    view: View,
    private val getSuperHeroes: GetSuperHeroes
) : CoroutineScope by MainScope() {

    private val view: View? by weak(
        view
    )

    fun onResume() {
        view?.showLoading()
        refreshSuperHeroes()
    }

    fun onDestroy() {
        cancel()
    }

    private fun refreshSuperHeroes() = launch {
        val result = async { getSuperHeroes() }
        view?.hideLoading()
        when {
            result.isEmpty() -> view?.showEmptyCase()
            else -> view?.showSuperHeroes(result)
        }
    }

    fun onSuperHeroClicked(superHero: SuperHero) = view?.openDetail(superHero.id)

    interface View {
        fun hideLoading()
        fun showSuperHeroes(superHeroes: List<SuperHero>)
        fun showLoading()
        fun showEmptyCase()
        fun openDetail(id: String)
    }
}