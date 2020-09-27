package me.wietlol.wietbot.data.commands.client

import com.amazonaws.regions.Regions.EU_WEST_1
import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequest
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequestImpl

object ListCommandsTest
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val lambdaClient = AWSLambdaClientBuilder.standard()
			.withRegion(EU_WEST_1)
			.build()
		val commandService: CommandService = CommandClient(lambdaClient)
		
		val commands = commandService.listCommands(ListCommandsRequestImpl()).commands
		
		commands.forEach {
			println(it.keyword)
		}
	}
}
