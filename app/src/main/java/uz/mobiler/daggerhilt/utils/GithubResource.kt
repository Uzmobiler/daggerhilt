package uz.mobiler.daggerhilt.utils

import uz.mobiler.daggerhilt.database.entity.UserEntity
sealed class GithubResource {

    object Loading : GithubResource()
    data class Success(val list: List<UserEntity>) : GithubResource()
    data class Error(val message: String) : GithubResource()
}