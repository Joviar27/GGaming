package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    @Named(AUTH_INTERCEPTOR)
    fun provideAuthInterceptor(): Interceptor{
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val urlWithQueryParams = originalRequest.url
                .newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(urlWithQueryParams)
                .build()

            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    @Named(LOGGING_INTERCEPTOR)
    fun provideInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named(AUTH_INTERCEPTOR) authInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

    companion object Type{
        const val AUTH_INTERCEPTOR = "auth-interceptor"
        const val LOGGING_INTERCEPTOR = "logging-interceptor"
    }
}