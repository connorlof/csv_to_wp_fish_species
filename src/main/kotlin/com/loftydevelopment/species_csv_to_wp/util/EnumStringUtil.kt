package com.loftydevelopment.species_csv_to_wp.util

object EnumStringUtil {
    fun toEnumString(string: String) = string.replace(" ", "_").toUpperCase()

    fun enumNameToHumanReadable(string: String) = string.replace("_", "").capitalize()
}