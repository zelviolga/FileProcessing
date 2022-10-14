package coding.withze.fileprocessing.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File

object Utils {

    fun getPdfUrl(): String {
        return "https://kotlinlang.org/assets/kotlin-media-kit.pdf"
    }

    fun getPdfNameFromAssets(): String {
        return "kotlin-media-kit.pdf"
    }

    //    GET ROOT path
    fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }



}