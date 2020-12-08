package editcom.vialsoft.mvipractice.repository

import androidx.lifecycle.LiveData
import editcom.vialsoft.mvipractice.api.RetrofitGenerator
import editcom.vialsoft.mvipractice.model.BlogPost
import editcom.vialsoft.mvipractice.model.User
import editcom.vialsoft.mvipractice.ui.main.state.MainViewState
import editcom.vialsoft.mvipractice.util.ApiSuccessResponse
import editcom.vialsoft.mvipractice.util.Resource
import editcom.vialsoft.mvipractice.util.GenericApiResponse

private const val TAG = "MainRepository"

object MainRepository {

    fun getBlogPosts(): LiveData<Resource<MainViewState>> {

         return object : NetworkBoundResource< List<BlogPost> , MainViewState>() {

             override fun successResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                 mediatorResult.value = Resource.data(
                     MainViewState(
                         blogList = response.body,
                         null
                     ) ,
                     null
                 )
             }

             override fun callToApiService(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitGenerator.apiConnection.getBlogPost()
             }

         }.responseAsLiveData()
    }

    fun getUserInfo(userId: String): LiveData<Resource<MainViewState>> {

        return object : NetworkBoundResource<User, MainViewState>(){
            override fun successResponse(response: ApiSuccessResponse<User>) {
                mediatorResult.value = Resource.data(
                    MainViewState(
                        user = response.body
                    ),
                    null
                )
            }

            override fun callToApiService(): LiveData<GenericApiResponse<User>> {
                return RetrofitGenerator.apiConnection.getUserInfo(userId)
            }

        }.responseAsLiveData()

    }

}