package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.bindViewModel
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.common.viewModel
import com.karumi.jetpack.superheroes.databinding.EditSuperHeroFragmentBinding
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.domain.usecase.SaveSuperHero
import com.karumi.jetpack.superheroes.ui.viewmodel.EditSuperHeroViewModel
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class EditSuperHeroFragment : BaseFragment<EditSuperHeroFragmentBinding>() {
    override val layoutId = R.layout.edit_super_hero_fragment
    override val viewModel: EditSuperHeroViewModel by viewModel()
    private val args: EditSuperHeroFragmentArgs by navArgs()

    override fun configureBinding(binding: EditSuperHeroFragmentBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isClosing.observe(this, Observer { requireActivity().onBackPressed() })
        viewModel.prepare(args.superHeroId)
    }

    override val activityModules = module {
        bindViewModel<EditSuperHeroViewModel>() with provider {
            EditSuperHeroViewModel(instance(), instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
        bind<SaveSuperHero>() with provider { SaveSuperHero(instance()) }
    }
}
