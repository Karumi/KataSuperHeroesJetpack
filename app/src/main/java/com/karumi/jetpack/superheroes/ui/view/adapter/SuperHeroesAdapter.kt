package com.karumi.jetpack.superheroes.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.databinding.SuperHeroRowBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroesViewModel

internal class SuperHeroesAdapter(
    private val viewModel: SuperHeroesViewModel
) : PagedListAdapter<SuperHero, SuperHeroViewHolder>(diffCallback) {
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
        val superHero = getItem(position) ?: return
        holder.render(superHero, viewModel)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SuperHero>() {
            override fun areItemsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean =
                oldItem == newItem
        }
    }
}