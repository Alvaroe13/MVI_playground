package editcom.vialsoft.mvipractice.api

import androidx.lifecycle.LiveData
import editcom.vialsoft.mvipractice.model.BlogPost
import editcom.vialsoft.mvipractice.model.User
import editcom.vialsoft.mvipractice.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequest {

    @GET("placeholder/blogs")
    fun getBlogPost() : LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}" )
    fun getUserInfo(
        @Path("userId")  userId: String
    ) : LiveData<GenericApiResponse<User>>

    /* NOTE : @Path when param to pass to server comes before the "?". @Query on the other hand
              is what comes afterwards*/

    // explained : https://stackoverflow.com/questions/37698501/retrofit-2-path-vs-query
}