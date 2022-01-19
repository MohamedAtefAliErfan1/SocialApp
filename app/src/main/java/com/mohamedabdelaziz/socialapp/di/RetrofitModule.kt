package com.mohamedabdelaziz.socialapp.di

import android.util.Log
import dagger.hilt.InstallIn
import com.mohamedabdelaziz.socialapp.BaseApplication
import javax.inject.Singleton
import com.mohamedabdelaziz.socialapp.network.SocialApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import com.google.gson.GsonBuilder

import com.mohamedabdelaziz.socialapp.util.PrefKeys
import com.mohamedabdelaziz.socialapp.util.PrefUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideSocialApiService(): SocialApiService {

        return provideRetrofit()
            .create(SocialApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Log.e(
                "API",
                message
            )
        }

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->

                //shared prefs for token
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + PrefUtils.getFromPrefs(
                            BaseApplication.appContext,
                            PrefKeys.USER_TOKEN,
                            ""
                        )
                    )
                    .addHeader("Accept", "application/json").build()
                chain.proceed(newRequest)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl("https://appsocial.courzone.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    }
}