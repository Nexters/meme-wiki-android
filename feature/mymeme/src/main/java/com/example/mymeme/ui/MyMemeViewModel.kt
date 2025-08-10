package com.example.mymeme.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool
import com.example.mymeme.ui.model.DrawingColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyMemeViewModel : ViewModel() {
    
    private val _drawingPaths = MutableStateFlow<List<DrawingPath>>(emptyList())
    val drawingPaths: StateFlow<List<DrawingPath>> = _drawingPaths.asStateFlow()
    
    private val _currentTool = MutableStateFlow(
        DrawingTool(
            strokeWidth = 4f,
            opacity = 1.0f,
            color = DrawingColor.RED
        )
    )
    val currentTool: StateFlow<DrawingTool> = _currentTool.asStateFlow()
    
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