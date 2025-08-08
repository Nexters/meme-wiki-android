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
    object Category : Screen("category")
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
                println("DEBUG: NavigateToCategory called")
                navController.navigate(Screen.Category.route)
            }
        }
    }

    // SearchNavigator 구현
    override fun navigate(action: SearchNavigationAction) {
        when (action) {
            is SearchNavigationAction.NavigateToDetail -> {
                // TODO: 상세 화면으로 이동
            }
        }
    }

    // CategoryNavigator 구현
    override fun navigate(action: CategoryNavigationAction) {
        when (action) {
            is CategoryNavigationAction.NavigateToDetail -> {
                // TODO: 상세 화면으로 이동
            }
        }
    }

    // 공통 네비게이션 (뒤로가기)
    fun navigateBack() {
        navController.popBackStack()
    }
} 