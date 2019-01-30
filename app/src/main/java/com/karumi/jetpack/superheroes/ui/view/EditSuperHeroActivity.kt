package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.databinding.EditSuperHeroActivityBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.domain.usecase.SaveSuperHero
import com.karumi.jetpack.superheroes.ui.presenter.EditSuperHeroPresenter
import kotlinx.android.synthetic.main.edit_super_hero_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class EditSuperHeroActivity :
    BaseActivity<EditSuperHeroActivityBinding>(),
    EditSuperHeroPresenter.View {

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
        binding.isLoading = false
    }

    override fun prepare(intent: Intent?) {
        title = superHeroId
        presenter.preparePresenter(superHeroId)
    }

    override fun close() = runOnUiThread {
        finish()
    }

    override fun showLoading() = runOnUiThread {
        binding.isLoading = true
    }

    override fun hideLoading() = runOnUiThread {
        binding.isLoading = false
    }

    override fun showSuperHero(superHero: SuperHero) = runOnUiThread {
        binding.listener = presenter
        binding.superHero = superHero
        binding.editableSuperHero = superHero.toEditable()
    }

    override val activityModules = module {
        bind<EditSuperHeroPresenter>() with provider {
            EditSuperHeroPresenter(this@EditSuperHeroActivity, instance(), instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
        bind<SaveSuperHero>() with provider { SaveSuperHero(instance()) }
    }

    private fun SuperHero.toEditable() =
        EditSuperHeroPresenter.EditableSuperHero(isAvenger, name, description)
}