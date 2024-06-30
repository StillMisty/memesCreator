package top.stillmisty.memescreator.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MemeInfo(
    val key: String = "",
    val keywords: List<String> = listOf(),
    val pattern: List<String> = listOf(),
    val params: Params = Params(),
    val patterns: List<String> = listOf() // Add this line if "patterns" should be included
) {
    data class Params(
        val min_images: Int = 0,
        val max_images: Int = 0,
        val min_texts: Int = 0,
        val max_texts: Int = 0,
        val default_texts: List<String> = listOf(),
        val args: List<String> = listOf()
    )
}