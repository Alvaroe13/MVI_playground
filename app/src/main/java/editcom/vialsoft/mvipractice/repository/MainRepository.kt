package editcom.vialsoft.mvipractice.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import editcom.vialsoft.mvipractice.api.RetrofitGenerator
import editcom.vialsoft.mvipractice.ui.main.state.MainViewState
import editcom.vialsoft.mvipractice.util.ApiEmptyResponse
import editcom.vialsoft.mvipractice.util.ApiErrorResponse
import editcom.vialsoft.mvipractice.util.ApiSuccessResponse
import editcom.vialsoft.mvipractice.util.DataState

private const val TAG = "MainRepository"

object MainRepository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitGenerator.apiConnection.getBlogPost()) { blogsFromApi ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()

                        when (blogsFromApi) {

                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(blogList = blogsFromApi.body, null),
                                    null
                                )
                                Log.d(TAG, "onActive: successful response")
                            }

                            is ApiEmptyResponse -> {
                                value =
                                    DataState.error(message = "204 HTTP code. Response from server came empty")
                                Log.d(TAG, "onActive: blogList empty response= ")
                            }

                            is ApiErrorResponse -> {
                                value = DataState.error(message = blogsFromApi.errorMessage)
                                Log.d(TAG, "onActive: blogList Error= ")
                            }
                        }
                    }

                }
            }
    }

    fun getUserInfo(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitGenerator.apiConnection.getUserInfo(userId)) { userInfoApiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()

                        when (userInfoApiResponse) {

                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(user = userInfoApiResponse.body),
                                    null
                                )
                                Log.d(TAG, "onActive: successful response")
                            }

                            is ApiEmptyResponse -> {
                                value =DataState.error(
                                    message = "204 HTTP code. Response from server came empty"
                                )
                                Log.d(TAG, "onActive: user empty response= ")
                            }

                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = userInfoApiResponse.errorMessage
                                )
                                Log.d(TAG, "onActive: user Error = ${userInfoApiResponse.errorMessage}")
                            }
                        }
                    }

                }
            }
    }

}