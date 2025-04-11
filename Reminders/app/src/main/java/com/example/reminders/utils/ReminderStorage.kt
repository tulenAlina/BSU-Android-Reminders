package com.example.reminders.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.reminders.models.Reminder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ReminderStorage {
    private const val PREFS_NAME = "reminder_prefs"
    private const val KEY_REMINDERS = "reminders"

    fun save(context: Context, reminders: List<Reminder>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(reminders)
        editor.putString(KEY_REMINDERS, json)
        editor.apply()
    }

    fun load(context: Context): MutableList<Reminder> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_REMINDERS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Reminder>>() {}.type
        return Gson().fromJson(json, type)
    }
}