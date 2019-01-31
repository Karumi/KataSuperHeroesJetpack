package com.karumi.jetpack.superheroes.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.databinding.SuperHeroRowBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroesViewModel

internal class SuperHeroesAdapter(
    private val viewModel: SuperHeroesViewModel
) : RecyclerView.Adapter<SuperHeroViewHolder>() {
    private val superHeroes: MutableList<SuperHero> = ArrayList()

    fun addAll(collection: Collection<SuperHero>) {
        superHeroes.addAll(collection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val binding: SuperHeroRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.super_hero_row,
            parent,
            false
        )

        return SuperHeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.render(superHeroes[position], viewModel)
    }

    override fun getItemCount(): Int {
        return superHeroes.size
    }

    fun clear() {
        superHeroes.clear()
    }
}