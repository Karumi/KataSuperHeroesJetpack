package com.karumi.ui.view

import android.support.v7.widget.Toolbar
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.karumi.R
import com.karumi.domain.usecase.GetSuperHeroes
import com.karumi.ui.presenter.EditSuperHeroPresenter
import kotlinx.android.synthetic.main.main_activity.*

class EditSuperHeroActivity : BaseActivity() {
    override val layoutId = R.layout.edit_super_hero_activity
    override val presenter: EditSuperHeroPresenter by injector.instance()
    override val toolbarView: Toolbar
        get() = toolbar

    override val activityModules = Kodein.Module(allowSilentOverride = true) {
        bind<EditSuperHeroPresenter>() with provider { EditSuperHeroPresenter() }
        bind<GetSuperHeroes>() with provider { GetSuperHeroes(instance()) }
    }
}