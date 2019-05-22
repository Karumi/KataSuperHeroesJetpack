package com.karumi.jetpack.superheroes.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.SuperHeroesApplication
import org.junit.Before
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class FragmentTest<F : Fragment> : ScreenshotTest {

    abstract val fragmentBlock: () -> F

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

    protected fun startFragment(args: Bundle? = null): F {
        val fragment = fragmentBlock()
        launchFragmentInContainer(args, R.style.AppTheme) { fragment as Fragment }
        return fragment
    }

    abstract val testDependencies: Kodein.Module
}