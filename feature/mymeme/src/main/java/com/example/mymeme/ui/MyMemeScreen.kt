package com.example.mymeme.ui

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mymeme.ui.component.BottomToolBar
import com.example.mymeme.ui.component.DrawingCanvas
import com.example.mymeme.ui.component.DrawingToolBar
import com.example.mymeme.ui.component.SaveButton
import com.example.mymeme.ui.component.SaveResultDialog
import com.example.mymeme.ui.model.DrawingColor
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool
import com.example.mymeme.ui.model.TextElement
import com.example.mymeme.ui.model.Width
import com.example.mymeme.ui.navigation.MyMemeNavigationAction
import com.example.mymeme.ui.navigation.MyMemeNavigator
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Black
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.typography.toTextStyle
import java.io.File
import java.io.FileOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMemeScreen(
    id: String,
    viewModel: MyMemeViewModel = hiltViewModel(),
    navigator: MyMemeNavigator
) {
    var drawingPaths by remember { mutableStateOf<List<DrawingPath>>(emptyList()) }
    var textElements by remember { mutableStateOf<List<TextElement>>(emptyList()) }

    // Undo/Redo 히스토리 스택 추가
    var deletedDrawingPaths by remember { mutableStateOf<List<DrawingPath>>(emptyList()) }
    var deletedTextElements by remember { mutableStateOf<List<TextElement>>(emptyList()) }

    var currentTool by remember {
        mutableStateOf(
            DrawingTool(
                strokeWidth = Width.LEVEL3,
                opacity = 1.0f,
                color = DrawingColor.RED
            )
        )
    }
    var isDrawingToolBarVisible by remember { mutableStateOf(false) }
    var isTextMode by remember { mutableStateOf(false) }
    var isBottomToolBarVisible by remember { mutableStateOf(true) }
    var isEditMode by remember { mutableStateOf(true) }  // 편집 모드 상태 (true: 편집, false: 저장)

    // 저장 결과 다이얼로그 상태
    var showSaveSuccessDialog by remember { mutableStateOf(false) }
    var showSaveErrorDialog by remember { mutableStateOf(false) }

    // 캡처 중 UI 요소 표시 상태
    var showTopBar by remember { mutableStateOf(true) }
    var showSaveButton by remember { mutableStateOf(true) }

    val interactionSource = remember { MutableInteractionSource() }

    // ViewModel 상태 수집
    val memeDetail by viewModel.memeDetail.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    // id 파라미터로 밈 상세 정보 요청
    LaunchedEffect(id) {
        viewModel.fetchMemeDetail(id)
    }

    val context = LocalContext.current

    // Undo 함수: 마지막에 추가된 요소를 삭제하고 히스토리에 저장
    val onUndo = {
        if (drawingPaths.isNotEmpty()) {
            val lastPath = drawingPaths.last()
            deletedDrawingPaths = deletedDrawingPaths + lastPath
            drawingPaths = drawingPaths.dropLast(1)
        } else if (textElements.isNotEmpty()) {
            val lastText = textElements.last()
            deletedTextElements = deletedTextElements + lastText
            textElements = textElements.dropLast(1)
        }
    }

    // Redo 함수: 삭제된 요소를 복원
    val onRedo = {
        if (deletedTextElements.isNotEmpty()) {
            val lastDeletedText = deletedTextElements.last()
            textElements = textElements + lastDeletedText
            deletedTextElements = deletedTextElements.dropLast(1)
        } else if (deletedDrawingPaths.isNotEmpty()) {
            val lastDeletedPath = deletedDrawingPaths.last()
            drawingPaths = drawingPaths + lastDeletedPath
            deletedDrawingPaths = deletedDrawingPaths.dropLast(1)
        }
    }

    // 새로운 요소가 추가될 때 히스토리 초기화 (새로운 작업이 시작되면 이전 히스토리는 무효화)
    val clearHistory = {
        deletedDrawingPaths = emptyList()
        deletedTextElements = emptyList()
    }

    // Undo/Redo 가능 여부 확인
    val canUndo = drawingPaths.isNotEmpty() || textElements.isNotEmpty()
    val canRedo = deletedDrawingPaths.isNotEmpty() || deletedTextElements.isNotEmpty()

    // 갤러리에 저장하는 간단한 함수
    fun saveToGallery(bitmap: Bitmap) {
        try {
            Log.d("MyMemeScreen", "갤러리 저장 시작: ${bitmap.width}x${bitmap.height} 크기")
            val filename = "meme_${System.currentTimeMillis()}.jpg"
            Log.d("MyMemeScreen", "파일명: $filename")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10 이상: MediaStore API 사용
                Log.d("MyMemeScreen", "Android 10+ MediaStore API 사용")
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val uri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                uri?.let { imageUri ->
                    Log.d("MyMemeScreen", "MediaStore URI 생성 성공: $imageUri")
                    context.contentResolver.openOutputStream(imageUri)?.use { outputStream ->
                        val compressed =
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        if (compressed) {
                            showSaveSuccessDialog = true
                        } else {
                            Log.e("MyMemeScreen", "❌ 이미지 압축 실패")
                            showSaveErrorDialog = true
                        }
                    } ?: run {
                        Log.e("MyMemeScreen", "❌ MediaStore OutputStream 열기 실패")
                        showSaveErrorDialog = true
                    }
                } ?: run {
                    Log.e("MyMemeScreen", "❌ MediaStore URI 생성 실패")
                    showSaveErrorDialog = true
                }
            } else {
                // Android 9 이하: File API 사용
                val picturesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                if (!picturesDir.exists()) {
                    val created = picturesDir.mkdirs()
                    Log.d("MyMemeScreen", "Pictures 디렉토리 생성: $created")
                }

                val imageFile = File(picturesDir, filename)
                Log.d("MyMemeScreen", "저장 경로: ${imageFile.absolutePath}")

                FileOutputStream(imageFile).use { outputStream ->
                    val compressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    if (compressed) {
                        Log.d("MyMemeScreen", "파일 저장 성공: ${imageFile.length()} bytes")
                    } else {
                        Log.e("MyMemeScreen", "❌ 파일 압축 실패")
                        showSaveErrorDialog = true
                        return
                    }
                }

                // MediaStore에 등록
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.DATA, imageFile.absolutePath)
                }

                val insertUri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                if (insertUri != null) {
                    showSaveSuccessDialog = true
                    Log.i("MyMemeScreen", "✅ 갤러리 저장 완료: $filename (${imageFile.absolutePath})")
                } else {
                    Log.e("MyMemeScreen", "❌ MediaStore 등록 실패")
                    showSaveErrorDialog = true
                }
            }

        } catch (e: Exception) {
            Log.e("MyMemeScreen", "❌ 갤러리 저장 실패", e)
            showSaveErrorDialog = true
        }
    }
    // 캡처 요청 상태
    var captureRequested by remember { mutableStateOf(false) }

    // DrawingCanvas에서 캡처 결과를 받아서 처리하는 콜백
    val onCanvasCaptured: (Bitmap?) -> Unit = { bitmap ->
        if (bitmap != null) {
            saveToGallery(bitmap)
            bitmap.recycle()
        } else {
            showSaveErrorDialog = true
        }
        // 캡처 요청 상태 리셋
        captureRequested = false

        // 캡처 완료 후 UI 요소들 다시 표시
        showTopBar = true
        showSaveButton = true
    }

    // DrawingCanvas에 캡처 요청하는 함수
    fun requestCanvasCapture() {
        // 캡처 전에 UI 요소들 숨기기
        showTopBar = false
        showSaveButton = false

        // 잠시 대기 후 캡처 요청 (UI가 완전히 숨겨진 후)
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            captureRequested = true
        }, 100)
    }

    // 권한 요청을 위한 launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // DrawingCanvas에 캡처 요청
            requestCanvasCapture()
        } else {
            showSaveErrorDialog = true
        }
    }

    // 권한 확인 후 저장하는 간단한 함수
    fun checkPermissionAndSave() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                // Android 10 이상: 권한 불필요
                requestCanvasCapture()
            }

            else -> {
                // Android 9 이하: 권한 확인 필요
                when {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        // DrawingCanvas에 캡처 요청
                        requestCanvasCapture()
                    }

                    else -> {
                        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = {
                        Text(
                            text = "",
                        )
                    },
                    navigationIcon = {
                        Spacer(Modifier.width(14.dp))
                        if (isEditMode) {
                            IconButton(onClick = {
                                navigator.navigate(MyMemeNavigationAction.NavigateBack)
                            }) {
                                Text(
                                    text = "취소",
                                    style = Body2.toTextStyle(),
                                    color = Color.White,
                                )
                            }
                        } else {
                            IconButton(onClick = {
                                navigator.navigate(MyMemeNavigationAction.NavigateBack)
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_back),
                                    contentDescription = "뒤로 가기",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    actions = {
                        if (isEditMode) {
                            // 편집 모드일 때: "완료" 텍스트 표시
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource
                                    ) {
                                        isEditMode = !isEditMode
                                        if (isEditMode) {
                                            isBottomToolBarVisible = true  // 편집 모드로 전환 시 플로팅 버튼 표시
                                        } else {
                                            isBottomToolBarVisible = false  // 저장 모드로 전환 시 플로팅 버튼 숨김
                                        }
                                    }
                                    .padding(end = 14.dp)
                            ) {
                                Text(
                                    text = "완료",
                                    style = Body2.toTextStyle(),
                                    color = Color.White,
                                )
                            }
                        } else {
                            // 저장 모드일 때: "편집" 텍스트 표시
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource
                                    ) {
                                        isEditMode = !isEditMode
                                        if (isEditMode) {
                                            isBottomToolBarVisible = true  // 편집 모드로 전환 시 플로팅 버튼 표시
                                        } else {
                                            isBottomToolBarVisible = false  // 저장 모드로 전환 시 플로팅 버튼 숨김
                                        }
                                    }
                                    .padding(end = 14.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_toolbox),
                                    contentDescription = "편집 모드 전환",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier.background(color = Black)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black),
            contentAlignment = Alignment.BottomCenter
        ) {
            // 그리기 캔버스 (캡처 콜백 추가)
            DrawingCanvas(
                imageUrl = memeDetail?.imgUrl ?: "", // API 응답에서 받은 imgUrl 사용
                drawingPaths = drawingPaths,
                textElements = textElements,
                currentTool = currentTool,
                isTextMode = isTextMode,
                onPathAdded = { path ->
                    clearHistory() // 새로운 경로가 추가되면 히스토리 초기화
                    drawingPaths = drawingPaths + path
                },
                onTextAdded = { textElement ->
                    clearHistory() // 새로운 텍스트가 추가되면 히스토리 초기화
                    textElements = textElements + textElement
                    // 텍스트 모드는 사용자가 직접 변경해야 함
                },
                onTextUpdated = { updatedText ->
                    textElements = textElements.map {
                        if (it.id == updatedText.id) updatedText else it
                    }
                },
                onTextDeleted = { textId ->
                    val deletedText = textElements.find { it.id == textId }
                    if (deletedText != null) {
                        deletedTextElements = deletedTextElements + deletedText
                        textElements = textElements.filter { it.id != textId }
                    }
                },
                modifier = Modifier.fillMaxSize(),
                onCaptureRequest = onCanvasCaptured,
                captureRequested = captureRequested
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // 그리기 도구 바 (우측 하단)
                if (isEditMode && isDrawingToolBarVisible) {
                    DrawingToolBar(
                        currentTool = currentTool,
                        onToolChanged = { tool ->
                            currentTool = tool
                        },
                        onClose = {
                            isDrawingToolBarVisible = false
                        },
                        isExpanded = isDrawingToolBarVisible
                    )
                }

                // 하단 도구 바
                if (isBottomToolBarVisible) {
                    BottomToolBar(
                        isDrawingToolBarVisible = isDrawingToolBarVisible,
                        isTextMode = isTextMode,
                        onDrawingToolBarVisibilityChanged = { newVisibility ->
                            isDrawingToolBarVisible = newVisibility
                            // 그리기 도구 바가 켜지면 텍스트 모드 끄기
                            if (newVisibility) {
                                isTextMode = false
                            }
                        },
                        onTextModeChanged = { newTextMode ->
                            isTextMode = newTextMode
                            // 텍스트 모드가 켜지면 그리기 도구 바 끄기
                            if (newTextMode) {
                                isDrawingToolBarVisible = false
                            }
                        },
                        onUndo = onUndo,
                        onRedo = onRedo,
                        canUndo = canUndo,
                        canRedo = canRedo
                    )
                }
            }

            // 저장 버튼 (편집 모드가 아니고 showSaveButton이 true일 때만 표시)
            if (!isEditMode && showSaveButton) {
                SaveButton(
                    onSave = {
                        // 권한 확인 후 PixelCopy를 사용한 화면 캡처 및 저장
                        checkPermissionAndSave()
                    }
                )
            }
        }

        // 저장 성공 다이얼로그
        if (showSaveSuccessDialog) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SaveResultDialog(
                    isSuccess = true,
                    onDismiss = { showSaveSuccessDialog = false }
                )
            }
        }

        // 저장 실패 다이얼로그
        if (showSaveErrorDialog) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SaveResultDialog(
                    isSuccess = false,
                    onDismiss = { showSaveErrorDialog = false }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyMemeScreen() {
    val mockNavigator = object : MyMemeNavigator {
        override fun navigate(action: MyMemeNavigationAction) {
            // Preview에서는 아무것도 하지 않음
        }
    }

    MyMemeScreen(
        id = "1",
        navigator = mockNavigator
    )
}