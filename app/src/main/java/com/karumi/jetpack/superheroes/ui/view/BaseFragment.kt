package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.karumi.jetpack.superheroes.common.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), KodeinAware {

    private val appKodein by closestKodein { requireActivity() }
    override val kodein: Kodein = Kodein.lazy {
        extend(appKodein)
        includeViewModelFactory()
        import(activityModules)
    }

    abstract val layoutId: Int
    abstract val activityModules: Kodein.Module
    abstract val viewModel: AndroidViewModel
    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        binding.setLifecycleOwner(this)
        configureBinding(binding)
        return binding.root
    }

    private fun Kodein.MainBuilder.includeViewModelFactory() {
        bind<ViewModelProvider.Factory>() with singleton {
            ViewModelFactory(instance(), instance())
        }
    }

    abstract fun configureBinding(binding: T)
}