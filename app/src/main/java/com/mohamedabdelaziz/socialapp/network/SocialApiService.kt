package com.mohamedabdelaziz.socialapp.network

import com.mohamedabdelaziz.socialapp.model.ChatModel
import com.mohamedabdelaziz.socialapp.model.LoginModel
import com.mohamedabdelaziz.socialapp.model.MessagesModel
import com.mohamedabdelaziz.socialapp.model.SendMessageModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface SocialApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Observable<LoginModel?>?

    @Multipart
    @POST("update")
    fun updateProfile(
        @Part("email") email: RequestBody?,
        @Part("name") name: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part avatar: MultipartBody.Part?,
        @Part("password") password: RequestBody?
    ): Observable<LoginModel?>?

    @GET("Users")
    fun getAllUsers():Observable<ChatModel?>?

    @GET("Chat/Messages/{id}")
    fun getMessages(@Path("id")id:String):Observable<MessagesModel?>?

    @Multipart
    @POST("Chat/Send")
    fun sendMessage(
        @Part("user_id") user_id: RequestBody?,
        @Part("contant") contant: RequestBody?,
        @Part avatar: MultipartBody.Part?,
    ): Observable<SendMessageModel?>?

}