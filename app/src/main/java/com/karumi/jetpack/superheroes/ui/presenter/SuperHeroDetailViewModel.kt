package com.karumi.jetpack.superheroes.ui.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById

class SuperHeroDetailViewModel(
    application: Application,
    private val getSuperHeroById: GetSuperHeroById
) : SuperHeroDetailListener, AndroidViewModel(application) {

    val superHero = MediatorLiveData<SuperHero>()
    val isLoading = MutableLiveData<Boolean>()
    val idOfSuperHeroToEdit = MutableLiveData<String?>()

    fun prepare(id: String) {
        isLoading.value = true
        superHero.addSource(getSuperHeroById(id)) {
            superHero.postValue(it)
            isLoading.value = false
        }
    }

    override fun onEditSelected() {
        idOfSuperHeroToEdit.value = superHero.value?.id
    }
}

interface SuperHeroDetailListener {
    fun onEditSelected()
}