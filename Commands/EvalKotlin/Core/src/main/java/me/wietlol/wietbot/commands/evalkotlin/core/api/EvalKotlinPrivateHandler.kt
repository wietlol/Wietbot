package me.wietlol.wietbot.commands.evalkotlin.core.api

import me.wietlol.wietbot.commands.evalkotlin.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evalkotlin.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evalkotlin.core.interfaces.ScriptEvaluator
import me.wietlol.wietbot.commands.evalkotlin.core.setup.DependencyInjection
import org.koin.core.KoinComponent
import org.koin.core.get

class EvalKotlinPrivateHandler : KoinComponent
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val scriptEvaluator: ScriptEvaluator = get()
	
	init
	{
		scriptEvaluator.evaluate("0")
	}
	
	fun evalKotlinPrivate(request: EvalRequest): EvalResponse? =
		request.code
			.takeIf { it.isNotEmpty() }
			?.let { EvalResponse(scriptEvaluator.evaluate(it)) }
}
