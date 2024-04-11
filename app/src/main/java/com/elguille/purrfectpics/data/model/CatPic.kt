package com.elguille.purrfectpics.data.model

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
}

@Serializable
data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val weight: Weight
)

@Serializable
data class Weight(
    val imperial: String,
    val metric: String
)