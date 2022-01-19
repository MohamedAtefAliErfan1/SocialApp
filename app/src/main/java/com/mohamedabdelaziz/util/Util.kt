package com.mohamedabdelaziz.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log


object Util {
    fun getRealPathFromUri(ctx: Context?, uri: Uri?): String? {
        val filePathColumn = arrayOf(MediaStore.Files.FileColumns.DATA)
        var picturePath: String? =null
        val cursor: Cursor? = uri?.let {
            ctx?.getContentResolver()?.query(
                it, filePathColumn,
                null, null, null
            )
        }
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
            picturePath = cursor.getString(columnIndex)
            Log.e("", "picturePath : $picturePath")
            cursor.close()
        }
        return picturePath
    }
}