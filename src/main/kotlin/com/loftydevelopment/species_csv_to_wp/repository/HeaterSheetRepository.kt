package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.csv.CsvReader
import com.loftydevelopment.species_csv_to_wp.csv.HeaterSheet
import com.loftydevelopment.species_csv_to_wp.model.Heater

class HeaterSheetRepository : HeaterRepository {
    private lateinit var heaterSheet: HeaterSheet

    init {
        val heaterCsvPath = "C:/Users/conno/Documents/Web/Aquarium API/heaters.csv"
        val heaterRows = CsvReader.readCsv(heaterCsvPath)
        heaterSheet = HeaterSheet(heaterRows)
    }

    override fun getHeaters(): List<Heater> = heaterSheet.heaters
}