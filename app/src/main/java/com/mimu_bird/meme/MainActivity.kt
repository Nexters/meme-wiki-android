package com.mimu_bird.meme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.meme.search.business.MemeSearchViewModel
import com.meme.search.ui.MemeSearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MemeSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val keyword = viewModel.keyword.collectAsState()
            val memes = viewModel.memes.collectAsLazyPagingItems()
            MemeSearchScreen(
                keyword = keyword.value,
                memes = memes,
                onChangeKeyword = { viewModel.changeKeyword(it) }
            )
        }
    }
}
