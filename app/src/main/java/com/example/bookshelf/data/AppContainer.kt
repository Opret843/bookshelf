package com.example.bookshelf.data

import com.example.bookshelf.network.BookshelfApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

interface AppContainer {
    val bookshelfRepository: BookshelfRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://www.googleapis.com/books/v1/"
    private val apiKey = "AIzaSyDSHtLP4DLknxmGuPNH_wVCCVgkvEznKSk"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("key", apiKey)
                .build()
            chain.proceed(original.newBuilder().url(url).build())
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val bookshelfRepository: BookshelfRepository by lazy {
        NetworkBookshelfRepository(service)
    }
}
