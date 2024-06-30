package top.stillmisty.memescreator.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import top.stillmisty.memescreator.data.model.MemeInfo

interface MemeApi {
    @GET("memes/keys")
    suspend fun getMemesList(): List<String>

    @GET("memes/{key}/info")
    suspend fun getMemeInfo(@Path("key") key: String): MemeInfo

    @Multipart
    @POST("memes/{key}/")
    suspend fun createMeme(
        @Path("key") key: String,
        @Part images: List<MultipartBody.Part>,
        @Part("texts") texts: RequestBody,
        @Part("args") args: RequestBody
    ): ResponseBody
}

object Api {
    const val BASE_URL = "http://10.0.2.2:2233"

    val retrofitService: MemeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(MemeApi::class.java)
    }
}