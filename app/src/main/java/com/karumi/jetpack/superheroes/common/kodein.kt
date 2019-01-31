package com.karumi.jetpack.superheroes.common

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

inline fun <reified T> T.module(
    allowSilentOverride: Boolean = false,
    prefix: String = "",
    noinline init: Kodein.Builder.() -> Unit
) where T : KodeinAware =
    Kodein.Module(
        "${T::class.java.simpleName} dependencies",
        allowSilentOverride,
        prefix,
        init
    )
