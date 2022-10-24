package com.dd.company.baseapp.sharepreference

import android.content.SharedPreferences
import kotlinx.serialization.builtins.serializer

class SettingSharePreferenceStore constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferenceDataStore<String>(sharedPreferences, String.serializer()) {

    fun setPassCode(passCode: String) {
        sharedPreferences.edit()
            .putString(KEY_PASS_CODE, passCode).apply()
    }

    fun getPassCode(): String = sharedPreferences.getString(KEY_PASS_CODE, "") ?: ""

    companion object {
        const val KEY_PASS_CODE = "KEY_PASS_CODE"
    }
}