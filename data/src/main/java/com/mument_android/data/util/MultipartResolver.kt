package com.mument_android.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject


class MultipartResolver @Inject constructor(@ApplicationContext private val context: Context) {

    fun createImageMultiPart(array: ByteArray): MultipartBody.Part? {
        val file = File(replaceFileName(UUID.randomUUID().toString()))
        "image/png".toMediaTypeOrNull()?.let {
            val surveyBody = array.toRequestBody(it)
            return MultipartBody.Part.createFormData("image", file.name, surveyBody)
        } ?: return null
    }

    @Throws(Exception::class)
    fun getRotatedBitmap(bitmap: Bitmap?, degrees: Int): Bitmap? {
        if (bitmap == null) return null
        if (degrees == 0) return bitmap
        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun getOrientationOfImage(uri: Uri): Int {
        // uri -> inputStream
        val inputStream = context.contentResolver.openInputStream(uri)
        val exif: ExifInterface? = try {
            ExifInterface(inputStream!!)
        } catch (e: IOException) {
            return -1
        }
        inputStream.close()

        // 회전된 각도 알아내기
        val orientation =
            exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        if (orientation != -1) {
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                ExifInterface.ORIENTATION_ROTATE_270 -> return 270
            }
        }
        return 0
    }

    private fun replaceFileName(fileName: String): String = "${fileName.replace(".", "_")}.png"

    companion object {
        private const val RESIZED_SIZE = 720f
    }
}