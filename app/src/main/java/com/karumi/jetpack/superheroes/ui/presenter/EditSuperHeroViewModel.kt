package com.karumi.jetpack.superheroes.ui.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.domain.usecase.SaveSuperHero

class EditSuperHeroViewModel(
    application: Application,
    private val getSuperHeroById: GetSuperHeroById,
    private val saveSuperHero: SaveSuperHero
) : AndroidViewModel(application) {

    val isLoading = MutableLiveData<Boolean>()
    val superHero = MediatorLiveData<SuperHero?>()
    val editableSuperHero = MutableLiveData<EditableSuperHero>()
    val isClosing = MutableLiveData<Boolean>()

    fun prepare(id: String) {
        isLoading.postValue(true)
        val superHeroSource = getSuperHeroById(id)
        superHero.addSource(superHeroSource) {
            superHero.removeSource(superHeroSource)

            superHero.postValue(it)
            editableSuperHero.postValue(it?.toEditable())
            isLoading.postValue(false)
        }
    }

    fun onSaveSuperHeroSelected() {
        val updatedSuperHero = getUpdatedSuperHero() ?: return

        isLoading.value = true
        saveSuperHero(updatedSuperHero)
        isClosing.value = true
    }

    private fun getUpdatedSuperHero(): SuperHero? {
        val superHero = superHero.value ?: return null
        return editableSuperHero.value?.applyTo(superHero)
    }

    data class EditableSuperHero(
        var isAvenger: Boolean,
        var name: String,
        var description: String
    )

    private fun SuperHero.toEditable() = EditableSuperHero(isAvenger, name, description)
    private fun EditableSuperHero.applyTo(superHero: SuperHero) =
        superHero.copy(isAvenger = isAvenger, name = name, description = description)
}