package ru.ra66it.updaterforspotify.data.repositories

import ru.ra66it.updaterforspotify.data.network.SpotifyApi
import ru.ra66it.updaterforspotify.domain.model.Result
import ru.ra66it.updaterforspotify.domain.model.Spotify
import ru.ra66it.updaterforspotify.presentation.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyRepository @Inject constructor(
        private val spotifyApi: SpotifyApi
) {

    suspend fun getSpotify() = safeApiCall(
            call = { latestSpotify() }
    )

    private suspend fun latestSpotify(): Result<Spotify> {
        val response = spotifyApi.latestSpotifyAsync().await()
        return if (response.isSuccessful) {
            val body = checkNotNull(response.body())
            Result.Success(body)
        } else {
            val errorBody = checkNotNull(response.errorBody())
            Result.Error(Exception(errorBody.string()))
        }
    }

}
