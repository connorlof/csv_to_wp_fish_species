package com.loftydevelopment.species_csv_to_wp.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.repository.FoodRepository
import com.loftydevelopment.species_csv_to_wp.wordpress.SpeciesPostTemplate
import com.loftydevelopment.species_csv_to_wp.wordpress.WordpressCsvRow

class CsvWriter(
        val foodRepository: FoodRepository,
) {
    fun writeCsv(rows: List<List<String>>) = csvWriter().writeAll(rows, "posts_csv.csv", append = true)

    fun generateSpeciesCsvOutput(speciesList: List<Species>): List<List<String>> {
        val header = header()
        val rows = speciesList.map { generateSpeciesRow(it) }

        val csv: MutableList<List<String>> = mutableListOf()
        csv.add(header)
        csv.addAll(rows)

        return csv
    }

    private fun header(): List<String> = listOf(
            "csv_post_title",
            "csv_post_post",
            "csv_post_type",
            "csv_post_excerpt",
            "csv_post_categories",
            "csv_post_tags",
            "csv_post_date",
    )

    private fun generateSpeciesRow(species: Species): List<String> {

        val postTitle = "${species.commonName} Care (${species.scientificName})"
        val postHtml = SpeciesPostTemplate(species, foodRepository).generateOutputHtml()
        val postType = "post"
        val postExcerpt = ""
        val postCategories = "Freshwater Species, ${species.speciesGroup.name}"
        val postTags = "${species.commonName}, ${species.scientificName}, ${species.aggroOverall}," +
                "${species.alternateNames.joinToString()}, ${species.careLevel.name}, ${species.speciesGroup.name}," +
                "care guide, freshwater"
        val postDate = "now"

        val wordpressCsvRow = WordpressCsvRow(
                postTitle = postTitle,
                postHtml = postHtml,
                postType = postType,
                postExcerpt = postExcerpt,
                postCategories = postCategories,
                postTags = postTags,
                postDate = postDate,
        )

        return wordpressCsvRow.asList()
    }
}