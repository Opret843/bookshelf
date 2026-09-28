package com.example.bookshelf.data

import com.example.bookshelf.network.BookshelfApiService
import retrofit2.HttpException
import java.io.IOException
interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>
}

class NetworkBookshelfRepository(
    private val api: BookshelfApiService
) : BookshelfRepository {

    override suspend fun getBooks(query: String): List<Book> {
        // 1) Primera solicitud: buscar y quedarnos con los ids
        val ids = api.searchBooks(query).items.orEmpty().map { it.id }

        // 2) Una solicitud por libro, una tras otra en la misma corrutina
        return ids.take(20).mapNotNull { id ->
            try {
                val info = api.getBook(id).volumeInfo
                val thumb = info?.imageLinks?.thumbnail ?: return@mapNotNull null
                Book(
                    id = id,
                    title = info.title ?: "Sin título",
                    thumbnailUrl = thumb.replace("http://", "https://")
                )
            } catch (e: IOException) {
                null
            } catch (e: HttpException) {
                null
            }
        }
    }
}
