package com.karumi.jetpack.superheroes.ui.presenter

import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import com.karumi.jetpack.superheroes.ui.view.SingleLiveEvent

class SuperHeroesPresenter(
    private val getSuperHeroes: GetSuperHeroes
) : SuperHeroesListener, LifecycleObserver {

    val isLoading = MutableLiveData<Boolean>()
    val isShowingEmptyCase = MutableLiveData<Boolean>()
    val superHeroes = MediatorLiveData<List<SuperHero>>()
    val idOfSuperHeroToOpen = SingleLiveEvent<String>()

    @OnLifecycleEvent(ON_CREATE)
    fun onCreate() {
        isLoading.postValue(true)
        superHeroes.addSource(getSuperHeroes()) {
            isLoading.postValue(false)
            isShowingEmptyCase.postValue(it.isEmpty())
            superHeroes.postValue(it)
        }
    }

    override fun onSuperHeroClicked(superHero: SuperHero) {
        idOfSuperHeroToOpen.postValue(superHero.id)
    }
}

interface SuperHeroesListener {
    fun onSuperHeroClicked(superHero: SuperHero)
}