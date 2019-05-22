package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.bindViewModel
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.common.viewModel
import com.karumi.jetpack.superheroes.databinding.SuperHeroesFragmentBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import com.karumi.jetpack.superheroes.ui.view.SuperHeroesFragmentDirections.Companion.actionSuperHeroesFragmentToSuperHeroDetailFragment
import com.karumi.jetpack.superheroes.ui.view.adapter.SuperHeroesAdapter
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroToOpen
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroesViewModel
import kotlinx.android.synthetic.main.super_heroes_fragment.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class SuperHeroesFragment : BaseFragment<SuperHeroesFragmentBinding>() {

    override val layoutId = R.layout.super_heroes_fragment
    override val viewModel: SuperHeroesViewModel by viewModel()
    private lateinit var adapter: SuperHeroesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeAdapter()
        initializeRecyclerView()
        viewModel.prepare()
        viewModel.superHeroToOpen.observe(this, Observer { openDetail(it) })
        viewModel.superHeroes.observe(this, Observer { showSuperHeroes(it) })
    }

    override fun configureBinding(binding: SuperHeroesFragmentBinding) {
        binding.viewModel = viewModel
    }

    private fun initializeAdapter() {
        adapter = SuperHeroesAdapter(viewModel)
    }

    private fun initializeRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

    private fun showSuperHeroes(superHeroes: PagedList<SuperHero>) {
        adapter.submitList(superHeroes)
    }

    private fun openDetail(superHeroToOpen: SuperHeroToOpen) {
        val directions = actionSuperHeroesFragmentToSuperHeroDetailFragment(
            superHeroToOpen.id,
            superHeroToOpen.name
        )
        findNavController().navigate(directions)
    }

    override val activityModules = module {
        bindViewModel<SuperHeroesViewModel>() with provider {
            SuperHeroesViewModel(instance(), instance())
        }
        bind<GetSuperHeroes>() with provider { GetSuperHeroes(instance()) }
    }
}