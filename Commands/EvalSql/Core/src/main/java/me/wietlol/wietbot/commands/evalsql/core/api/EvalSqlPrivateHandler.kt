package me.wietlol.wietbot.commands.evalsql.core.api

import me.wietlol.wietbot.commands.evalsql.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evalsql.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evalsql.core.interfaces.ScriptEvaluator
import me.wietlol.wietbot.commands.evalsql.core.setup.DependencyInjection
import org.koin.core.KoinComponent
import org.koin.core.get

class EvalSqlPrivateHandler : KoinComponent
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val scriptEvaluator: ScriptEvaluator = get()
	
	fun evalSqlPrivate(request: EvalRequest): EvalResponse? =
		request.code
			.takeIf { it.isNotEmpty() }
			?.let { EvalResponse(scriptEvaluator.evaluate(it)) }
}
