package com.loftydevelopment.species_csv_to_wp.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.repository.FilterRepository
import com.loftydevelopment.species_csv_to_wp.repository.FoodRepository
import com.loftydevelopment.species_csv_to_wp.repository.HeaterRepository
import com.loftydevelopment.species_csv_to_wp.repository.TankRepository
import com.loftydevelopment.species_csv_to_wp.util.EnumStringUtil
import com.loftydevelopment.species_csv_to_wp.wordpress.SpeciesPostTemplate
import com.loftydevelopment.species_csv_to_wp.wordpress.WordpressCsvRow

class CsvWriter(
        private val foodRepository: FoodRepository,
        private val tankRepository: TankRepository,
        private val filterRepository: FilterRepository,
        private val heaterRepository: HeaterRepository,
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
        val postHtml = SpeciesPostTemplate(species, foodRepository, tankRepository, filterRepository, heaterRepository)
                .generateOutputHtml()
        val postType = "post"
        val postExcerpt = ""
        val postCategories = "FRESHWATER SPECIES, ${EnumStringUtil.enumNameToHumanReadable(species.speciesGroup.name).toUpperCase()}"
        val postTags = "${species.commonName.toUpperCase()}, ${species.scientificName.toUpperCase()}, ${species.aggroOverall}," +
                "${species.alternateNames.joinToString().toUpperCase()}, ${species.careLevel.name}," +
                " ${EnumStringUtil.enumNameToHumanReadable(species.speciesGroup.name).toUpperCase()}," +
                "CARE GUIDE, FRESHWATER"
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