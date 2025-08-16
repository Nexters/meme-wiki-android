package com.mimu_bird.meme.navigation

import androidx.navigation.NavHostController
import com.meme.search.navigation.SearchNavigationAction
import com.meme.search.navigation.SearchNavigator
import com.mimu_bird.main.navigation.MainNavigationAction
import com.mimu_bird.main.navigation.MainNavigator
import com.seomseom.category.navigation.CategoryNavigationAction
import com.seomseom.category.navigation.CategoryNavigator

/**
 * 앱 전체 네비게이션 라우트 정의
 */
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Search : Screen("search")
    object Category : Screen("category/{categoryId}") {
        fun createRoute(categoryId: Int) = "category/$categoryId"
    }
    object Detail: Screen("detail/{memeId}") {
        fun createRoute(memeId: Int) = "detail/$memeId"
    }
    data object WebView: Screen("web/{url}") {
        fun createRoute(url: String) = "wev/$url"
    }
}

/**
 * 앱 전체 Navigator 구현체
 * 모든 모듈의 Navigator를 구현
 */
class AppNavigator(
    private val navController: NavHostController
) : MainNavigator, SearchNavigator, CategoryNavigator {

    // MainNavigator 구현
    override fun navigate(action: MainNavigationAction) {
        when (action) {
            is MainNavigationAction.NavigateToSearch -> {
                println("DEBUG: NavigateToSearch called")
                navController.navigate(Screen.Search.route)
            }

            is MainNavigationAction.NavigateToCategory -> {
                println("DEBUG: NavigateToCategory called: ${'$'}{action.categoryId}")
                navController.navigate(Screen.Category.createRoute(action.categoryId))
            }

            is MainNavigationAction.NavigateToDetail -> {
                navController.navigate(Screen.Detail.createRoute(action.memeId))
            }

            is MainNavigationAction.NavigateToWebView -> {
                navController.navigate(Screen.WebView.createRoute(action.url))
            }
        }
    }

    // SearchNavigator 구현
    override fun navigate(action: SearchNavigationAction) {
        when (action) {
            is SearchNavigationAction.NavigateToDetail -> {
                navController.navigate(Screen.Detail.createRoute(action.memeId))
            }
            SearchNavigationAction.NavigateToMain -> {
                navController.navigate(Screen.Main.route) {
                    launchSingleTop = true
                }
            }
        }
    }

    // CategoryNavigator 구현
    override fun navigate(action: CategoryNavigationAction) {
        when (action) {
            is CategoryNavigationAction.NavigateToDetail -> {
                navController.navigate(Screen.Detail.createRoute(action.memeId))
            }
            CategoryNavigationAction.NavigateSearch -> {
                navController.navigate(Screen.Search.route)
            }
        }
    }

    // 공통 네비게이션 (뒤로가기)
    fun navigateBack() {
        navController.popBackStack()
    }
} 