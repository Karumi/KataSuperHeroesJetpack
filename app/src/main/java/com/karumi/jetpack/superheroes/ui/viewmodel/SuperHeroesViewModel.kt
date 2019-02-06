package com.karumi.jetpack.superheroes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import com.karumi.jetpack.superheroes.ui.view.SingleLiveEvent

class SuperHeroesViewModel(
    application: Application,
    private val getSuperHeroes: GetSuperHeroes
) : SuperHeroesListener, AndroidViewModel(application) {

    val isLoading = MutableLiveData<Boolean>()
    val isShowingEmptyCase = MutableLiveData<Boolean>()
    val superHeroes = MediatorLiveData<PagedList<SuperHero>>()
    val superHeroToOpen = SingleLiveEvent<SuperHeroToOpen>()

    fun prepare() {
        isLoading.value = true
        superHeroes.addSource(getSuperHeroes()) {
            isShowingEmptyCase.postValue(it.isEmpty())
            superHeroes.postValue(it)
            isLoading.postValue(false)
        }
    }

    override fun onSuperHeroClicked(id: String, name: String) {
        superHeroToOpen.value = SuperHeroToOpen(id, name)
    }
}

interface SuperHeroesListener {
    fun onSuperHeroClicked(id: String, name: String)
}

data class SuperHeroToOpen(val id: String, val name: String)