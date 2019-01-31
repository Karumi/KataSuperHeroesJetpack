package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.bindViewModel
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.common.viewModel
import com.karumi.jetpack.superheroes.databinding.SuperHeroDetailActivityBinding
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.ui.viewmodel.SuperHeroDetailViewModel
import kotlinx.android.synthetic.main.super_hero_detail_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class SuperHeroDetailActivity :
    BaseActivity<SuperHeroDetailActivityBinding>() {
    companion object {
        private const val SUPER_HERO_ID_KEY = "super_hero_id_key"

        fun open(activity: Activity, superHeroId: String) {
            val intent = Intent(activity, SuperHeroDetailActivity::class.java)
            intent.putExtra(SUPER_HERO_ID_KEY, superHeroId)
            activity.startActivity(intent)
        }
    }

    override val viewModel: SuperHeroDetailViewModel by viewModel()
    override val layoutId: Int = R.layout.super_hero_detail_activity
    override val toolbarView: Toolbar
        get() = toolbar
    private val superHeroId: String by lazy { intent?.extras?.getString(SUPER_HERO_ID_KEY) ?: "" }

    override fun configureBinding(binding: SuperHeroDetailActivityBinding) {
        binding.viewModel = viewModel
        viewModel.idOfSuperHeroToEdit.observe(this, Observer { openEditSuperHero(it) })
        viewModel.superHero.observe(this, Observer { title = it?.name })
        viewModel.prepare(superHeroId)
    }

    private fun openEditSuperHero(superHeroId: String) {
        EditSuperHeroActivity.open(this, superHeroId)
    }

    override val activityModules = module {
        bindViewModel<SuperHeroDetailViewModel>() with provider {
            SuperHeroDetailViewModel(instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
    }
}