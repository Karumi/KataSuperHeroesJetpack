package com.karumi.jetpack.superheroes.ui.presenter

import com.karumi.jetpack.superheroes.common.async
import com.karumi.jetpack.superheroes.common.weak
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SuperHeroDetailPresenter(
    view: View,
    private val getSuperHeroById: GetSuperHeroById
) : CoroutineScope by MainScope() {

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
        cancel()
    }

    private fun refreshSuperHero() = launch {
        val superHero = async { getSuperHeroById(id) } ?: return@launch
        view?.hideLoading()
        view?.showSuperHero(superHero)
    }

    interface View {
        fun close()
        fun showLoading()
        fun hideLoading()
        fun showSuperHero(superHero: SuperHero)
    }
}