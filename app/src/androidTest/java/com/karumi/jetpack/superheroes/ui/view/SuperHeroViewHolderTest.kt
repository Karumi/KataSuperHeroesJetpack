package com.karumi.jetpack.superheroes.ui.view

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.databinding.SuperHeroRowBinding
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.ui.presenter.SuperHeroesPresenter
import com.karumi.jetpack.superheroes.ui.view.adapter.SuperHeroViewHolder
import org.junit.Test
import org.mockito.Mockito.mock

class SuperHeroViewHolderTest : ScreenshotTest {

    @Test
    fun showsAnySuperHero() {
        val superHero = givenASuperHero()
        val holder = givenASuperHeroViewHolder()

        holder.render(superHero)

        compareScreenshot(holder, R.dimen.super_hero_row_height)
    }

    @Test
    fun showsSuperHeroesWithLongNames() {
        val superHero = givenASuperHeroWithALongName()
        val holder = givenASuperHeroViewHolder()

        holder.render(superHero)

        compareScreenshot(holder, R.dimen.super_hero_row_height)
    }

    @Test
    fun showsSuperHeroesWithLongDescriptions() {
        val superHero = givenASuperHeroWithALongDescription()
        val holder = givenASuperHeroViewHolder()

        holder.render(superHero)

        compareScreenshot(holder, R.dimen.super_hero_row_height)
    }

    @Test
    fun showsAvengersBadge() {
        val superHero = givenASuperHero(isAvenger = true)
        val holder = givenASuperHeroViewHolder()

        holder.render(superHero)

        compareScreenshot(holder, R.dimen.super_hero_row_height)
    }

    private fun givenASuperHeroViewHolder(): SuperHeroViewHolder =
        runOnUi {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val inflater = LayoutInflater.from(context)
            val binding: SuperHeroRowBinding =
                DataBindingUtil.inflate(inflater, R.layout.super_hero_row, null, false)
            SuperHeroViewHolder(
                binding,
                mock<SuperHeroesPresenter>(SuperHeroesPresenter::class.java)
            )
        }

    private fun givenASuperHeroWithALongDescription(): SuperHero {
        val superHeroName = "Super Hero Name"
        val superHeroDescription = """
            |Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            |incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
            |ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
            |voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
            |proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            |""".trimMargin()
        val isAvenger = false
        return givenASuperHero("#1", superHeroName, superHeroDescription, isAvenger)
    }

    private fun givenASuperHeroWithALongName(): SuperHero {
        val superHeroId = "Super Hero Id"
        val superHeroName = """
            |Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            |incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
            |ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
            |voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
            |proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            |""".trimMargin()
        val superHeroDescription = "Description Super Hero"
        val isAvenger = false
        return givenASuperHero(superHeroId, superHeroName, superHeroDescription, isAvenger)
    }

    private fun givenASuperHero(
        superHeroId: String = "Super Hero Id",
        superHeroName: String = "Super Hero Name",
        superHeroDescription: String = "Super Hero Description",
        isAvenger: Boolean = false
    ): SuperHero = SuperHero(superHeroId, superHeroName, null, isAvenger, superHeroDescription)
}

private fun <T> runOnUi(block: () -> T): T {
    var response: T? = null
    InstrumentationRegistry.getInstrumentation().runOnMainSync { response = block() }
    return response!!
}