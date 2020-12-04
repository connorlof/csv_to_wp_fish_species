package com.loftydevelopment.species_csv_to_wp.wordpress

data class WordpressCsvRow(
        val postTitle: String = "",
        val postHtml: String = "",
        val postType: String = "",
        val postExcerpt: String = "",
        val postCategories: String = "",
        val postTags: String = "",
        val postDate: String = "",
) {
    fun asList(): List<String> = listOf(
            postTitle,
            postHtml,
            postType,
            postExcerpt,
            postCategories,
            postTags,
            postDate,
    )
}