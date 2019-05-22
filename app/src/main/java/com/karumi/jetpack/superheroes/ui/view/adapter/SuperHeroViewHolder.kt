package com.karumi.jetpack.superheroes.ui.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.karumi.jetpack.superheroes.databinding.SuperHeroRowBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroesListener

class SuperHeroViewHolder(
    private val binding: SuperHeroRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun render(superHero: SuperHero, listener: SuperHeroesListener) {
        binding.superHero = superHero
        binding.listener = listener
        binding.executePendingBindings()
    }
}