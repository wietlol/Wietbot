package me.wietlol.wietbot.commands.evaljavascript.core.api

import me.wietlol.wietbot.commands.evaljavascript.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evaljavascript.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evaljavascript.core.interfaces.ScriptEvaluator
import me.wietlol.wietbot.commands.evaljavascript.core.setup.DependencyInjection
import org.koin.core.KoinComponent
import org.koin.core.get

class EvalJavascriptPrivateHandler : KoinComponent
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
	
	fun evalJavascriptPrivate(request: EvalRequest): EvalResponse? =
		request.code
			.takeIf { it.isNotEmpty() }
			?.let { EvalResponse(scriptEvaluator.evaluate(it)) }
}
