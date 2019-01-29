package com.karumi.jetpack.superheroes.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.ui.presenter.SuperHeroesPresenter

internal class SuperHeroesAdapter(
    private val presenter: SuperHeroesPresenter
) : RecyclerView.Adapter<SuperHeroViewHolder>() {
    private val superHeroes: MutableList<SuperHero> = ArrayList()

    fun addAll(collection: Collection<SuperHero>) {
        superHeroes.addAll(collection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.super_hero_row, parent,
            false
        )
        return SuperHeroViewHolder(view, presenter)
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        val superHero = superHeroes[position]
        holder.render(superHero)
    }

    override fun getItemCount(): Int {
        return superHeroes.size
    }

    fun clear() {
        superHeroes.clear()
    }
}