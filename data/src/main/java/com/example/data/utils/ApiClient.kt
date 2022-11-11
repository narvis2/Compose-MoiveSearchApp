package com.example.data.utils

import com.example.domain.model.UploadFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Multipart 안에 RequestBody 로 다른 Query 가 들어가는 경우 사용
 * ex_) 👇
 * @Multipart
 * @POST("${API_NAME}/qna/write")
 * suspend fun bbsQNAWrite(
 *     @Part("subject") subject: RequestBody,
 *     @Part("contents") contents: RequestBody,
 *     @Part("category") category: RequestBody,
 *     @Part multiPart: MultipartBody.Part?,
 * ): Response<BaseResponse>
 */
fun setValue(value: String): RequestBody = value
    .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaType())

fun setValue(value: Long): RequestBody = value.toString()
    .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaType())

fun setValue(value: Int): RequestBody = value.toString()
    .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaType())

// ex_) requestBodyToMultiPart("profile2", profile.file.name, byteArrayToRequestBody(profile))
fun requestBodyToMultiPart(
    title: String,
    fileName: String,
    body: RequestBody
): MultipartBody.Part {
    return MultipartBody.Part.createFormData(title, fileName, body)
}

fun byteArrayToRequestBody(profile: UploadFile): RequestBody {
    return profile.byte.toRequestBody(profile.file.mimType.toMediaType(), 0, profile.byte.size)
}
