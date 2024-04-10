package com.elguille.purrfectpics.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatPicItem(
    @SerialName("_id")
    val id: String,
    val tags: Array<String>,
    val owner: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatPicItem

        if (id != other.id) return false
        if (!tags.contentEquals(other.tags)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + tags.contentHashCode()
        return result
    }

    companion object {
        val INVALID = CatPicItem("", emptyArray())
    }
}
