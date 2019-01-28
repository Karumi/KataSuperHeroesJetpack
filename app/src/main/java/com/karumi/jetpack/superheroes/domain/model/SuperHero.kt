package com.karumi.jetpack.superheroes.domain.model

data class SuperHero(
    val name: String,
    val photo: String?,
    val isAvenger: Boolean,
    val description: String
)