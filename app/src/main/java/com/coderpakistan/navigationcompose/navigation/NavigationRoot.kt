package com.coderpakistan.navigationcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.coderpakistan.navigationcompose.auth.AuthNavigation
import com.coderpakistan.navigationcompose.screens.TodoDetailScreen
import com.coderpakistan.navigationcompose.screens.TodoListScreen
import com.coderpakistan.navigationcompose.screens.TodoNavigation

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val rootBackStack = rememberNavBackStack(Route.Auth)
    NavDisplay(
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = rootBackStack,
        entryProvider = entryProvider {
            entry<Route.Auth> {
                AuthNavigation(
                    onLogin = {
                        rootBackStack.remove(Route.Auth)
                        rootBackStack.add(Route.Todo)
                    }
                )
            }
            entry<Route.Todo> {
                TodoNavigation()
            }

        }
    )
}