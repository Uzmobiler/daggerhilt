package uz.mobiler.daggerhilt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.mobiler.daggerhilt.database.entity.UserEntity
import uz.mobiler.daggerhilt.models.GithubUser
import uz.mobiler.daggerhilt.repository.GithubRepository
import uz.mobiler.daggerhilt.utils.GithubResource
import uz.mobiler.daggerhilt.utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val stateFlow = MutableStateFlow<GithubResource>(GithubResource.Loading)

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                stateFlow.value = GithubResource.Loading
                val flow = githubRepository.getUsers()
                flow.catch {
                    stateFlow.value = GithubResource.Error(it.message ?: "")
                }.collect {
                    if (it.isSuccessful) {
                        val list = ArrayList<UserEntity>()
                        it.body()?.forEach {
                            list.add(UserEntity(it.id, it.login, it.avatar_url))
                        }
                        githubRepository.insertUsers(list)
                        stateFlow.value = GithubResource.Success(githubRepository.getDbUsers())
                    } else {

                    }
                }
            }
        } else {
            stateFlow.value = GithubResource.Error("Internet not connected")
        }
    }

    fun getUsers(): StateFlow<GithubResource> {
        return stateFlow
    }
}