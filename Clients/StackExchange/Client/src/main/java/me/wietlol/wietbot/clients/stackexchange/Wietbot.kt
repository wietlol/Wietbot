package me.wietlol.wietbot.clients.stackexchange

import me.wietlol.wietbot.clients.stackexchange.api.WietbotServer
import me.wietlol.wietbot.clients.stackexchange.botfeatures.BotFeature
import org.koin.core.KoinComponent
import org.koin.core.inject

class Wietbot : KoinComponent
{
	private val server: WietbotServer by inject()
	
	fun start()
	{
		try
		{
			registerBotFeatures()
			
			server.start()
		}
		catch (ex: Exception)
		{
			ex.printStackTrace()
		}
	}
	
	private fun registerBotFeatures()
	{
		getKoin().getAll<BotFeature>()
	}
}
