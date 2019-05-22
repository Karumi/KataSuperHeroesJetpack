package com.karumi.jetpack.superheroes.ui.view

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.bindViewModel
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.common.viewModel
import com.karumi.jetpack.superheroes.databinding.SuperHeroDetailFragmentBinding
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.ui.view.SuperHeroDetailFragmentDirections.Companion.actionSuperHeroDetailFragmentToEditSuperHeroFragment
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroDetailViewModel
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroToEdit
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class SuperHeroDetailFragment : BaseFragment<SuperHeroDetailFragmentBinding>() {
    override val layoutId = R.layout.super_hero_detail_fragment
    override val viewModel: SuperHeroDetailViewModel by viewModel()
    private val args: SuperHeroDetailFragmentArgs by navArgs()

    override fun configureBinding(binding: SuperHeroDetailFragmentBinding) {
        binding.viewModel = viewModel
        viewModel.superHeroToEdit.observe(this, Observer { openEditSuperHero(it) })
        viewModel.prepare(args.superHeroId)
    }

    private fun openEditSuperHero(superHeroToEdit: SuperHeroToEdit?) {
        superHeroToEdit ?: return
        val directions = actionSuperHeroDetailFragmentToEditSuperHeroFragment(
            superHeroToEdit.id,
            superHeroToEdit.name
        )
        findNavController().navigate(directions)
    }

    override val activityModules = module {
        bindViewModel<SuperHeroDetailViewModel>() with provider {
            SuperHeroDetailViewModel(instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
    }
}
