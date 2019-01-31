package com.karumi.jetpack.superheroes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.ui.view.SingleLiveEvent

class SuperHeroDetailViewModel(
    application: Application,
    private val getSuperHeroById: GetSuperHeroById
) : AndroidViewModel(application) {

    val isLoading = MutableLiveData<Boolean>()
    val superHero = MediatorLiveData<SuperHero?>()
    val idOfSuperHeroToEdit = SingleLiveEvent<String>()

    fun prepare(id: String) {
        loadSuperHero(id)
    }

    fun onEditSelected() {
        idOfSuperHeroToEdit.value = superHero.value?.id
    }

    private fun loadSuperHero(id: String) {
        isLoading.value = true
        superHero.addSource(getSuperHeroById(id)) {
            superHero.postValue(it)
            isLoading.postValue(false)
        }
    }
}
