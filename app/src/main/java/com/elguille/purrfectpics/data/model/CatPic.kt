package com.elguille.purrfectpics.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatPic(
    val id: String,
    val url: String,
    val breeds: Array<Breed> = emptyArray(),
    val width: Int,
    val height: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatPic

        if (id != other.id) return false
        if (url != other.url) return false
        if (!breeds.contentEquals(other.breeds)) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + breeds.contentHashCode()
        result = 31 * result + width
        result = 31 * result + height
        return result
    }

    companion object {
        val INVALID = CatPic("", "", emptyArray(), 0, 0)
    }
}

@Serializable
data class Breed(
    val id: String,
    val name: String,
    val description: String,
    val origin: String,
    val weight: Weight,
    @SerialName("life_span")
    val lifeSpan: String? = null,
    val temperament: String? = null,
    @SerialName("alt_names")
    val alternateNames: String? = null,
    @SerialName("wikipedia_url")
    val wikipediaUrl: String? = null,
    @SerialName("hypoallergenic")
    val hypoallergenic: Int? = null,
    @SerialName("shedding_level")
    val sheddingLevel: Int? = null,
    val adaptability: Int? = null,
    @SerialName("affection_level")
    val affectionLevel: Int? = null,
    @SerialName("social_needs")
    val socialNeeds: Int? = null,
    @SerialName("child_friendly")
    val childFriendly: Int? = null,
    @SerialName("dog_friendly")
    val dogFriendly: Int? = null,
    @SerialName("energy_level")
    val energyLevel: Int? = null
)

@Serializable
data class Weight(
    val imperial: String,
    val metric: String
)