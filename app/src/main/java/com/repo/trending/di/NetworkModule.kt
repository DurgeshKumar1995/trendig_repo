package com.repo.trending.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.repo.trending.network.InterfaceGlobalAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import com.repo.trending.BuildConfig
import com.repo.trending.utils.Constants
import okhttp3.Interceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient


object NetworkModule {

    private const val WaitTimeOut: Long = 30
    private const val ConnTimeOut: Long = 10

    val networkModule = module {

        single { provideDefaultOkhttpClient() }
        single { provideGson() }
        single { provideRetrofit(get(), get()) }
        single { provideInterfaceGlobalAPI(get()) }


    }

    private fun provideDefaultOkhttpClient(): OkHttpClient {


        return if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            logging.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .callTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ConnTimeOut, TimeUnit.SECONDS)
                .readTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("authorization", "Basic ${Constants.REPO_SHARE_KEY}")
                        .build()
                    chain.proceed(request)
                }
                .build()
        } else {
            OkHttpClient.Builder()
                .callTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ConnTimeOut, TimeUnit.SECONDS)
                .readTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("authorization", "Basic ${Constants.REPO_SHARE_KEY}")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }

    }


    private fun provideGson() = GsonBuilder()
        .create()

    private fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    private fun provideInterfaceGlobalAPI(retrofit: Retrofit): InterfaceGlobalAPI =
        retrofit.create(InterfaceGlobalAPI::class.java)

}