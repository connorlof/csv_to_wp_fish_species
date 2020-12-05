package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.csv.CsvReader
import com.loftydevelopment.species_csv_to_wp.csv.TankSheet
import com.loftydevelopment.species_csv_to_wp.model.Tank

class TankSheetRepository : TankRepository {
    private lateinit var tankSheet: TankSheet

    init {
        val tankCsvPath = "C:/Users/conno/Documents/Web/Aquarium API/tanks.csv"
        val tankRows = CsvReader.readCsv(tankCsvPath)
        tankSheet = TankSheet(tankRows)
    }

    override fun getTanks(): List<Tank> = tankSheet.tanks
}