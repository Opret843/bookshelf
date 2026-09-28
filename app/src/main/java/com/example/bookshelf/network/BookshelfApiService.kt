package com.example.bookshelf.network

import com.example.bookshelf.data.BookItem
import com.example.bookshelf.data.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {

    // https://www.googleapis.com/books/v1/volumes?q=jazz+history
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BooksResponse

    // https://www.googleapis.com/books/v1/volumes/<volume_id>
    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): BookItem
}
