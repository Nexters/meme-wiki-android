package com.mimu_bird.common.navigation

/**
 * 공통 네비게이션 액션 정의
 */
open class NavigationAction {
    object NavigateBack : NavigationAction()
}

/**
 * 공통 네비게이션 인터페이스
 */
interface Navigator {
    fun navigate(action: NavigationAction)
} 