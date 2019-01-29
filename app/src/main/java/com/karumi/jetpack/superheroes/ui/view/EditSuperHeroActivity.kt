package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.domain.usecase.SaveSuperHero
import com.karumi.jetpack.superheroes.ui.presenter.EditSuperHeroPresenter
import com.karumi.jetpack.superheroes.ui.utils.setImageBackground
import kotlinx.android.synthetic.main.edit_super_hero_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class EditSuperHeroActivity : BaseActivity(), EditSuperHeroPresenter.View {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bt_save_edition.setOnClickListener {
            presenter.onSaveSuperHeroSelected(
                name = et_super_hero_name.text?.toString() ?: "",
                description = et_super_hero_description.text?.toString() ?: "",
                isAvenger = cb_is_avenger.isChecked
            )
        }
    }

    override fun prepare(intent: Intent?) {
        title = superHeroId
        presenter.preparePresenter(superHeroId)
    }

    override fun close() = runOnUiThread {
        finish()
    }

    override fun showLoading() = runOnUiThread {
        et_super_hero_name.isEnabled = false
        et_super_hero_description.isEnabled = false
        bt_save_edition.isEnabled = false
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() = runOnUiThread {
        et_super_hero_name.isEnabled = true
        et_super_hero_description.isEnabled = true
        bt_save_edition.isEnabled = true
        progress_bar.visibility = View.GONE
    }

    override fun showSuperHero(superHero: SuperHero) = runOnUiThread {
        et_super_hero_name.setText(superHero.name)
        et_super_hero_description.setText(superHero.description)
        iv_super_hero_photo.setImageBackground(superHero.photo)
        cb_is_avenger.isChecked = superHero.isAvenger
    }

    override val activityModules = module {
        bind<EditSuperHeroPresenter>() with provider {
            EditSuperHeroPresenter(this@EditSuperHeroActivity, instance(), instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
        bind<SaveSuperHero>() with provider { SaveSuperHero(instance()) }
    }
}