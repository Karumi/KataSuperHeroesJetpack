package com.karumi.jetpack.superheroes.ui.presenter

import com.karumi.jetpack.superheroes.common.weak
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import java.util.concurrent.ExecutorService

class SuperHeroesPresenter(
    view: View,
    private val getSuperHeroes: GetSuperHeroes,
    private val executor: ExecutorService
) {

    private val view: View? by weak(view)

    fun onResume() {
        view?.showLoading()
        refreshSuperHeroes()
    }

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
        fun showSuperHeroes(superHeroes: List<SuperHero>)
        fun showLoading()
        fun showEmptyCase()
        fun openDetail(id: String)
    }
}