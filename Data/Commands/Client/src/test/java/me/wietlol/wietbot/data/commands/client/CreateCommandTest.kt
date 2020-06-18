package me.wietlol.wietbot.data.commands.client

import com.amazonaws.regions.Regions.EU_WEST_1
import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.models.CreateCommandRequest

object CreateCommandTest
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val lambdaClient = AWSLambdaClientBuilder.standard()
			.withRegion(EU_WEST_1)
			.build()
		val commandService: CommandService = CommandClient(lambdaClient)
		
//		commandService.createCommand(CreateCommandRequest.of(
//			"evalJs",
//			"https://sqs.eu-west-1.amazonaws.com/059598504952/wietbot-commands-evalJavascript"
//		))
		
//		commandService.createCommand(CreateCommandRequest.of(
//			"evalGroovy",
//			"https://sqs.eu-west-1.amazonaws.com/059598504952/wietbot-commands-evalGroovy"
//		))
		
//		commandService.createCommand(CreateCommandRequest.of(
//			"evalKotlin",
//			"https://sqs.eu-west-1.amazonaws.com/059598504952/wietbot-commands-evalKotlin"
//		))
		
//		commandService.createCommand(CreateCommandRequest.of(
//			"evalNode",
//			"https://sqs.eu-west-1.amazonaws.com/059598504952/wietbot-commands-evalNode"
//		))
		
//		commandService.createCommand(CreateCommandRequest.of(
//			"evalCSharp",
//			"https://sqs.eu-west-1.amazonaws.com/059598504952/wietbot-commands-evalCSharp"
//		))
		
		commandService.createCommand(CreateCommandRequest.of(
			"evalSql",
			"https://sqs.eu-west-1.amazonaws.com/059598504952/wietbot-commands-evalSql"
		))
	}
}
