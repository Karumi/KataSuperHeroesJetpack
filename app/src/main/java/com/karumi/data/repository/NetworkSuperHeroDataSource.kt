package com.karumi.data.repository

import com.karumi.domain.model.*
import com.karumi.marvelapiclient.CharacterApiClient
import com.karumi.marvelapiclient.MarvelAuthApiException
import com.karumi.marvelapiclient.model.CharacterDto
import com.karumi.marvelapiclient.model.MarvelImage
import com.karumi.marvelapiclient.model.MarvelResponse
import org.funktionale.either.Either
import java.net.ConnectException

class NetworkSuperHeroDataSource(private val apiClient: CharacterApiClient) : SuperHeroDataSource {
    companion object {
        val PAGE_SIZE = 10
        val RESPONSE_OK = 200
    }

    override fun get(key: String): Either<DomainError, SuperHero> =
        try {
            mapResponse(apiClient.getCharacter(key)) { mapSuperHero(it) }
        } catch (exception: Exception) {
            Either.left(mapException(exception))
        }

    override fun isUpdated(): Boolean = true

    override fun getAll(): Either<DomainError, List<SuperHero>> =
        try {
            mapResponse(apiClient.getAll(0, PAGE_SIZE),
                {
                    it.characters.map { mapSuperHero(it) }
                })
        } catch (exception: Exception) {
            Either.left(mapException(exception))
        }

    private fun mapSuperHero(characterDto: CharacterDto): SuperHero =
        SuperHero(
            id = characterDto.id,
            name = characterDto.name,
            description = characterDto.description,
            isAvenger = false,
            photo = characterDto.thumbnail.getImageUrl(MarvelImage.Size.PORTRAIT_MEDIUM)
        )

    private fun <T, R> mapResponse(
        response: MarvelResponse<T>, mapper: (T) -> R):
        Either<DomainError, R> =
        when (response.code) {
            RESPONSE_OK -> Either.right(mapper(response.response))
            else -> Either.left(UnknownDomainError())
        }

    private fun mapException(exception: Exception): DomainError =
        when {
            exception is MarvelAuthApiException -> AuthDomainError()
            exception.cause is ConnectException -> NotInternetDomainError()
            else -> UnknownDomainError()
        }

}