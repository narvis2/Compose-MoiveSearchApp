package com.example.moviesearchapp.view.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Html
import android.webkit.MimeTypeMap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.FileInfoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

fun String.htmlToString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this).toString()
    }
}

// Lifecycle 이 필요한 경우 and Dispose 일 경우 Job 해제
@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    DisposableEffect(Unit) {
        val job = lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect {
                action(it)
            }
        }

        onDispose {
            job.cancel()
        }
    }
}

// Lifecycle 이 필요한 경우 상태 공유
@Composable
inline fun <reified T> Flow<T>.observeOnLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect {
                action(it)
            }
        }
    }
}

// bitmap -> ByteArray 로 압축 > UpLoadFile 의 두 번째 인자에 들어감
fun compressImageWithJpeg(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

// UpLoadFile 의 첫 번째 인자에 들어감
fun getFileInfo(context: Context, contentUri: Uri): FileInfoModel {
    // We tried querying the ContentResolver...didn't work out
    // Try extracting the last path segment
    var fileName: String = contentUri.lastPathSegment!!
    var fileSize = -1L
    var mimeType = "*/*"

    when (contentUri.scheme) {
        ContentResolver.SCHEME_FILE -> {
            val file = File(contentUri.path!!)

            fileName = file.name
            fileSize = file.length()

            val fileExtension: String = file.extension

            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.lowercase(Locale.ENGLISH)
            )?.let {
                mimeType = it
            }
        }

        ContentResolver.SCHEME_CONTENT -> {
            @Suppress("DEPRECATION")
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                OpenableColumns.DISPLAY_NAME,
                OpenableColumns.SIZE
            )

            context.contentResolver.getType(contentUri)?.let { mimeType = it }

            context.contentResolver.query(contentUri, projection, null, null, null).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    // Try extracting content size
                    val sizeIndex: Int = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (sizeIndex != -1) {
                        fileSize = cursor.getLong(sizeIndex)
                    }

                    // Try extracting display name
                    var name: String? = null

                    // Strategy: The column name is NOT guaranteed to be indexed by DISPLAY_NAME
                    // so, we try two methods
                    var nameIndex: Int = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        name = cursor.getString(nameIndex)
                    }

                    if (nameIndex == -1 || name == null) {
                        @Suppress("DEPRECATION")
                        nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                        if (nameIndex != -1) {
                            name = cursor.getString(nameIndex)
                        }
                    }

                    if (name != null) {
                        fileName = name
                    }
                }
            }
        }

        else -> {
            throw RuntimeException("Only scheme content:// or file:// is accepted")
        }
    }

    return FileInfoModel(fileName, fileSize, mimeType)
}