package com.repo.trending.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.repo.trending.network.BaseRepository
import com.repo.trending.network.InterfaceGlobalAPI
import com.repo.trending.repo.TrendAPIPagerRepo
import org.koin.dsl.module
import retrofit2.Retrofit
import com.repo.trending.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient


object NetworkModule {

    const val WaitTimeOut: Long = 30
    const val ConnTimeOut: Long = 10

    val networkModule = module {

        single { provideDefaultOkhttpClient() }
        single { provideGson() }
        single { provideBaseRepository() }
        single { provideRetrofit(get(), get()) }
        single { provideInterfaceGlobalAPI(get()) }
        single { provideTrendAPIPagerRepo(get()) }


    }

    fun provideDefaultOkhttpClient(): OkHttpClient {


        return if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            logging.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .callTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ConnTimeOut, TimeUnit.SECONDS)
                .readTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .callTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ConnTimeOut, TimeUnit.SECONDS)
                .readTimeout(WaitTimeOut, TimeUnit.SECONDS)
                .build()
        }

    }


    fun provideGson() = GsonBuilder()
        .create()

    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    private fun provideInterfaceGlobalAPI(retrofit: Retrofit): InterfaceGlobalAPI =
        retrofit.create(InterfaceGlobalAPI::class.java)

    private fun provideBaseRepository(): BaseRepository = BaseRepository()

    private fun provideTrendAPIPagerRepo(globalAPI: InterfaceGlobalAPI): TrendAPIPagerRepo = TrendAPIPagerRepo(globalAPI)


}