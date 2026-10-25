package com.coderpakistan.navigationcompose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.coderpakistan.navigationcompose.scenes.ListDetailScene
import com.coderpakistan.navigationcompose.scenes.rememberListDetailSceneStrategy
import com.coderpakistan.navigationcompose.screens.DifferentScreen
import com.coderpakistan.navigationcompose.screens.TodoDetailScreen
import com.coderpakistan.navigationcompose.screens.TodoListScreen


import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.coderpakistan.navigationcompose.screens.ChangeSettingScreen
import com.coderpakistan.navigationcompose.screens.SettingsScreen

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

    val resultStore = rememberResultStore()

    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val isMultiPane = windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)

    val isTopLevelRoute = navigationState.currentRoute in TOP_LEVEL_DESTINATIONS.keys
    val isListDetailActive = isMultiPane && navigationState.currentRoute is Route.TodoDetail
    val shouldShowBottomBar = isTopLevelRoute || isListDetailActive

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (shouldShowBottomBar) {
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
            sceneStrategies = listOf(rememberListDetailSceneStrategy()),
            entries = navigationState.toEntries(
                entryProvider {
                    entry<Route.TodoList>(
                        metadata = ListDetailScene.listPane()
                    ){
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
                    entry<Route.TodoDetail>(
                        metadata = ListDetailScene.detailPane()
                    ) {
                        TodoDetailScreen(
                            todo = it.todo
                        )
                    }
                    entry<Route.DifferentScreen> {
                        DifferentScreen()
                    }
                    entry<Route.Settings> {
                        SettingsScreen(
                            resultStore = resultStore,
                            onChangeSettingClick = {
                                navigator.navigate(Route.ChangeSetting)
                            }
                        )
                    }
                    entry<Route.ChangeSetting> {
                        ChangeSettingScreen(
                            resultStore = resultStore,
                            onSave = {
                                navigator.goBack()
                            }
                        )
                    }
                }
            )
        )
    }
}