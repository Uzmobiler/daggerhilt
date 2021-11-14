package uz.mobiler.daggerhilt.networking

import retrofit2.Response
import retrofit2.http.GET
import uz.mobiler.daggerhilt.models.GithubUser

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<GithubUser>>
}