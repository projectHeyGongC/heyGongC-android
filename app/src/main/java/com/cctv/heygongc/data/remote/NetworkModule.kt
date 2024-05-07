package com.cctv.heygongc.data.remote

import com.google.gson.GsonBuilder
import com.cctv.heygongc.data.local.LocalDataStoreManager
import com.cctv.heygongc.data.remote.apicalladapter.ApiCallAdapterFactory
import com.cctv.heygongc.data.remote.service.DeviceService
import com.cctv.heygongc.ui.login.GoogleAccessService
import com.cctv.heygongc.ui.login.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHeaderInterceptor(localDataStoreManager: LocalDataStoreManager): HeaderInterceptor {
        return HeaderInterceptor(localDataStoreManager)
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        return GsonConverterFactory.create(
            GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .serializeNulls()
                .setPrettyPrinting()
                .create()
        )
    }

    @Singleton
    @Provides
    fun provideScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val header = Interceptor { chain ->
            val newRequest = chain.request().newBuilder().build()
            chain.proceed(newRequest)
        }

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(header)
            .build()
    }

    // 우리 서버는 Retrofit으로 Provices 만들어 쓰고 Google Access는 Interface로 Provides 만들어서 씀
    @Singleton
    @Provides
    fun provideGoogleAccessRetrofit(
        client: OkHttpClient,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): GoogleAccessService {
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")     // token은 google 서버에서 받아옴
            .client(client)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(ApiCallAdapterFactory.create())
            .build()
            .create(GoogleAccessService::class.java)

    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://15.165.133.184/")     // 이전 우리 서버 주소
            //.baseUrl("https://www.googleapis.com/") // token은 google 서버에서 받아옴
            .client(client)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(ApiCallAdapterFactory.create())
            .build()
    }




    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideDeviceService(retrofit: Retrofit): DeviceService {
        return retrofit.create(DeviceService::class.java)
    }
}