package com.example.bookshelf

import com.example.bookshelf.data.BookItem
import com.example.bookshelf.data.BooksResponse
import com.example.bookshelf.data.ImageLinks
import com.example.bookshelf.data.VolumeInfo
import com.example.bookshelf.network.BookshelfApiService

// Servicio falso: no hace ninguna solicitud real a Google Books
class FakeBookshelfApiService : BookshelfApiService {

    override suspend fun searchBooks(query: String): BooksResponse =
        BooksResponse(listOf(BookItem("1", null), BookItem("2", null)))

    override suspend fun getBook(id: String): BookItem =
        BookItem(
            id = id,
            volumeInfo = VolumeInfo(
                title = "Libro $id",
                imageLinks = ImageLinks("http://fake.com/$id.jpg")
            )
        )
}
