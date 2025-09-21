package com.example.mymeme.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymeme.ui.model.Brush
import com.example.mymeme.ui.model.BrushColor
import com.example.mymeme.ui.model.BrushWidth
import com.example.mymeme.ui.model.Line
import com.mimu_bird.domain.usercase.meme.GetMemeDetailUseCase
import com.mimu_bird.ui.model.BriefMemeUiModel
import com.mimu_bird.ui.model.toBrief
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMemeViewModel @Inject constructor(
    private val getMemeDetailUseCase: GetMemeDetailUseCase
) : ViewModel() {
    private val _meme = MutableStateFlow<BriefMemeUiModel?>(null)
    val meme: StateFlow<BriefMemeUiModel?> = _meme.asStateFlow()

    private val _lines = MutableStateFlow<List<Line>>(emptyList())
    val lines: StateFlow<List<Line>> = _lines.asStateFlow()

    private val _brush = MutableStateFlow<Brush>(Brush.DEFAULT)
    val brush: StateFlow<Brush> = _brush.asStateFlow()

    internal fun fetchMemeDetailInfo(
        memeId: Int
    ) {
        viewModelScope.launch {
            getMemeDetailUseCase(
                id = memeId
            ).onSuccess {
                _meme.value = it.toBrief()
            }
        }
    }

    internal fun addLine(
        line: Line
    ) {
        _lines.value += line
    }

    internal fun changeBrushStroke(
        stoke: BrushWidth
    ) {
        _brush.value = brush.value.copy(width = stoke)
    }

    internal fun changeBrushAlpha(
        alpha: Float
    ) {
        if (alpha < 1f) return
        _brush.value = brush.value.copy(alpha = alpha / 100f)
    }

    internal fun changeBrushColor(
        color: BrushColor
    ) {
        _brush.value = brush.value.copy(color = color)
    }
} 