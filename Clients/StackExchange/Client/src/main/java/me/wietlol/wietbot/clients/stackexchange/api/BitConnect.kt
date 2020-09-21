package me.wietlol.wietbot.clients.stackexchange.api

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.LoggerFactory
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.ScopedSourceLogger
import me.wietlol.loggo.common.logError
import me.wietlol.loggo.common.logTrace
import java.io.EOFException
import java.net.ServerSocket
import java.net.Socket

class BitConnect<I : BitSerializable, O : BitSerializable>(
	val schema: Schema,
	val inputClass: Class<I>,
	val loggerFactory: LoggerFactory<CommonLog>,
	val process: (I) -> O,
)
{
	fun start(port: Int): Nothing
	{
		val server = ServerSocket(port)
		while (true)
			Action(server.accept(), schema, inputClass, process, loggerFactory).start()
	}
	
	companion object
	{
		inline operator fun <reified I : BitSerializable, O : BitSerializable> invoke(
			schema: Schema,
			loggerFactory: LoggerFactory<CommonLog>,
			noinline process: (I) -> O,
		): BitConnect<I, O> =
			BitConnect(schema, I::class.java, loggerFactory, process)
	}
	
	private class Action<I : BitSerializable, O : BitSerializable>(
		val socket: Socket,
		val schema: Schema,
		val inputClass: Class<I>,
		val process: (I) -> O,
		val loggerFactory: LoggerFactory<CommonLog>,
	) : Thread(), AutoCloseable
	{
		private val requestEventId = EventId(1234820194, "bitconnect-action-request")
		private val responseEventId = EventId(1973860474, "bitconnect-action-response")
		private val errorEventId = EventId(1861370874, "bitconnect-action-error")
		
		override fun run()
		{
			use { // this
				loggerFactory
					.createLogger()
					.let { ScopedSourceLogger(it) { it + "BitConnect" } }
					.use { logger ->
						try
						{
							while (true)
							{
								val request: I = schema.deserialize(socket.getInputStream(), inputClass)!!
								logger.logTrace(requestEventId, mapOf("request" to request))
								
								val response: O = process(request)
								logger.logTrace(responseEventId, mapOf("response" to response))
								
								schema.serialize(socket.getOutputStream(), response)
							}
						}
						catch (_: EOFException)
						{
							// end of conversation reached
						}
						catch (ex: Throwable)
						{
							logger.logError(errorEventId, emptyMap<Any, Any>(), ex)
						}
					}
			}
		}
		
		override fun close()
		{
			socket.close()
		}
	}
}
