package com.karumi.jetpack.superheroes.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleObserver
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    private val appKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(appKodein)
        import(activityModules)
    }
    abstract val presenter: LifecycleObserver
    abstract val layoutId: Int
    abstract val toolbarView: Toolbar
    abstract val activityModules: Kodein.Module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
        setContentView(layoutId)
        setSupportActionBar(toolbarView)
        preparePresenter(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(presenter)
    }

    open fun preparePresenter(intent: Intent?) {}
}