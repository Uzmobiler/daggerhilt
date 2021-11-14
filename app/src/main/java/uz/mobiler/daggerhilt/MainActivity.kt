package uz.mobiler.daggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import uz.mobiler.daggerhilt.utils.GithubResource
import uz.mobiler.daggerhilt.viewmodels.GithubViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope {

    private val githubViewModel: GithubViewModel by viewModels()

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            withContext(Dispatchers.Main){
                githubViewModel.getUsers().collect {
                    when (it) {
                        is GithubResource.Success -> {
                            Log.d(TAG, "onCreate: ${it.list}")
                        }
                        is GithubResource.Error -> {
                            Log.d(TAG, "onCreate: ${it.message}")
                        }
                        is GithubResource.Loading -> {

                        }
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}