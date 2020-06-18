package me.wietlol.wietbot.services.chatclient.client

import com.amazonaws.regions.Regions.EU_WEST_1
import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw
import java.util.stream.Stream
import kotlin.concurrent.thread
import kotlin.random.Random

object ConnectionTest
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val lambdaClient = AWSLambdaClientBuilder.standard()
			.withRegion(EU_WEST_1)
			.build()
		
		try
		{
			val chatClient = ChatClientClient(lambdaClient)
			chatClient.sendMessage(SendMessageRequest.of(1, "Hello, World!"))
		}
		catch (ex: Throwable)
		{
			ex.printStackTrace()
		}
		
		
//		Stream.generate { Random.nextInt(1, 10000) }
//		Stream.iterate(1) { it + 1 }
//			.parallel()
//			.limit(5)
//			.forEach { number ->
//				val lambdaClient = AWSLambdaClientBuilder.standard()
//					.withRegion(EU_WEST_1)
//					.build()
//
//				val chatClient = ChatClientClient(lambdaClient)
//
//				try
//				{
//					chatClient.sendMessage(SendMessageRequest.of(1, "PatrickStar eval $number"))
//					chatClient.sendMessage(SendMessageRequest.of(1, "Jack, echo $number"))
//					chatClient.sendMessage(SendMessageRequest.of(1, "I am free!"))
//				}
//				catch (ex: Exception)
//				{
//
//				}
//			}
	}
}
