package com.mohamedabdelaziz.socialapp.util

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import java.lang.Exception
import java.lang.ref.WeakReference

object PrefUtils {
    /**
     * Called to save supplied value in shared preferences against given key.
     *
     * @param context Context of caller activity
     * @param key     Key of value to save against
     * @param value   Value to save
     */
    fun saveToPrefs(context: Context?, key: String?, value: Any) {
        val contextWeakReference = WeakReference(context)
        if (contextWeakReference.get() != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
            val editor = prefs.edit()
            if (value is Int) {
                editor.putInt(key, value.toInt())
            } else if (value is String) {
                editor.putString(key, value.toString())
            } else if (value is Boolean) {
                editor.putBoolean(key, value)
            } else if (value is Long) {
                editor.putLong(key, value.toLong())
            } else if (value is Float) {
                editor.putFloat(key, value.toFloat())
            } else if (value is Double) {
                editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            }
            editor.commit()
        }
    }

    /**
     * Called to retrieve required value from shared preferences, identified by given key.
     * Default value will be returned of no value found or error occurred.
     *
     * @param context      Context of caller activity
     * @param key          Key to find value against
     * @param defaultValue Value to return if no data found against given key
     * @return Return the value found against given key, default if not found or any error occurs
     */
    fun getFromPrefs(context: Context?, key: String?, defaultValue: Any): Any? {
        val contextWeakReference = WeakReference(context)
        if (contextWeakReference.get() != null) {
            val sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
            try {
                if (defaultValue is String) {
                    return sharedPrefs.getString(key, defaultValue.toString())
                } else if (defaultValue is Int) {
                    return sharedPrefs.getInt(key, defaultValue.toInt())
                } else if (defaultValue is Boolean) {
                    return sharedPrefs.getBoolean(key, defaultValue)
                } else if (defaultValue is Long) {
                    return sharedPrefs.getLong(key, defaultValue.toLong())
                } else if (defaultValue is Float) {
                    return sharedPrefs.getFloat(key, defaultValue.toFloat())
                } else if (defaultValue is Double) {
                    return java.lang.Double.longBitsToDouble(
                        sharedPrefs.getLong(
                            key, java.lang.Double.doubleToLongBits(
                                defaultValue
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("Execption", e.message!!)
                return defaultValue
            }
        }
        return defaultValue
    }

    /**
     * @param context Context of caller activity
     * @param key     Key to delete from SharedPreferences
     */
    fun removeFromPrefs(context: Context?, key: String?) {
        val contextWeakReference = WeakReference(context)
        if (contextWeakReference.get() != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
            val editor = prefs.edit()
            editor.remove(key)
            editor.commit()
        }
    }
fun clearPrefs(context: Context?)
{
    val contextWeakReference = WeakReference(context)
    if (contextWeakReference.get() != null) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
        editor.commit()
}
    fun hasKey(context: Context?, key: String?): Boolean {
        val contextWeakReference = WeakReference(context)
        if (contextWeakReference.get() != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
            return prefs.contains(key)
        }
        return false
    }
}
}