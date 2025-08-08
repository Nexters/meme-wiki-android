package com.seomseom.category.navigation

import com.mimu_bird.common.navigation.NavigationAction

/**
 * Category 모듈 네비게이션 액션 정의
 */
sealed class CategoryNavigationAction : NavigationAction() {
    object NavigateToDetail : CategoryNavigationAction()
}

/**
 * Category 모듈 네비게이션 인터페이스
 */
interface CategoryNavigator {
    fun navigate(action: CategoryNavigationAction)
} 