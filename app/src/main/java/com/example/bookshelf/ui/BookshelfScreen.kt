package com.example.bookshelf.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.bookshelf.data.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bookshelf") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9C4322),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        val viewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)

        when (val state = viewModel.uiState) {
            is BookshelfUiState.Loading -> Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is BookshelfUiState.Success -> BookGrid(
                books = state.books,
                modifier = Modifier.padding(padding)
            )

            is BookshelfUiState.Error -> Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No se pudieron cargar los libros")
                Button(onClick = viewModel::loadBooks) { Text("Reintentar") }
            }
        }
    }
}

@Composable
fun BookGrid(books: List<Book>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ) {
        items(books, key = { it.id }) { book ->
            AsyncImage(
                model = book.thumbnailUrl,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(0.66f)
            )
        }
    }
}
