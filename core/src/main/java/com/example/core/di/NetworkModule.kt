package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
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
    fun provideCertificatePinner(): CertificatePinner {
        val hostName = BuildConfig.HOST_NAME
        return CertificatePinner.Builder()
            .add(hostName, "sha256/Ti5AhU6OcJWgTQfedHg7+xEaS6XE1iOgqkeZX161P7U=")
            .add(hostName, "sha256/yDu9og255NN5GEf+Bwa9rTrqFQ0EydZ0r1FCh9TdAW4=")
            .add(hostName, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
            .add(hostName, "sha256/b0kr6GUvjgiM8He9NBD+PpV3XaC8gCh9D+sTVN/HAbU=")
            .add(hostName, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add(hostName, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        @Named(AUTH_INTERCEPTOR) authInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    companion object Type{
        const val AUTH_INTERCEPTOR = "auth-interceptor"
        const val LOGGING_INTERCEPTOR = "logging-interceptor"
    }
}