package com.karumi.jetpack.superheroes.data.repository.room

import androidx.room.Embedded
import androidx.room.Entity
import com.karumi.jetpack.superheroes.domain.model.SuperHero

@Entity(tableName = "superheroes", primaryKeys = ["superhero_id"])
data class SuperHeroEntity(
    @Embedded(prefix = "superhero_") val superHero: SuperHero
)