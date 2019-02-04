package com.karumi.jetpack.superheroes.ui.presenter

import com.karumi.jetpack.superheroes.common.weak
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import java.util.concurrent.ExecutorService

class SuperHeroDetailPresenter(
    view: View,
    private val getSuperHeroById: GetSuperHeroById,
    private val executor: ExecutorService
) {

    private val view: View? by weak(view)

    private lateinit var id: String

    fun preparePresenter(id: String?) {
        if (id != null) {
            this.id = id
        } else {
            view?.close()
        }
    }

    fun onResume() {
        view?.showLoading()
        refreshSuperHero()
    }

    fun onDestroy() {
        executor.shutdownNow()
    }

    fun onEditSelected() {
        view?.openEditSuperHero(id)
    }

    private fun refreshSuperHero() = executor.submit {
        val superHero = getSuperHeroById(id) ?: return@submit
        view?.hideLoading()
        view?.showSuperHero(superHero)
    }

    interface View {
        fun close()
        fun showLoading()
        fun hideLoading()
        fun showSuperHero(superHero: SuperHero)
        fun openEditSuperHero(superHeroId: String)
    }
}