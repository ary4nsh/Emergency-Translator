package com.emergency.translator.ui.common

import android.content.Context
import android.content.SharedPreferences
import com.emergency.translator.R

object ThemeManager {

    enum class AccentColor { BLUE, GREEN, RED }

    private const val PREFS = "theme_prefs"
    private const val KEY_ACCENT = "accent_color"
    private const val KEY_DARK = "dark_mode"
    private const val KEY_SKIP_SPLASH = "skip_splash"

    private fun prefs(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getAccentColor(context: Context): AccentColor {
        val name = prefs(context).getString(KEY_ACCENT, AccentColor.BLUE.name)!!
        return runCatching { AccentColor.valueOf(name) }.getOrDefault(AccentColor.BLUE)
    }

    fun isDarkMode(context: Context): Boolean =
        prefs(context).getBoolean(KEY_DARK, false)

    fun setAccentColor(context: Context, accent: AccentColor) {
        prefs(context).edit().putString(KEY_ACCENT, accent.name).apply()
    }

    fun setDarkMode(context: Context, dark: Boolean) {
        prefs(context).edit().putBoolean(KEY_DARK, dark).apply()
    }

    fun markThemeChange(context: Context) {
        prefs(context).edit().putBoolean(KEY_SKIP_SPLASH, true).apply()
    }

    fun consumeSkipSplash(context: Context): Boolean {
        val skip = prefs(context).getBoolean(KEY_SKIP_SPLASH, false)
        if (skip) prefs(context).edit().remove(KEY_SKIP_SPLASH).apply()
        return skip
    }

    fun getThemeResId(context: Context): Int {
        val accent = getAccentColor(context)
        val dark = isDarkMode(context)
        return when {
            dark && accent == AccentColor.BLUE  -> R.style.Theme_EmergencyTranslator_Dark_Blue
            dark && accent == AccentColor.GREEN -> R.style.Theme_EmergencyTranslator_Dark_Green
            dark && accent == AccentColor.RED   -> R.style.Theme_EmergencyTranslator_Dark_Red
            accent == AccentColor.GREEN -> R.style.Theme_EmergencyTranslator_Light_Green
            accent == AccentColor.RED   -> R.style.Theme_EmergencyTranslator_Light_Red
            else                        -> R.style.Theme_EmergencyTranslator_Light_Blue
        }
    }
}
