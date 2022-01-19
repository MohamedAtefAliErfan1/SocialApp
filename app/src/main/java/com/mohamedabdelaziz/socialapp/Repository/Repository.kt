package com.mohamedabdelaziz.socialapp.Repository


import com.mohamedabdelaziz.socialapp.model.ChatModel
import javax.inject.Inject
import com.mohamedabdelaziz.socialapp.network.SocialApiService
import com.mohamedabdelaziz.socialapp.model.LoginModel
import com.mohamedabdelaziz.socialapp.model.MessagesModel
import com.mohamedabdelaziz.socialapp.model.SendMessageModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository @Inject constructor(private val socialApiService: SocialApiService) {
    fun login(email: String?, password: String?): Observable<LoginModel?>? {
        return socialApiService.login(email, password)
    }
    fun updateProfile(email: RequestBody?, password: RequestBody?, avatar: MultipartBody.Part?, phone:RequestBody?, name:RequestBody?): Observable<LoginModel?>? {
        return socialApiService.updateProfile(email,name,phone,avatar, password)
    }
    fun getAllUsers(): Observable<ChatModel?>? {
        return socialApiService.getAllUsers()
    }
    fun getmessages(messageId:String): Observable<MessagesModel?>? {
        return socialApiService.getMessages(messageId)
    }
    fun sendMessage(userID: RequestBody?, content: RequestBody?, file: MultipartBody.Part?): Observable<SendMessageModel?>?
    {
        return socialApiService.sendMessage(userID,content,file)
    }

}