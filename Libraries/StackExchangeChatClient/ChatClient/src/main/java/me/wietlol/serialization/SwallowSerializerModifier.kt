package me.wietlol.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier

class SwallowSerializerModifier : BeanSerializerModifier()
{
	override fun changeProperties(config: SerializationConfig?, beanDesc: BeanDescription?, beanProperties: List<BeanPropertyWriter>): List<BeanPropertyWriter> =
		beanProperties
			.asSequence()
			.map {
				object : BeanPropertyWriter(it)
				{
					override fun serializeAsField(bean: Any, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider)
					{
						runCatching {
							super.serializeAsField(bean, jsonGenerator, serializerProvider)
						}
					}
				}
			}.toList()
}

