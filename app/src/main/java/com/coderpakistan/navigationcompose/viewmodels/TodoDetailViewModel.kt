package com.coderpakistan.navigationcompose.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoDetailViewModel(private val todo: String) : ViewModel() {
    private val _todos = MutableStateFlow(
        TodoDetailState(todo )
    )
    val todos = _todos.asStateFlow()
}

data class TodoDetailState(val todo: String)