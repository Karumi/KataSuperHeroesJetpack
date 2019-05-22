package com.karumi.jetpack.superheroes.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), KodeinAware {

    private val appKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(appKodein)
        import(activityModules)
    }
    abstract val presenter: LifecycleObserver
    abstract val layoutId: Int
    abstract val toolbarView: Toolbar
    abstract val activityModules: Kodein.Module
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
        binding = DataBindingUtil.setContentView(this, layoutId)
        configureBinding(binding)
        setSupportActionBar(toolbarView)
        prepare(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(presenter)
    }

    abstract fun configureBinding(binding: T)
    open fun prepare(intent: Intent?) {}
}