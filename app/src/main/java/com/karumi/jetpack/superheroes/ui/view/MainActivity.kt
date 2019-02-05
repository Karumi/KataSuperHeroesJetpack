package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.bindViewModel
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.common.viewModel
import com.karumi.jetpack.superheroes.databinding.MainActivityBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import com.karumi.jetpack.superheroes.ui.view.adapter.SuperHeroesAdapter
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroesViewModel
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class MainActivity : BaseActivity<MainActivityBinding>() {

    override val viewModel: SuperHeroesViewModel by viewModel()
    private lateinit var adapter: SuperHeroesAdapter
    override val layoutId: Int = R.layout.main_activity
    override val toolbarView: Toolbar
        get() = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeAdapter()
        initializeRecyclerView()
        viewModel.prepare()
        viewModel.idOfSuperHeroToOpen.observe(this, Observer { openDetail(it) })
        viewModel.superHeroes.observe(this, Observer { showSuperHeroes(it) })
    }

    override fun configureBinding(binding: MainActivityBinding) {
        binding.viewModel = viewModel
    }

    private fun initializeAdapter() {
        adapter = SuperHeroesAdapter(viewModel)
    }

    private fun initializeRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

    private fun showSuperHeroes(superHeroes: PagedList<SuperHero>) {
        adapter.submitList(superHeroes)
    }

    private fun openDetail(id: String) {
        SuperHeroDetailActivity.open(
            activity = this,
            superHeroId = id
        )
    }

    override val activityModules = module {
        bindViewModel<SuperHeroesViewModel>() with provider {
            SuperHeroesViewModel(instance(), instance())
        }
        bind<GetSuperHeroes>() with provider { GetSuperHeroes(instance()) }
    }
}
