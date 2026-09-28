package com.example.bookshelf.data

// Respuesta de la búsqueda: GET volumes?q=...
data class BooksResponse(val items: List<BookItem>?)

// Un libro del JSON (Gson lo llena segun el nombre de las claves)
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo?
)

data class VolumeInfo(
    val title: String?,
    val imageLinks: ImageLinks?
)

data class ImageLinks(val thumbnail: String?)

// Modelo limpio que usa la UI
data class Book(
    val id: String,
    val title: String,
    val thumbnailUrl: String
)
