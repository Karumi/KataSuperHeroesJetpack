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
    val superHeroToEdit = SingleLiveEvent<SuperHeroToEdit>()

    fun prepare(id: String) {
        loadSuperHero(id)
    }

    fun onEditSelected() {
        superHeroToEdit.value = superHero.value?.let { SuperHeroToEdit(it.id, it.name) }
    }

    private fun loadSuperHero(id: String) {
        isLoading.value = true
        superHero.addSource(getSuperHeroById(id)) {
            superHero.postValue(it)
            isLoading.postValue(false)
        }
    }
}

data class SuperHeroToEdit(val id: String, val name: String)
