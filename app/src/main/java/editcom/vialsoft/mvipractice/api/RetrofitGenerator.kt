package editcom.vialsoft.mvipractice.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGenerator {

    const val BASE_URL = "https://open-api.xyz"

    val retrofitBuilder : Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiConnection : ApiRequest by lazy {
        retrofitBuilder.build().create(ApiRequest::class.java)
    }
}