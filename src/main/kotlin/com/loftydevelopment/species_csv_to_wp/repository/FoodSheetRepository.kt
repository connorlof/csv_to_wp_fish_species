package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.csv.CsvReader
import com.loftydevelopment.species_csv_to_wp.csv.FoodSheet
import com.loftydevelopment.species_csv_to_wp.model.Food

class FoodSheetRepository : FoodRepository {
    private lateinit var foodSheet: FoodSheet

    init {
        val foodCsvPath = "C:/Users/conno/Documents/Web/Aquarium API/food.csv"
        val foodRows = CsvReader.readCsv(foodCsvPath)
        foodSheet = FoodSheet(foodRows)
    }

    override fun getFood(): List<Food> = foodSheet.foodList
}