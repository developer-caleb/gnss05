package kr.loplab.gnss05.common

import android.content.Context
import android.content.SharedPreferences
import kr.loplab.gnss05.common.PrefUtil
import java.lang.StringBuilder

object PrefUtil {
    private val TAG = PrefUtil::class.java.simpleName
    private const val PREFERENCES_NAME = ""
    private const val DEFAULT_VALUE_STRING = ""
    private const val DEFAULT_VALUE_BOOLEAN = false
    private const val DEFAULT_VALUE_INT = -1
    private const val DEFAULT_VALUE_LONG = -1L
    private const val DEFAULT_VALUE_FLOAT = -1f
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    /**
     * String 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setString(context: Context, key: String?, value: String?) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * String array 값 저장
     * @param context
     * @param key
     * @param values
     */
    fun setStringArray(context: Context, key: String?, values: Array<String?>) {
        val builder = StringBuilder()
        for (value in values) builder.append(value).append(",")
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, builder.toString())
        editor.commit()
    }

    /**
     * boolean 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setBoolean(context: Context, key: String?, value: Boolean) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    /**
     * int 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setInt(context: Context, key: String?, value: Any) {
        var putvalue = if (value !is Int)  0 else value
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, putvalue)
        editor.commit()
    }

    /**
     * long 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setLong(context: Context, key: String?, value: Long) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    fun setDouble(context: Context, key: String?, value: Any) {
        var putvalue = if (value !is Double)  0.0 else value
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putLong(key, java.lang.Double.doubleToRawLongBits(putvalue))
        editor.commit()
    }

    /**
     * float 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setFloat(context: Context, key: String?, value: Float) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putFloat(key, value)
        editor.commit()
    }

    /**
     * String 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getString(context: Context, key: String?): String? {
        val prefs = getPreferences(context)
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    /**
     * String 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getStringArray(context: Context, key: String?): Array<String> {
        val prefs = getPreferences(context)
        val values = prefs.getString(key, DEFAULT_VALUE_STRING)
        return if (values!!.length > 0) values.split(",").toTypedArray() else arrayOf()
    }

    /**
     * boolean 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getBoolean(context: Context, key: String?): Boolean {
        val prefs = getPreferences(context)
        return prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN)
    }

    /**
     * int 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getInt(context: Context, key: String?): Int {
        val prefs = getPreferences(context)
        return prefs.getInt(key, DEFAULT_VALUE_INT)
    }

    fun getInt2(context: Context, key: String?): Int {
        val prefs = getPreferences(context)
        var value = prefs.getInt(key, DEFAULT_VALUE_INT)
        if (value == -1) value = 0
        return value
    }

    fun getInt2(context: Context, key: String?, initialvalue: Int): Int {
        val prefs = getPreferences(context)
        var value = prefs.getInt(key, DEFAULT_VALUE_INT)
        if (value == -1) value = initialvalue
        return value
    }

    /**
     * long 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getLong(context: Context, key: String?): Long {
        val prefs = getPreferences(context)
        return prefs.getLong(key, DEFAULT_VALUE_LONG)
    }

    fun getDouble(context: Context, key: String?, defaultValue: Double): Double {
        val prefs = getPreferences(context)
        return if (!prefs.contains(key)) defaultValue else java.lang.Double.longBitsToDouble(
            prefs.getLong(
                key,
                0
            )
        )
    }

    /**
     * float 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getFloat(context: Context, key: String?): Float {
        val prefs = getPreferences(context)
        return prefs.getFloat(key, DEFAULT_VALUE_FLOAT)
    }

    fun getFloat(context: Context, key: String?, initialvalue: Float): Float {
        val prefs = getPreferences(context)
        var value = prefs.getFloat(key, DEFAULT_VALUE_FLOAT)
        if (value == -1f) value = initialvalue
        return value
    }

    /**
     * 키 값 삭제
     * @param context
     * @param key
     */
    fun removeKey(context: Context, key: String?) {
        val prefs = getPreferences(context)
        val edit = prefs.edit()
        edit.remove(key)
        edit.commit()
    }

    /**
     * 모든 저장 데이터 삭제
     * @param context
     */
    fun clear(context: Context) {
        val prefs = getPreferences(context)
        val edit = prefs.edit()
        edit.clear()
        edit.commit()
    }
}