package com.karumi.data.repository

import com.karumi.domain.model.SuperHero
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.funktionale.either.Either
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@Suppress("IllegalIdentifier")
class SuperHeroRepositoryTest {
    companion object {
        val ANY_SUPERHERO = SuperHero(
            id = "anyId",
            name = "anyName",
            photo = null,
            isAvenger = false,
            description = "any description")

        val ANY_NAME = "anyName"
    }

    @Mock private lateinit var dataSource1: SuperHeroDataSource
    @Mock private lateinit var dataSource2: SuperHeroDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should return get all data from the first datasource if info is updated`() {
        givenDataSourceWithData(dataSource1)
        val repository = SuperHeroRepository(listOf(dataSource1, dataSource2))

        repository.getAllSuperHeroes()

        verify(dataSource1, times(1)).getAll()
        verify(dataSource2, never()).getAll()
    }

    @Test
    fun `should return get all data from the second datasource if first is not updated`() {
        givenDataSourceWithOldData(dataSource1)
        givenDataSourceWithData(dataSource2)
        val repository = SuperHeroRepository(listOf(dataSource1, dataSource2))

        repository.getAllSuperHeroes()

        verify(dataSource1, never()).getAll()
        verify(dataSource2, times(1)).getAll()
    }

    @Test
    fun `should call populate with new data for each datasource after obtain getAll`() {
        givenDataSourceWithOldData(dataSource1)
        val superheroes = givenDataSourceWithData(dataSource2)
        val repository = SuperHeroRepository(listOf(dataSource1, dataSource2))

        repository.getAllSuperHeroes()

        verify(dataSource1, times(1)).populate(superheroes)
        verify(dataSource2, times(1)).populate(superheroes)
    }

    @Test
    fun `should return get superhero from the first datasource if first is contains the superhero`() {
        givenDataSourceWithData(dataSource1)
        val repository = SuperHeroRepository(listOf(dataSource1, dataSource2))

        repository.getByName(ANY_NAME)

        verify(dataSource1, times(1)).get(ANY_NAME)
        verify(dataSource2, never()).get(ANY_NAME)
    }

    @Test
    fun `should return get superhero from the second if first is not update`() {
        givenDataSourceWithOldData(dataSource1)
        givenDataSourceWithData(dataSource2)
        val repository = SuperHeroRepository(listOf(dataSource1, dataSource2))

        repository.getByName(ANY_NAME)

        verify(dataSource1, never()).get(ANY_NAME)
        verify(dataSource2, times(1)).get(ANY_NAME)
    }

    @Test
    fun `should return get superhero from the second if first does not contains the key`() {
        givenDataSourceWithoutTheKey(dataSource1)
        givenDataSourceWithData(dataSource2)
        val repository = SuperHeroRepository(listOf(dataSource1, dataSource2))

        repository.getByName(ANY_NAME)

        verify(dataSource1, never()).get(ANY_NAME)
        verify(dataSource2, times(1)).get(ANY_NAME)
    }

    private fun givenDataSourceWithOldData(dataSource: SuperHeroDataSource) {
        whenever(dataSource.isUpdated()).thenReturn(false)
    }

    private fun givenDataSourceWithoutTheKey(dataSource: SuperHeroDataSource) {
        whenever(dataSource.isUpdated()).thenReturn(true)
        whenever(dataSource.contains(any())).thenReturn(false)
    }

    private fun givenDataSourceWithData(dataSource: SuperHeroDataSource): List<SuperHero> {
        val superheroes = listOf(ANY_SUPERHERO)
        whenever(dataSource.isUpdated()).thenReturn(true)
        whenever(dataSource.getAll()).thenReturn(Either.right(superheroes))
        whenever(dataSource.contains(any())).thenReturn(true)
        whenever(dataSource.get(any())).thenReturn(Either.right(ANY_SUPERHERO))
        return superheroes
    }

}