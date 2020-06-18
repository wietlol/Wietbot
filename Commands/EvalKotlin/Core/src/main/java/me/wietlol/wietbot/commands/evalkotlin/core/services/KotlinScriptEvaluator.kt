package me.wietlol.wietbot.commands.evalkotlin.core.services

import me.wietlol.wietbot.commands.evalkotlin.core.interfaces.ScriptEvaluator
import java.io.StringWriter
import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class KotlinScriptEvaluator : ScriptEvaluator
{
	init {
		System.setProperty("idea.io.use.fallback", "true")
		System.setProperty("idea.use.native.fs.for.win", "false")
	}
	
	override fun evaluate(script: String): String
	{
		val engine: ScriptEngine = ScriptEngineManager().getEngineByExtension("kts")
		
		val sw = StringWriter()
		engine.context.apply {
			writer = sw
			errorWriter = sw
		}
		
		engine.getBindings(ScriptContext.ENGINE_SCOPE).apply {
			put("stdOut", sw)
		}
		
		val header = "fun println(obj: Any?) { (bindings[\"stdOut\"] as java.io.Writer).write(obj.toString() + \"\\n\") }\n"
		
		val result: Any?
		try
		{
			result = engine.eval(header + script)
		}
		catch (ex: Exception)
		{
			return "${ex.javaClass.name}(${ex.message})"
		}
		
		val stdOut = sw.toString()
		
		return if (stdOut.isEmpty())
			result.toString()
		else
			"Result: $result\r\nOutput:\r\n$stdOut"
	}
}
