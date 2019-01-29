package com.karumi.jetpack.superheroes.ui.presenter

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
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
) : LifecycleObserver, CoroutineScope by MainScope() {

    private val view: View? by weak(view)

    private lateinit var id: String

    fun preparePresenter(id: String?) {
        if (id != null) {
            this.id = id
        } else {
            view?.close()
        }
    }

    @OnLifecycleEvent(ON_RESUME)
    fun onResume() {
        view?.showLoading()
        refreshSuperHero()
    }

    @OnLifecycleEvent(ON_DESTROY)
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