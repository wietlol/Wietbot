package me.wietlol.wietbot.clients.stackexchange.api

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.bitblock.api.serialization.Schema
import java.io.EOFException
import java.net.ServerSocket
import java.net.Socket

class BitConnect<I : BitSerializable, O : BitSerializable>(
	val schema: Schema,
	val inputClass: Class<I>,
	val process: (I) -> O,
)
{
	fun start(port: Int): Nothing
	{
		val server = ServerSocket(port)
		println("listening to :$port")
		while (true)
			Action(server.accept(), schema, inputClass, process).start()
	}
	
	companion object
	{
		inline operator fun <reified I : BitSerializable, O : BitSerializable> invoke(
			schema: Schema,
			noinline process: (I) -> O,
		): BitConnect<I, O> =
			BitConnect(schema, I::class.java, process)
	}
	
	private class Action<I : BitSerializable, O : BitSerializable>(
		val socket: Socket,
		val schema: Schema,
		val inputClass: Class<I>,
		val process: (I) -> O,
	) : Thread(), AutoCloseable
	{
		override fun run()
		{
			// todo add logger
			use {
				try
				{
					while (true)
					{
						val request: I = schema.deserialize(socket.getInputStream(), inputClass)!!
						val response: O = process(request)
						schema.serialize(socket.getOutputStream(), response)
					}
				}
				catch (_: EOFException)
				{
					// end of conversation reached
				}
			}
		}
		
		override fun close()
		{
			socket.close()
		}
	}
}
