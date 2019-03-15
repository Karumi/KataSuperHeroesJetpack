package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.jetpack.superheroes.SuperHeroesApplication
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class AcceptanceTest<T : Activity>(clazz: Class<T>) : ScreenshotTest {

    companion object {
        private const val doNotLaunchActivityAtLunch = false
    }

    @Rule
    @JvmField
    val testRule: IntentsTestRule<T> = IntentsTestRule(clazz, true, doNotLaunchActivityAtLunch)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val app = ApplicationProvider.getApplicationContext<SuperHeroesApplication>()
        app.override(Kodein.Module("Base test dependencies", allowSilentOverride = true) {
            import(testDependencies, allowOverride = true)
            bind<Executor>() with instance(Executor {
                InstrumentationRegistry.getInstrumentation().runOnMainSync { it.run() }
            })
        })
    }

    fun startActivity(args: Bundle = Bundle()): T {
        val intent = Intent()
        intent.putExtras(args)
        return testRule.launchActivity(intent)
    }

    abstract val testDependencies: Kodein.Module
}