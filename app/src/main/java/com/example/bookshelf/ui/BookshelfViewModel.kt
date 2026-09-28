package com.example.bookshelf.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.Book
import com.example.bookshelf.data.BookshelfRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import android.util.Log
import java.io.IOException

sealed interface BookshelfUiState {
    object Loading : BookshelfUiState
    data class Success(val books: List<Book>) : BookshelfUiState
    object Error : BookshelfUiState
}

class BookshelfViewModel(
    private val repository: BookshelfRepository
) : ViewModel() {

    var uiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            uiState = BookshelfUiState.Loading
            uiState = try {
                BookshelfUiState.Success(repository.getBooks("jazz history"))
            } catch (e: IOException) {
                Log.e("Bookshelf", "Error de red", e)
                BookshelfUiState.Error
            } catch (e: HttpException) {
                Log.e("Bookshelf", "Error HTTP ${e.code()}", e)
                BookshelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as BookshelfApplication
                BookshelfViewModel(app.container.bookshelfRepository)
            }
        }
    }
}
