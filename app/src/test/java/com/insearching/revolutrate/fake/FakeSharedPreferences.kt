package com.insearching.revolutrate.fake

import android.content.SharedPreferences

class FakeSharedPreferences : SharedPreferences {
    private val data = mutableMapOf<String, String?>()

    override fun getString(key: String?, defValue: String?): String? = data[key] ?: defValue

    override fun edit(): SharedPreferences.Editor = Editor()

    inner class Editor : SharedPreferences.Editor {
        override fun putString(key: String?, value: String?): SharedPreferences.Editor {
            data[key ?: ""] = value
            return this
        }

        override fun apply() {}
        override fun commit(): Boolean = true
        override fun clear(): SharedPreferences.Editor { data.clear(); return this }
        override fun remove(key: String?): SharedPreferences.Editor { data.remove(key); return this }
        override fun putLong(key: String?, value: Long) = this
        override fun putInt(key: String?, value: Int) = this
        override fun putBoolean(key: String?, value: Boolean) = this
        override fun putFloat(key: String?, value: Float) = this
        override fun putStringSet(key: String?, values: MutableSet<String>?) = this
    }

    override fun contains(key: String?): Boolean = data.containsKey(key)
    override fun getBoolean(key: String?, defValue: Boolean): Boolean = defValue
    override fun getInt(key: String?, defValue: Int): Int = defValue
    override fun getAll(): MutableMap<String, *> = data
    override fun getLong(key: String?, defValue: Long): Long = defValue
    override fun getFloat(key: String?, defValue: Float): Float = defValue
    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? = defValues
    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {}
    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {}
}
