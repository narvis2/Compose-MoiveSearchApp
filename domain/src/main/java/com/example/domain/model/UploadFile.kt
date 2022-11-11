package com.example.domain.model

// Repository 에서 requestBodyToMultiPart 를 사용하여 UploadFile 👉 @MultiPart.Part 로 바꿔줌
data class UploadFile(
    val file: FileInfoModel,
    val byte: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UploadFile

        if (file != other.file) return false
        if (!byte.contentEquals(other.byte)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = file.hashCode()
        result = 31 * result + byte.contentHashCode()
        return result
    }
}
