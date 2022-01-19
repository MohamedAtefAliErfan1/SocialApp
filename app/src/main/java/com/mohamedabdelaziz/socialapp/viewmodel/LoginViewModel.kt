package com.mohamedabdelaziz.socialapp.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.mohamedabdelaziz.socialapp.Repository.Repository
import com.mohamedabdelaziz.socialapp.di.RetrofitModule
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
class LoginViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val loginModelMutableLiveData = MutableLiveData<LoginModel>()
    fun login(username: String?, password: String?) {
        repository.login(username, password)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result: LoginModel? -> loginModelMutableLiveData.setValue(result) },
                { error: Throwable  ->if (error is retrofit2.HttpException){
                    try {
                        val body = Objects.requireNonNull((error as HttpException).response())!!
                            .errorBody()
                        val checkResponseConverter: Converter<ResponseBody?, LoginModel> =
                            RetrofitModule.provideRetrofit().responseBodyConverter(
                                LoginModel::class.java, arrayOfNulls<Annotation>(0)
                            )
                        val checkResponse: LoginModel? = checkResponseConverter.convert(body)
                        loginModelMutableLiveData.setValue(checkResponse!!)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("LoginViewModel", "Error in : " + error.getLocalizedMessage())
                } })
    }
    fun getLoginData():MutableLiveData<LoginModel>
    {
        return loginModelMutableLiveData
    }
}