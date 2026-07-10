
/**
 * This file contains the TypeConverters for the Room database.
 * It uses Gson to convert complex types to and from JSON strings.
 */
package com.example.everclinic.data.local

import androidx.room.TypeConverter
import com.example.everclinic.data.model.Medication
import com.example.everclinic.data.model.Surgery
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromMedicationList(value: String): List<Medication> {
        val listType = object : TypeToken<List<Medication>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toMedicationList(list: List<Medication>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromSurgeryList(value: String): List<Surgery> {
        val listType = object : TypeToken<List<Surgery>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toSurgeryList(list: List<Surgery>): String {
        return Gson().toJson(list)
    }
}
