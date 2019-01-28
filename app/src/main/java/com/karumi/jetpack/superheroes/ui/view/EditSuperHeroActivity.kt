package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.Toolbar
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroes
import com.karumi.jetpack.superheroes.ui.presenter.EditSuperHeroPresenter
import kotlinx.android.synthetic.main.main_activity.*

class EditSuperHeroActivity : BaseActivity() {

    companion object {
        private const val SUPER_HERO_NAME_KEY = "super_hero_name_key"

        fun open(activity: Activity, superHeroName: String) {
            val intent = Intent(activity, EditSuperHeroActivity::class.java)
            intent.putExtra(SUPER_HERO_NAME_KEY, superHeroName)
            activity.startActivity(intent)
        }
    }

    override val layoutId = R.layout.edit_super_hero_activity
    override val presenter: EditSuperHeroPresenter by injector.instance()
    override val toolbarView: Toolbar
        get() = toolbar
    private val superHeroName: String
        get() = intent?.extras?.getString(SUPER_HERO_NAME_KEY) ?: ""

    override val activityModules = Kodein.Module(allowSilentOverride = true) {
        bind<EditSuperHeroPresenter>() with provider { EditSuperHeroPresenter() }
        bind<GetSuperHeroes>() with provider {
            GetSuperHeroes(
                instance()
            )
        }
    }
}