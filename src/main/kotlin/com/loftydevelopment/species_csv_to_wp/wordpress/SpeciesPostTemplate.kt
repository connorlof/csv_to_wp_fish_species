package com.loftydevelopment.species_csv_to_wp.wordpress

import com.loftydevelopment.species_csv_to_wp.calculate.*
import com.loftydevelopment.species_csv_to_wp.model.Food
import com.loftydevelopment.species_csv_to_wp.model.Species
import com.loftydevelopment.species_csv_to_wp.repository.FilterRepository
import com.loftydevelopment.species_csv_to_wp.repository.FoodRepository
import com.loftydevelopment.species_csv_to_wp.repository.HeaterRepository
import com.loftydevelopment.species_csv_to_wp.repository.TankRepository
import com.loftydevelopment.species_csv_to_wp.util.EnumStringUtil
import java.util.jar.Attributes

data class SpeciesPostTemplate(
        val species: Species,
        val foodRepository: FoodRepository,
        val tankRepository: TankRepository,
        val filterRepository: FilterRepository,
        val heaterRepository: HeaterRepository
) {
    fun generateOutputHtml(): String {
        return "${topHeading()}\n" +
                "${headingImage()}\n" +
                "${headingImageCitation()}\n" +
                "${topHeadingStatsTable()}\n\n" +
                generalHeading("Water Parameters") +
                "${waterParamTable()}\n\n" +
                "${waterParamDescription()}\n\n" +
                generalHeading("Compatibility") +
                "${compatibilityTable()}\n\n" +
                generalHeading("Diet") +
                "${dietTable()}\n\n" +
                smallHeading("Food Recommendations") +
                foodRecommendationsDescription() +
                "${foodRecommendationsTable()}\n\n" +
                generalHeading("Tank Setup") +
                "${tankSetupTable()}\n\n" +
                "${tankSetupDescription()}\n\n" +
                smallHeading("Recommended Setup") +
                tankRecommendationsDescription() +
                "${tankRecommendationsTable()}\n\n" +
                generalHeading("Additional Notes") +
                "${additionalNotesDescription()}\n\n"
    }

    private fun generalHeading(title: String): String {
        return "<!-- wp:heading {\"level\":3} -->\n" +
                "<h3>$title</h3>\n" +
                "<!-- /wp:heading -->"
    }

    private fun smallHeading(title: String): String {
        return "<!-- wp:heading {\"level\":5} -->\n" +
                "<h5>$title</h5>\n" +
                "<!-- /wp:heading -->"
    }

    private fun topHeading(): String {
        return "<!-- wp:heading {\"level\":3} -->\n" +
                "<h3>${species.commonName} Care (${species.scientificName})</h3>\n" +
                "<!-- /wp:heading -->"
    }

    private fun headingImage(): String {
        if (species.imageUrl.trim().isEmpty()) return ""

        return "<!-- wp:image {\"sizeSlug\":\"large\",\"linkDestination\":\"none\",\"className\":\"is-style-default\"} -->\n" +
                "<figure class=\"wp-block-image size-large is-style-default\"><img src=\"${species.imageUrl}\" alt=\"\"/></figure>\n" +
                "<!-- /wp:image -->"
    }

    private fun headingImageCitation(): String {
        // No Image
        if (species.imageUrl.trim().isEmpty()) return ""

        // Public Domain
        if (species.imageLicenseType.trim() == "Public Domain") {
            return "<!-- wp:paragraph {\"align\":\"right\"} -->\n" +
                    "<p class=\"has-text-align-right\"><sub><sup>\"${species.commonName}\" is licensed under Public Domain (<a rel=\"noreferrer noopener nofollow\" href=\"${species.imageSourceUrl}\" data-type=\"URL\" data-id=\"${species.imageSourceUrl}\" target=\"_blank\">Original Source</a>)</sup></sub></p>\n" +
                    "<!-- /wp:paragraph -->"
        }

        // Normal citation
        return "<!-- wp:paragraph {\"align\":\"right\"} -->\n" +
                "<p class=\"has-text-align-right\"><sup>\"${species.commonName}\" by ${species.imageAuthor} is licensed under <a href=\"${species.imageLicenseUrl}\" data-type=\"URL\" data-id=\"${species.imageLicenseUrl}\" target=\"_blank\" rel=\"noreferrer noopener nofollow\">${species.imageLicenseType}</a> (<a href=\"${species.imageSourceUrl}\" data-type=\"URL\" data-id=\"${species.imageSourceUrl}\" target=\"_blank\" rel=\"noreferrer noopener nofollow\">Original Source</a>)</sup></p>\n" +
                "<!-- /wp:paragraph -->"
    }

    private fun topHeadingStatsTable(): String {
        return "<!-- wp:table -->\n" +
                "<figure class=\"wp-block-table\"><table><tbody><tr><td>Common Name</td><td>${species.commonName}</td></tr>" +
                "<tr><td>Scientific Name</td><td>${species.scientificName}</td></tr>" +
                "<tr><td>Alternate Names</td><td>${species.alternateNames.joinToString()}</td></tr>" +
                "<tr><td>Species Group</td><td>${EnumStringUtil.enumNameToHumanReadable(species.speciesGroup.name)}</td></tr>" +
                "<tr><td>Care Level</td><td>${EnumStringUtil.enumNameToHumanReadable(species.careLevel.name)}</td></tr>" +
                "<tr><td>Average Adult Size</td><td>${species.avgAdultSize} inches / ${Conversions.inchesToMm(species.avgAdultSize.toDouble()).toInt()} mm</td></tr>" +
                "<tr><td>Maximum Adult Size</td><td>${species.maxAdultSize} inches / ${Conversions.inchesToMm(species.maxAdultSize.toDouble()).toInt()} mm</td></tr>" +
                "<tr><td>Lifespan</td><td>${species.lifespan} years</td></tr></tbody></table></figure>\n" +
                "<!-- /wp:table -->"
    }

    private fun waterParamTable(): String {
        return "<!-- wp:table -->\n" +
                "<figure class=\"wp-block-table\"><table><tbody><tr>" +
                "<td>pH</td><td>Temperature</td><td>Hardness (GH)</td></tr>" +
                "<tr><td>${species.phRange.toStringDecimal(1)}</td>" +
                "<td>${species.temperatureRange} F / ${species.temperatureRange.convertFahToCelsius()} C</td>" +
                "<td>${species.hardnessRange} dGH</td>" +
                "</tr></tbody></table></figure>\n" +
                "<!-- /wp:table -->"
    }

    private fun waterParamDescription(): String {
        return "<!-- /wp:table -->\n" +
                "\n" +
                "<!-- wp:paragraph -->\n" +
                "<p>Note: These are the ranges tolerable for individuals of this species. Towards the middle of this range tends to be best for most species. However, keeping water parameters stable and consistent tend to be more important than hitting a specific number.</p>\n" +
                "<!-- /wp:paragraph -->"
    }

    private fun compatibilityTable(): String {
        val isLivePlantCompatible = if (species.isLivePlantCompatible) "yes" else "no"

        return "<!-- wp:list -->\n" +
                "<ul><li><strong>Swimming Level</strong>: ${EnumStringUtil.enumNameToHumanReadable(species.swimmingLevel.name)}</li>" +
                "<li><strong>Overall Aggressiveness:</strong> ${formatAggression(EnumStringUtil.enumNameToHumanReadable(species.aggroOverall.name))}</li>" +
                "<li><strong>Aggressiveness Own Species:</strong> ${formatAggression(EnumStringUtil.enumNameToHumanReadable(species.aggroOwnSpecies.name))}</li>" +
                "<li><strong>Aggressiveness Other Species:</strong> ${formatAggression(EnumStringUtil.enumNameToHumanReadable(species.aggroOtherSpecies.name))}</li>" +
                "<li><strong>Ideal Number:</strong> ${species.idealQuantity}</li>" +
                "<li><strong>M:F Ratio</strong> - ${species.mfRatio}</li>" +
                "<li><strong>Live Plants:</strong> ${isLivePlantCompatible.capitalize()}</li></ul>\n" +
                "<!-- /wp:list -->"
    }

    private fun formatAggression(aggroString: String): String {
        if (aggroString == "Semiaggressive") return "Semi-aggressive"

        return aggroString
    }

    private fun dietTable(): String {
        return "<!-- wp:paragraph -->\n" +
                "<p><strong>Diet Type:</strong> ${EnumStringUtil.enumNameToHumanReadable(species.diet.name)}<br>" +
                "<strong>Food Preferences: </strong>${species.foodPreferences.joinToString().toLowerCase().capitalize()}</p>\n" +
                "<!-- /wp:paragraph -->"
    }

    private fun foodRecommendationsDescription(): String {
        return "<!-- wp:paragraph -->\n" +
                "<p>Variety tends to be key in promoting health of aquatic life. Along with the food preferences listed above here are the top three food Aquarium API recommends for this species. These are Amazon Affiliate links and purchases help support the site.</p>\n" +
                "<!-- /wp:paragraph -->"
    }

    private fun foodRecommendationsTable(): String {
        val foodImageUrls = getFoodImageUrls()
        val foodNames = getFoodNames()
        val foodProductUrls = getFoodProductUrls()

        return "<!-- wp:nichetablewpwp/niche-table {\"tableStyle\":\"border-bottom-each-row\\u002d\\u002daround-table\",\"nthrColor\":\"#f9f9f9\",\"block_id\":\"a48269d8-7959-4194-b6f8-48d4e0977013\",\"rHoverEffect\":true,\"textAlignIn\":\"text_center\",\"verticalAlign\":\"alignmiddel\"} -->\n" +
                "<div><table id=\"nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013\" class=\"niche_table ms_enable  text_center alignmiddel border-bottom-each-row--around-table tr-hover-effect\" width=\"100%\"><!-- wp:nichetablewpwp/tablerowhead -->\n" +
                "<tr class=\"thead\" style=\"background-color:#f9f9f9!important\"><!-- wp:nichetablewpwp/tableheading -->\n" +
                "<th><strong></strong> </th>\n" +
                "<!-- /wp:nichetablewpwp/tableheading -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tableheading -->\n" +
                "<th><strong></strong> </th>\n" +
                "<!-- /wp:nichetablewpwp/tableheading -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tableheading -->\n" +
                "<th><strong></strong> </th>\n" +
                "<!-- /wp:nichetablewpwp/tableheading --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerowhead -->" +
                "\n" +
                "<!-- wp:nichetablewpwp/tablerow -->\n" +
                "<tr><!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"pImage\",\"imgeExternal\":true,\"externalUrl\":\"" +
                foodImageUrls[0] +
                "\"} -->\n" +
                "<td class=\"tdimg\"><img src=\"" +
                foodImageUrls[0] +
                "\"/></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata -->\n" +
                "<td>" +
                foodNames[0] +
                " </td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"button\",\"buttonType\":\"typetwo\"} -->\n" +
                "<td><a class=\"table-button typetwo\" href=\"" +
                foodProductUrls[0] +
                "\" target=\"_blank\" rel=\"nofollow noopener noreferrer\">Buy on Amazon</a></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerow -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tablerow -->\n" +
                "<tr><!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"pImage\",\"imgeExternal\":true,\"externalUrl\":\"" +
                foodImageUrls[1] +
                "\"} -->\n" +
                "<td class=\"tdimg\"><img src=\"" +
                foodImageUrls[1] +
                "\"/></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata -->\n" +
                "<td>" +
                foodNames[1] +
                "\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"button\",\"buttonType\":\"typetwo\"} -->\n" +
                "<td><a class=\"table-button typetwo\" href=\"" +
                foodProductUrls[1] +
                "\" target=\"_blank\" rel=\"nofollow noopener noreferrer\">Buy on Amazon</a></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerow -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tablerow -->\n" +
                "<tr><!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"pImage\",\"imgeExternal\":true,\"externalUrl\":\"" +
                foodImageUrls[2] +
                "\"} -->\n" +
                "<td class=\"tdimg\"><img src=\"" +
                foodImageUrls[2] +
                "\"/></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata -->\n" +
                "<td>" +
                foodNames[2] +
                " </td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"button\",\"buttonType\":\"typetwo\"} -->\n" +
                "<td><a class=\"table-button typetwo\" href=\"" +
                foodProductUrls[2] +
                "\" target=\"_blank\" rel=\"nofollow noopener noreferrer\">Buy on Amazon</a></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerow --></table><style> @media only screen and (max-width: 768px){ #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(1):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(2):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(3):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(4):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(5):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(6):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(7):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(8):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } } @media only screen and (min-width: 768px){ .niche_table, .niche_table th, .niche_table td, .niche_table tr{ border-color: #f1f1f1 !important; } .niche_table tr:nth-child(odd){background-color:#f9f9f9 !important;} } </style></div>\n" +
                "<!-- /wp:nichetablewpwp/niche-table -->"
    }

    private fun getFoodImageUrls(): List<String> {
        val foodList = FoodSelector.selectFoodForSpecies(species, foodRepository)
        val urlList: MutableList<String> = foodList.map { it.imageUrl }.toMutableList()

        while (urlList.size < 3) {
            urlList.add("")
        }

        return urlList
    }

    private fun getFoodNames(): List<String> {
        val foodList = FoodSelector.selectFoodForSpecies(species, foodRepository)
        val nameList: MutableList<String> = foodList.map { it.name }.toMutableList()

        while (nameList.size < 3) {
            nameList.add("")
        }

        return nameList
    }

    private fun getFoodProductUrls(): List<String> {
        val foodList = FoodSelector.selectFoodForSpecies(species, foodRepository)
        val urlList: MutableList<String> = foodList.map { it.productUrl }.toMutableList()

        while (urlList.size < 3) {
            urlList.add("")
        }

        return urlList
    }

    private fun tankSetupTable(): String {
        return "<!-- wp:table -->\n" +
                "<figure class=\"wp-block-table\"><table><tbody>" +
                "<tr><td>Substrate</td><td>${EnumStringUtil.enumNameToHumanReadable(species.substrate.name)}</td></tr>" +
                "<tr><td>Light</td><td>${EnumStringUtil.enumNameToHumanReadable(species.lightLevel.name)}</td></tr>" +
                "<tr><td>Water Current</td><td>${EnumStringUtil.enumNameToHumanReadable(species.waterCurrent.name)}</td></tr>" +
                "<tr><td>Decorations</td><td>${EnumStringUtil.enumNameToHumanReadable(species.decorationLevel.name)}</td></tr>" +
                "<tr><td>Minimum Tank Size</td><td>${species.minTankSize} gallons / " +
                "${Conversions.gallonsToLiters(species.minTankSize.toDouble()).toInt()} liters</td></tr>" +
                "</tbody></table></figure>\n" +
                "<!-- /wp:table -->"
    }

    private fun tankSetupDescription(): String {
        return "<!-- wp:paragraph -->\n" +
                "<p>Note: The minimum tank size refers to the tank volume for the smallest ideal adult stocking quantity seen in the compatibility section. For a species that does not have a minimum number the tank size refers to the minimum for a single adult species.</p>\n" +
                "<!-- /wp:paragraph -->"
    }

    private fun tankRecommendationsDescription(): String {
        return "<!-- wp:paragraph -->" +
                "<p>Below are recommendations for setting up an aquarium of the minimum tank size for this species. These are Amazon Affiliate links and purchases help support the site.</p>" +
                "<!-- /wp:paragraph -->"
    }

    private fun tankRecommendationsTable(): String {
        // Tank recommendation
        val tankRecommendation = TankSelector.selectTankForSpecies(species, tankRepository)
        val tankImageUrl = tankRecommendation?.imageUrl ?: ""
        val tankName = tankRecommendation?.name ?: "No tank recommendations for this species"
        val tankProductUrl = tankRecommendation?.productUrl ?: ""

        // Filter recommendation
        val filterRecommendation = FilterSelector.selectFilterForSpecies(species, filterRepository)
        val filterImageUrl = filterRecommendation?.imageUrl ?: ""
        val filterName = filterRecommendation?.name ?: "No filter recommendations for this species"
        val filterProductUrl = filterRecommendation?.productUrl ?: ""

        // Heater recommendation
        val heaterRecommendation = HeaterSelector.selectHeaterForSpecies(species, heaterRepository)
        val heaterImageUrl = heaterRecommendation?.imageUrl ?: ""
        val heaterName = heaterRecommendation?.name ?: "No heater recommendations for this species"
        val heaterProductUrl = heaterRecommendation?.productUrl ?: ""

        return "<!-- wp:nichetablewpwp/niche-table {\"tableStyle\":\"border-bottom-each-row\\u002d\\u002daround-table\",\"nthrColor\":\"#f9f9f9\",\"block_id\":\"a48269d8-7959-4194-b6f8-48d4e0977013\",\"rHoverEffect\":true,\"textAlignIn\":\"text_center\",\"verticalAlign\":\"alignmiddel\"} -->\n" +
                "<div><table id=\"nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013\" class=\"niche_table ms_enable  text_center alignmiddel border-bottom-each-row--around-table tr-hover-effect\" width=\"100%\"><!-- wp:nichetablewpwp/tablerowhead -->\n" +
                "<tr class=\"thead\" style=\"background-color:#f9f9f9!important\"><!-- wp:nichetablewpwp/tableheading -->\n" +
                "<th><strong></strong> </th>\n" +
                "<!-- /wp:nichetablewpwp/tableheading -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tableheading -->\n" +
                "<th><strong></strong> </th>\n" +
                "<!-- /wp:nichetablewpwp/tableheading -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tableheading -->\n" +
                "<th><strong></strong> </th>\n" +
                "<!-- /wp:nichetablewpwp/tableheading --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerowhead -->" +
                "\n" +
                "<!-- wp:nichetablewpwp/tablerow -->\n" +
                "<tr><!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"pImage\",\"imgeExternal\":true,\"externalUrl\":\"" +
                tankImageUrl +
                "\"} -->\n" +
                "<td class=\"tdimg\"><img src=\"" +
                tankImageUrl +
                "\"/></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata -->\n" +
                "<td>" +
                tankName +
                " </td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"button\",\"buttonType\":\"typetwo\"} -->\n" +
                "<td><a class=\"table-button typetwo\" href=\"" +
                tankProductUrl +
                "\" target=\"_blank\" rel=\"nofollow noopener noreferrer\">Buy on Amazon</a></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerow -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tablerow -->\n" +
                "<tr><!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"pImage\",\"imgeExternal\":true,\"externalUrl\":\"" +
                filterImageUrl +
                "\"} -->\n" +
                "<td class=\"tdimg\"><img src=\"" +
                filterImageUrl +
                "\"/></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata -->\n" +
                "<td>" +
                filterName +
                "\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"button\",\"buttonType\":\"typetwo\"} -->\n" +
                "<td><a class=\"table-button typetwo\" href=\"" +
                filterProductUrl +
                "\" target=\"_blank\" rel=\"nofollow noopener noreferrer\">Buy on Amazon</a></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerow -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tablerow -->\n" +
                "<tr><!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"pImage\",\"imgeExternal\":true,\"externalUrl\":\"" +
                heaterImageUrl +
                "\"} -->\n" +
                "<td class=\"tdimg\"><img src=\"" +
                heaterImageUrl +
                "\"/></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata -->\n" +
                "<td>" +
                heaterName +
                " </td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata -->\n" +
                "\n" +
                "<!-- wp:nichetablewpwp/tabledata {\"tableDataIcon\":\"button\",\"buttonType\":\"typetwo\"} -->\n" +
                "<td><a class=\"table-button typetwo\" href=\"" +
                heaterProductUrl +
                "\" target=\"_blank\" rel=\"nofollow noopener noreferrer\">Buy on Amazon</a></td>\n" +
                "<!-- /wp:nichetablewpwp/tabledata --></tr>\n" +
                "<!-- /wp:nichetablewpwp/tablerow --></table><style> @media only screen and (max-width: 768px){ #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(1):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(2):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(3):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(4):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(5):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(6):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(7):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(8):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } #nichetablewpwp-a48269d8-7959-4194-b6f8-48d4e0977013 td:nth-of-type(9):before{ content: ''; } } @media only screen and (min-width: 768px){ .niche_table, .niche_table th, .niche_table td, .niche_table tr{ border-color: #f1f1f1 !important; } .niche_table tr:nth-child(odd){background-color:#f9f9f9 !important;} } </style></div>\n" +
                "<!-- /wp:nichetablewpwp/niche-table -->"
    }

    private fun additionalNotesDescription(): String {
        var attributeString = ""
        val attributes = species.speciesAttributes
        val endString = "Thanks for checking out this care guide. Do you have any questions about this species? " +
                "Or did you find an error on this page? Just leave a comment below."

        for (attribute in attributes) {
            attributeString = "$attributeString<p>${getAttributeString(attribute)}</p>\n\n"
        }

        if (attributes.isEmpty() || attributes[0] == "none") {
            attributeString = "<p>No additional notes about this species.\n\n$endString</p>\n"
        } else {
            attributeString = "$attributeString<p>\n\n$endString</p>\n"
        }

        return "<!-- wp:paragraph -->\n" +
                attributeString +
                "<!-- /wp:paragraph -->"
    }

    private fun getAttributeString(attribute: String): String {
        var outputString = ""

        when (attribute) {
            "jumper" -> outputString = "This species is known to jump or escape from aquariums. It is recommended to have a tight fitting lid"
            "digger" -> outputString = "This species is known to dig in the substrate. Plants and decorations may be moved."
            "fin nipper" -> outputString = "This species is know to be a fin nipper. It is not recommended to house them with fish that have long flowing fins."
        }

        return outputString
    }
}
