package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.model.Food

interface FoodRepository {
    abstract fun getFood(): List<Food>
}