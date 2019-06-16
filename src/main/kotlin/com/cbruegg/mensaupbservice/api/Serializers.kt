package com.cbruegg.mensaupbservice.api

import kotlinx.serialization.*
import kotlinx.serialization.internal.SerialClassDescImpl
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    private val iso8601Format by threadLocal { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") }

    override val descriptor: SerialDescriptor = SerialClassDescImpl("java.util.Date")

    override fun deserialize(decoder: Decoder): Date = iso8601Format.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, obj: Date) = encoder.encodeString(iso8601Format.format(obj))
}

private class ThreadLocalDelegate<out T>(init: () -> T) : ReadOnlyProperty<Any, T> {

    private val local = object : ThreadLocal<T>() {
        override fun initialValue() = init()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T = local.get()

}

private fun <T> threadLocal(init: () -> T) = ThreadLocalDelegate(init)