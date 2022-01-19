package com.mohamedabdelaziz.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamedabdelaziz.socialapp.Repository.Repository
import com.mohamedabdelaziz.socialapp.di.RetrofitModule
import com.mohamedabdelaziz.socialapp.model.LoginModel
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
class ProfileViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val profileModelMutableLiveData = MutableLiveData<LoginModel>()
    fun updateProfile(email: RequestBody?, password: RequestBody?, avatar: MultipartBody.Part?, phone:RequestBody?, name:RequestBody?) {
        repository.updateProfile(email, password, avatar, phone, name)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result: LoginModel? -> profileModelMutableLiveData.setValue(result) },
                { error: Throwable  ->if (error is retrofit2.HttpException){
                    try {
                        val body = Objects.requireNonNull((error as HttpException).response())!!
                            .errorBody()
                        val checkResponseConverter: Converter<ResponseBody?, LoginModel> =
                            RetrofitModule.provideRetrofit().responseBodyConverter(
                                LoginModel::class.java, arrayOfNulls<Annotation>(0)
                            )
                        val checkResponse: LoginModel? = checkResponseConverter.convert(body)
                        profileModelMutableLiveData.setValue(checkResponse!!)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("ProfileViewModel", "Error in : " + error.getLocalizedMessage())
                } })
    }
    fun getProfileData():MutableLiveData<LoginModel>
    {
        return profileModelMutableLiveData
    }
}