package com.mimu_bird.meme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymeme.ui.MyMemeScreen
import com.meme.search.ui.MemeSearchScreen
import com.mimu_bird.detail.ui.MemeDetailScreen
import com.mimu_bird.main.ui.MainScreen
import com.mimu_bird.meme.navigation.AppNavigator
import com.mimu_bird.meme.navigation.Screen
import com.mimu_bird.ui.screen.MemeQuizScreen
import com.seomseom.category.ui.CategoryScreen

/**
 * 네비게이션 그래프 설정
 */
@Composable
fun MemeWikiNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Main.route
) {
    val appNavigator = AppNavigator(navController)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                navigator = appNavigator
            )
        }

        composable(Screen.Search.route) { backStackEntry ->
            MemeSearchScreen(
                navController = navController,
                navigator = appNavigator
            )
        }

        composable(
            route = Screen.Category.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            CategoryScreen(
                categoryId = categoryId,
                navController = navController,
                navigator = appNavigator
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("memeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val memeId = backStackEntry.arguments?.getInt("memeId") ?: 0
            MemeDetailScreen(
                memeId = memeId,
                navController = navController,
                navigator = appNavigator
            )
        }

        composable(
            route = Screen.Quiz.route
        ) { backStackEntry ->
            MemeQuizScreen(navController = navController)
        }

        composable(
            route = Screen.MyMeme.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            MyMemeScreen(
                id = id,
                navigator = appNavigator
            )
        }
    }
}