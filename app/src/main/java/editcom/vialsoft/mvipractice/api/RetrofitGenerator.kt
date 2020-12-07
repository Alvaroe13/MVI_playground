package editcom.vialsoft.mvipractice.api

import editcom.vialsoft.mvipractice.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGenerator {

    private const val BASE_URL = "https://open-api.xyz/"

    private val retrofitBuilder : Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiConnection : ApiRequest by lazy {
        retrofitBuilder.build().create(ApiRequest::class.java)
    }
}