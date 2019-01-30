package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.databinding.SuperHeroDetailActivityBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.ui.presenter.SuperHeroDetailPresenter
import com.karumi.jetpack.superheroes.ui.utils.setImageBackground
import kotlinx.android.synthetic.main.super_hero_detail_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class SuperHeroDetailActivity :
    BaseActivity<SuperHeroDetailActivityBinding>(),
    SuperHeroDetailPresenter.View {
    companion object {
        private const val SUPER_HERO_ID_KEY = "super_hero_id_key"

        fun open(activity: Activity, superHeroId: String) {
            val intent = Intent(activity, SuperHeroDetailActivity::class.java)
            intent.putExtra(SUPER_HERO_ID_KEY, superHeroId)
            activity.startActivity(intent)
        }
    }

    override val presenter: SuperHeroDetailPresenter by instance()
    override val layoutId: Int = R.layout.super_hero_detail_activity
    override val toolbarView: Toolbar
        get() = toolbar
    private val superHeroId: String by lazy { intent?.extras?.getString(SUPER_HERO_ID_KEY) ?: "" }

    override fun configureBinding(binding: SuperHeroDetailActivityBinding) {
        binding.listener = presenter
        binding.isLoading = false
    }

    override fun prepare(intent: Intent?) {
        title = superHeroId
        presenter.preparePresenter(superHeroId)
    }

    override fun showLoading() = runOnUiThread {
        binding.isLoading = true
    }

    override fun hideLoading() = runOnUiThread {
        binding.isLoading = false
    }

    override fun showSuperHero(superHero: SuperHero) = runOnUiThread {
        title = superHero.name
        binding.superHero = superHero
        iv_super_hero_photo.setImageBackground(superHero.photo)
    }

    override fun openEditSuperHero(superHeroId: String) = runOnUiThread {
        EditSuperHeroActivity.open(this, superHeroId)
    }

    override val activityModules = module {
        bind<SuperHeroDetailPresenter>() with provider {
            SuperHeroDetailPresenter(this@SuperHeroDetailActivity, instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
    }
}