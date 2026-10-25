package com.coderpakistan.navigationcompose.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.coderpakistan.navigationcompose.auth.LoginScreen
import com.coderpakistan.navigationcompose.auth.LoginViewModel
import com.coderpakistan.navigationcompose.auth.RegisterScreen
import com.coderpakistan.navigationcompose.auth.RegisterViewModel
import com.coderpakistan.navigationcompose.auth.SharedAuthViewModel
import com.coderpakistan.navigationcompose.navigation.Route
import com.coderpakistan.navigationcompose.screens.TodoListScreen

@Composable
fun TodoNavigation(
    modifier: Modifier = Modifier
) {
    val todoBackStack = rememberNavBackStack(Route.Todo.TodoList)
    NavDisplay(
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = todoBackStack,
        entryProvider = entryProvider {
            entry<Route.Todo.TodoList> {
                TodoListScreen(
                    onTodoClick = {
                        todoBackStack.add(Route.Todo.TodoDetail(it))
                    }
                )
            }
            entry<Route.Todo.TodoDetail> {
                TodoDetailScreen(
                    todo = it.todo
                )
            }


        }
    )
}