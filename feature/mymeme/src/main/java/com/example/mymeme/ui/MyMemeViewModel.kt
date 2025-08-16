package com.example.mymeme.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool
import com.example.mymeme.ui.model.DrawingColor
import com.example.mymeme.ui.model.Width
import com.mimu_bird.domain.usercase.meme.GetMemeDetailUseCase
import com.mimu_bird.domain.model.meme.MemeDetailModel
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
    
    private val _drawingPaths = MutableStateFlow<List<DrawingPath>>(emptyList())
    val drawingPaths: StateFlow<List<DrawingPath>> = _drawingPaths.asStateFlow()
    
    private val _currentTool = MutableStateFlow(
        DrawingTool(
            strokeWidth = Width.LEVEL3,
            opacity = 1.0f,
            color = DrawingColor.RED
        )
    )
    val currentTool: StateFlow<DrawingTool> = _currentTool.asStateFlow()
    
    private val _memeDetail = MutableStateFlow<MemeDetailModel?>(null)
    val memeDetail: StateFlow<MemeDetailModel?> = _memeDetail.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun fetchMemeDetail(id: String) {
        val memeId = id.toIntOrNull() ?: return
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val detail = getMemeDetailUseCase(memeId)
                _memeDetail.value = detail
                Log.d("MyMemeViewModel", "Meme detail fetched: ${detail.title}, imgUrl: ${detail.imgUrl}")
            } catch (e: Exception) {
                Log.e("MyMemeViewModel", "Failed to fetch meme detail", e)
                _memeDetail.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateTool(tool: DrawingTool) {
        _currentTool.value = tool
    }
    
    fun addDrawingPath(path: DrawingPath) {
        _drawingPaths.value = _drawingPaths.value + path
    }
    
    fun undoLastPath() {
        val currentPaths = _drawingPaths.value
        if (currentPaths.isNotEmpty()) {
            _drawingPaths.value = currentPaths.dropLast(1)
        }
    }
    
    fun clearAllPaths() {
        _drawingPaths.value = emptyList()
    }
    
    fun saveMeme() {
        viewModelScope.launch {
            // TODO: 실제 저장 로직 구현
            // 1. 이미지와 그리기 경로를 합성
            // 2. 파일로 저장
            // 3. 갤러리에 저장
        }
    }
} 