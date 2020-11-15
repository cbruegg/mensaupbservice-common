@file:OptIn(ExperimentalSerializationApi::class)

package com.cbruegg.mensaupbservice.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoNumber
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
private val proto = ProtoBuf

@Serializable
data class RestaurantsServiceResult(
        @ProtoNumber(1) val restaurants: List<Restaurant> = emptyList()
) {
    fun serialize(): String = proto.encodeToHexString(serializer(), this)

    companion object {
        fun deserialize(str: String): RestaurantsServiceResult = proto.decodeFromHexString(serializer(), str)
    }
}

@Serializable
data class Restaurant(
        @ProtoNumber(1) val id: String,
        @ProtoNumber(2) val name: String,
        @ProtoNumber(3) val location: String,
        @ProtoNumber(4) val isActive: Boolean
)

@Serializable
data class DishesServiceResult(
        @ProtoNumber(1) val dishes: List<Dish> = emptyList()
) {
    fun serialize(): String = proto.encodeToHexString(serializer(), this)

    companion object {
        fun deserialize(str: String): DishesServiceResult = proto.decodeFromHexString(serializer(), str)
    }
}

@Serializable
data class Dish constructor(
        @ProtoNumber(1) @Serializable(with = DateSerializer::class) val date: Date,
        @ProtoNumber(2) val nameDE: String,
        @ProtoNumber(3) val nameEN: String,
        @ProtoNumber(4) val descriptionDE: String?,
        @ProtoNumber(5) val descriptionEN: String?,
        @ProtoNumber(6) val category: String,
        @ProtoNumber(7) val categoryDE: String,
        @ProtoNumber(8) val categoryEN: String,
        @ProtoNumber(9) val subcategoryDE: String,
        @ProtoNumber(10) val subcategoryEN: String,
        @ProtoNumber(11) val studentPrice: Double,
        @ProtoNumber(12) val workerPrice: Double,
        @ProtoNumber(13) val guestPrice: Double,
        @ProtoNumber(14) val allergens: List<String> = emptyList(),
        @ProtoNumber(15) val orderInfo: Int,
        @ProtoNumber(16) val badges: List<Badge> = emptyList(),
        @ProtoNumber(17) val restaurantId: String,
        @ProtoNumber(18) val priceType: PriceType,
        @ProtoNumber(19) val imageUrl: String?,
        @ProtoNumber(20) val thumbnailImageUrl: String?
)

enum class PriceType {
    WEIGHTED, FIXED
}

enum class Badge(private val id: String) {
    VEGAN("vegan"), VEGETARIAN("vegetarian"),
    NONFAT("nonfat"), LACTOSE_FREE("lactose-free");

    companion object {
        /**
         * Each Badge has an id that is used by the API. This method retrieves a Badge by its id.
         * Return value will be null if there's no matching element.
         */
        fun findById(id: String): Badge? = values().firstOrNull { it.id == id }
    }
}