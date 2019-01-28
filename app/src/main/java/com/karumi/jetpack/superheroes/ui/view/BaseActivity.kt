package com.karumi.jetpack.superheroes.ui.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.karumi.jetpack.superheroes.asApp

abstract class BaseActivity : KodeinAppCompatActivity() {

    abstract val layoutId: Int
    abstract val toolbarView: Toolbar
    abstract val activityModules: Module

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationContext.asApp().addModule(activityModules)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setSupportActionBar(toolbarView)
        preparePresenter(intent)
    }

    open fun preparePresenter(intent: Intent?) {}
}