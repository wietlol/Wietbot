package me.wietlol.wietbot.clients.stackexchange.client

//import me.wietlol.bitblock.api.serialization.deserialize
//import me.wietlol.bitblock.core.BitBlock
//import me.wietlol.bitblock.core.registry.LocalModelRegistry
//import me.wietlol.bitblock.core.serialization.ImmutableSchema
//import me.wietlol.wietbot.data.auth.models.WietbotDataAuth
//import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponse
//
//object SerializationTest
//{
//	@JvmStatic
//	fun main(args: Array<String>)
//	{
//		val response = IsUserAuthorizedResponse.of(true)
//
//		val schema = LocalModelRegistry()
//			.apply(BitBlock::initialize)
//			.apply(WietbotDataAuth::initialize)
//			.let { ImmutableSchema(WietbotDataAuth::class.java.getResourceAsStream("/me/wietlol/wietbot/data/auth/models/Api.bitschema"), it) }
//
//		val data = schema.serialize(response)
//		val newResponse = schema.deserialize<IsUserAuthorizedResponse>(data)
//
//		println(newResponse)
//	}
//}
