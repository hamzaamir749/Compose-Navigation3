package com.coderpakistan.navigationcompose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.coderpakistan.navigationcompose.screens.DifferentScreen
import com.coderpakistan.navigationcompose.screens.TodoDetailScreen
import com.coderpakistan.navigationcompose.screens.TodoListScreen


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState(
        startRoute = Route.TodoList,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    val isTopLevelRoute = navigationState.currentRoute in TOP_LEVEL_DESTINATIONS.keys

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (isTopLevelRoute) {
                TodoNavigationBar(
                    selectedKey = navigationState.topLevelRoute,
                    onSelectKey = {
                        navigator.navigate(it)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = navigator::goBack,
            entries = navigationState.toEntries(
                entryProvider {
                    entry<Route.TodoList> {
                        TodoListScreen(
                            onTodoClick = {
                                navigator.navigate(Route.TodoDetail(it))
                            }
                        )
                    }
                    entry<Route.TodoFavorites> {
                        TodoListScreen(
                            onTodoClick = {
                                navigator.navigate(Route.DifferentScreen)
                            }
                        )
                    }
                    entry<Route.TodoDetail> {
                        TodoDetailScreen(
                            todo = it.todo
                        )
                    }
                    entry<Route.DifferentScreen> {
                        DifferentScreen()
                    }
                    entry<Route.Settings> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Settings")
                        }
                    }
                }
            )
        )
    }
}