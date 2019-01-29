package com.karumi.jetpack.superheroes.data.repository.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.karumi.jetpack.superheroes.domain.model.SuperHero

@Entity(tableName = "superheroes")
data class SuperHeroEntity(
    @PrimaryKey val id: String,
    @Embedded(prefix = "superhero_") val superHero: SuperHero
)