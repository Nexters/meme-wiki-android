package com.mimu_bird.detail

import com.mimu_bird.common.navigation.NavigationAction

sealed class MemeDetailNavigationAction : NavigationAction() {
    data class NavigateToMyMeme(
        val id: String
    ) : MemeDetailNavigationAction()
}

interface MemeDetailNavigator {
    fun navigate(action: MemeDetailNavigationAction)
}