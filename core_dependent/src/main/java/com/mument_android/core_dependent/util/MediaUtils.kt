package com.mument_android.core_dependent.util

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.mument_android.core_dependent.R
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MediaUtils(private val context: Context) {
    fun getBitmapUri(view: View, fileNameToSave: String): Pair<File, Uri>? {
        val bitmap = getBitmap(view)
        val file = bitmapToFile(bitmap, fileNameToSave)
        val uri = file?.let {
            FileProvider.getUriForFile(context, "${context.packageName}.provider", it)
        }
        return if (file != null && uri != null) file to uri else null
    }

    private fun bitmapToFile(bitmap: Bitmap?, fileNameToSave: String): File? {
        var file: File? = null
        return try {
            file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + File.separator + fileNameToSave + ".jpeg")
            file.createNewFile()

            val bitmapByteArray = ByteArrayOutputStream().let { bos ->
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, bos)
                bos.toByteArray()
            }

            FileOutputStream(file).let { fos ->
                fos.write(bitmapByteArray)
                fos.flush()
                fos.close()
            }

            file
        } catch (e: Exception) {
            e.printStackTrace()
            file
        }
    }

    private fun getBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmap
    }

    private fun getRoundedCornerBitmap(bitmap: Bitmap): Bitmap? {
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        canvas.drawColor(Color.TRANSPARENT)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = 11.dpToPx(context).toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    private fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap? {
        try {
            if (source.height >= source.width) {
                if (source.height <= maxLength) {
                    return source
                }

                val aspectRatio = source.width.toDouble() / source.height.toDouble()
                val targetWidth = (maxLength * aspectRatio).toInt()
                return Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
            } else {
                if (source.width <= maxLength) {
                    return source
                }

                val aspectRatio = source.height.toDouble() / source.width.toDouble()
                val targetHeight = (maxLength * aspectRatio).toInt()
                return Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
            }
        } catch (e: Exception) {
            return source
        }
    }
}