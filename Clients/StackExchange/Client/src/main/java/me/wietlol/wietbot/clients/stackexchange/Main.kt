package me.wietlol.wietbot.clients.stackexchange

import me.wietlol.wietbot.clients.stackexchange.setup.DependencyInjection

object Main
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		try
		{
			DependencyInjection.bindServiceCollection()
			
			Wietbot().start()
		}
		catch (ex: Exception)
		{
			ex.printStackTrace()
		}
	}
}
