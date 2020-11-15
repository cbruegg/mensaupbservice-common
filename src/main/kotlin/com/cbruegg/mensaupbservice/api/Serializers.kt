package com.cbruegg.mensaupbservice.api

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    private val iso8601Format by threadLocal { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.util.Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date = iso8601Format.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeString(iso8601Format.format(value))
}

private class ThreadLocalDelegate<out T>(init: () -> T) : ReadOnlyProperty<Any, T> {

    private val local = object : ThreadLocal<T>() {
        override fun initialValue() = init()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T = local.get()

}

private fun <T> threadLocal(init: () -> T) = ThreadLocalDelegate(init)