package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.karumi.jetpack.superheroes.common.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), KodeinAware {

    private val appKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(appKodein)
        includeViewModelFactory()
        import(activityModules)
    }

    abstract val layoutId: Int
    abstract val toolbarView: Toolbar
    abstract val activityModules: Kodein.Module
    abstract val viewModel: AndroidViewModel
    private lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        configureBinding(binding)
        setSupportActionBar(toolbarView)
    }

    private fun Kodein.MainBuilder.includeViewModelFactory() {
        bind<ViewModelProvider.Factory>() with singleton {
            ViewModelFactory(instance(), instance())
        }
    }

    abstract fun configureBinding(binding: T)
}