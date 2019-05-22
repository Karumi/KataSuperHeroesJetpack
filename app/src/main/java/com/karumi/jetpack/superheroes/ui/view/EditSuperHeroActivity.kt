package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.databinding.EditSuperHeroActivityBinding
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.domain.usecase.SaveSuperHero
import com.karumi.jetpack.superheroes.ui.presenter.EditSuperHeroPresenter
import kotlinx.android.synthetic.main.edit_super_hero_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class EditSuperHeroActivity :
    BaseActivity<EditSuperHeroActivityBinding>() {

    companion object {
        private const val SUPER_HERO_ID_KEY = "super_hero_id_key"

        fun open(activity: Activity, superHeroId: String) {
            val intent = Intent(activity, EditSuperHeroActivity::class.java)
            intent.putExtra(SUPER_HERO_ID_KEY, superHeroId)
            activity.startActivity(intent)
        }
    }

    override val presenter: EditSuperHeroPresenter by instance()
    override val layoutId = R.layout.edit_super_hero_activity
    override val toolbarView: Toolbar
        get() = toolbar
    private val superHeroId: String by lazy { intent?.extras?.getString(SUPER_HERO_ID_KEY) ?: "" }

    override fun configureBinding(binding: EditSuperHeroActivityBinding) {
        binding.listener = presenter
        binding.isLoading = presenter.isLoading
        binding.superHero = presenter.superHero
        binding.editableSuperHero = presenter.editableSuperHero
        binding.lifecycleOwner = this
    }

    override fun prepare(intent: Intent?) {
        title = superHeroId
        presenter.isClosing.observe(this, Observer { finish() })
        presenter.preparePresenter(superHeroId)
    }

    override val activityModules = module {
        bind<EditSuperHeroPresenter>() with provider {
            EditSuperHeroPresenter(instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
        bind<SaveSuperHero>() with provider { SaveSuperHero(instance()) }
    }
}