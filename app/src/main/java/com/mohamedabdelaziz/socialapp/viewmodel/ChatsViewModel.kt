package com.mohamedabdelaziz.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamedabdelaziz.socialapp.Repository.Repository
import com.mohamedabdelaziz.socialapp.di.RetrofitModule
import com.mohamedabdelaziz.socialapp.model.ChatModel
import com.mohamedabdelaziz.socialapp.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel  @Inject constructor(private val repository: Repository) :ViewModel() {

    private val chatListMutableLiveData = MutableLiveData<ChatModel>()
    fun getAllUsers() {
        repository.getAllUsers()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result: ChatModel? -> chatListMutableLiveData.setValue(result) },
                { error: Throwable  ->if (error is retrofit2.HttpException){
                    try {
                        val body = Objects.requireNonNull((error as HttpException).response())!!
                            .errorBody()
                        val checkResponseConverter: Converter<ResponseBody?, ChatModel> =
                            RetrofitModule.provideRetrofit().responseBodyConverter(
                                ChatModel::class.java, arrayOfNulls<Annotation>(0)
                            )
                        val checkResponse: ChatModel? = checkResponseConverter.convert(body)
                        chatListMutableLiveData.setValue(checkResponse!!)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("ChatViewModel", "Error in : " + error.getLocalizedMessage())
                } })
    }
    fun getChatModel():MutableLiveData<ChatModel>
    {
        return chatListMutableLiveData
    }
}