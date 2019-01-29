package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.ui.presenter.SuperHeroDetailPresenter
import com.karumi.jetpack.superheroes.ui.utils.setImageBackground
import kotlinx.android.synthetic.main.super_hero_detail_activity.*
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class SuperHeroDetailActivity : BaseActivity(), SuperHeroDetailPresenter.View {

    companion object {
        private const val SUPER_HERO_ID_KEY = "super_hero_id_key"

        fun open(activity: Activity, superHeroId: String) {
            val intent = Intent(activity, SuperHeroDetailActivity::class.java)
            intent.putExtra(SUPER_HERO_ID_KEY, superHeroId)
            activity.startActivity(intent)
        }
    }

    private val presenter: SuperHeroDetailPresenter by instance()
    override val layoutId: Int = R.layout.super_hero_detail_activity
    override val toolbarView: Toolbar
        get() = toolbar
    private val superHeroId: String
        get() = intent?.extras?.getString(SUPER_HERO_ID_KEY) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
        edit_super_hero.setOnClickListener {
            EditSuperHeroActivity.open(
                this@SuperHeroDetailActivity,
                superHeroId
            )
        }
    }

    override fun preparePresenter(intent: Intent?) {
        title = superHeroId
        presenter.preparePresenter(superHeroId)
    }

    override fun close() = finish()

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showSuperHero(superHero: SuperHero) {
        title = superHero.name
        tv_super_hero_name.text = superHero.name
        tv_super_hero_description.text = superHero.description
        iv_avengers_badge.visibility =
                if (superHero.isAvenger) View.VISIBLE else View.GONE
        iv_super_hero_photo.setImageBackground(superHero.photo)
        edit_super_hero.visibility = View.VISIBLE
        super_hero_background.visibility = View.VISIBLE
    }

    override val activityModules =
        Kodein.Module("SuperHeroDetailActivity dependencies", allowSilentOverride = true) {
            bind<SuperHeroDetailPresenter>() with provider {
                SuperHeroDetailPresenter(this@SuperHeroDetailActivity, instance())
            }
            bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
        }
}