package com.karumi.jetpack.superheroes.ui.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import com.karumi.jetpack.superheroes.ui.view.SingleLiveEvent

class SuperHeroesViewModel(
    application: Application,
    private val getSuperHeroes: GetSuperHeroes
) : AndroidViewModel(application), SuperHeroesListener {

    val isLoading = MutableLiveData<Boolean>()
    val isShowingEmptyCase = MutableLiveData<Boolean>()
    val superHeroes = MediatorLiveData<List<SuperHero>>()
    val idOfSuperHeroToOpen = SingleLiveEvent<String>()

    fun prepare() {
        isLoading.postValue(true)
        superHeroes.addSource(getSuperHeroes()) {
            isLoading.postValue(false)
            isShowingEmptyCase.postValue(it.isEmpty())
            superHeroes.postValue(it)
        }
    }

    override fun onSuperHeroClicked(id: String) {
        idOfSuperHeroToOpen.postValue(id)
    }
}

interface SuperHeroesListener {
    fun onSuperHeroClicked(id: String)
}