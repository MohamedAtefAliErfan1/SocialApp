package com.mohamedabdelaziz.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamedabdelaziz.socialapp.Repository.Repository
import com.mohamedabdelaziz.socialapp.di.RetrofitModule
import com.mohamedabdelaziz.socialapp.model.MessagesModel
import com.mohamedabdelaziz.socialapp.model.SendMessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatBodyViewModel @Inject constructor(private val repository: Repository) :ViewModel() {

    private val chatBodyListMutableLiveData = MutableLiveData<MessagesModel>()
    private val sendMessageMutableLiveData = MutableLiveData<SendMessageModel>()
    fun getMessages(id: String?) {
        if (id != null) {
            repository.getmessages(id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { result: MessagesModel? -> chatBodyListMutableLiveData.setValue(result) },
                    { error: Throwable  ->if (error is retrofit2.HttpException){
                        try {
                            val body = Objects.requireNonNull((error as HttpException).response())!!
                                .errorBody()
                            val checkResponseConverter: Converter<ResponseBody?, MessagesModel> =
                                RetrofitModule.provideRetrofit().responseBodyConverter(
                                    MessagesModel::class.java, arrayOfNulls<Annotation>(0)
                                )
                            val checkResponse: MessagesModel? = checkResponseConverter.convert(body)
                            chatBodyListMutableLiveData.setValue(checkResponse!!)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } else {
                        Log.e("ChatBodyViewModel", "Error in : " + error.getLocalizedMessage())
                    } })
        }
    }
    fun sendMessage(userId:RequestBody, content:RequestBody, file: MultipartBody.Part?) {

            repository.sendMessage(userId,content, file)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { result: SendMessageModel? -> sendMessageMutableLiveData.setValue(result) },
                    { error: Throwable  ->if (error is retrofit2.HttpException){
                        try {
                            val body = Objects.requireNonNull((error as HttpException).response())!!
                                .errorBody()
                            val checkResponseConverter: Converter<ResponseBody?, SendMessageModel> =
                                RetrofitModule.provideRetrofit().responseBodyConverter(
                                    SendMessageModel::class.java, arrayOfNulls<Annotation>(0)
                                )
                            val checkResponse: SendMessageModel? = checkResponseConverter.convert(body)
                            sendMessageMutableLiveData.setValue(checkResponse!!)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } else {
                        Log.e("ChatBodyViewModel", "Error in : " + error.getLocalizedMessage())
                    } })

    }
    fun getMessageModel():MutableLiveData<MessagesModel>
    {
        return chatBodyListMutableLiveData
    }
    fun getSendMessageModel():MutableLiveData<SendMessageModel>
    {
        return sendMessageMutableLiveData
    }
}