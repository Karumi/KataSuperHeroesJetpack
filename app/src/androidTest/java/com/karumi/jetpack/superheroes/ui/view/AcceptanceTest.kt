package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.karumi.jetpack.superheroes.SuperHeroesApplication
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.mockito.MockitoAnnotations
import java.util.concurrent.ExecutorService
import java.util.concurrent.FutureTask

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class AcceptanceTest<T : Activity>(clazz: Class<T>) : ScreenshotTest {

    companion object {
        private const val doNotLaunchActivityAtLunch = false
    }

    @Rule
    @JvmField
    val testRule: IntentsTestRule<T> = IntentsTestRule(clazz, true, doNotLaunchActivityAtLunch)

    private val executorServiceOnUiThread = mock<ExecutorService> {
        on(it.submit(any())).thenAnswer { invocation ->
            testRule.runOnUiThread { (invocation.getArgument(0) as Runnable).run() }
            FutureTask { null }
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val app = ApplicationProvider.getApplicationContext<SuperHeroesApplication>()
        app.override(Kodein.Module("Base test dependencies", allowSilentOverride = true) {
            import(testDependencies, allowOverride = true)
            bind<ExecutorService>() with instance(executorServiceOnUiThread)
        })
    }

    fun startActivity(args: Bundle = Bundle()): T {
        val intent = Intent()
        intent.putExtras(args)
        return testRule.launchActivity(intent)
    }

    abstract val testDependencies: Kodein.Module
}