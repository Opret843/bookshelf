package com.example.bookshelf

import com.example.bookshelf.data.Book
import com.example.bookshelf.data.NetworkBookshelfRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBookshelfRepositoryTest {

    @Test
    fun getBooks_devuelveLibrosConHttps() = runTest {
        val repo = NetworkBookshelfRepository(FakeBookshelfApiService())

        val result = repo.getBooks("jazz")

        assertEquals(
            listOf(
                Book("1", "Libro 1", "https://fake.com/1.jpg"),
                Book("2", "Libro 2", "https://fake.com/2.jpg")
            ),
            result
        )
    }
}
