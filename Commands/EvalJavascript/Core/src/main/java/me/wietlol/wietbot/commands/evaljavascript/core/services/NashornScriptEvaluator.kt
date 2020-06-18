package me.wietlol.wietbot.commands.evaljavascript.core.services

import me.wietlol.wietbot.commands.evaljavascript.core.interfaces.ScriptEvaluator
import java.io.StringWriter
import javax.script.ScriptEngineManager

class NashornScriptEvaluator : ScriptEvaluator
{
	override fun evaluate(script: String): String
	{
		/*
		String[] arguments = new String[] {"-strict", "--no-java", "--no-syntax-extensions", "-dump-on-error"};
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine(arguments);
        StringWriter sw = new StringWriter();
        ScriptContext context = engine.getContext();
        context.setWriter(sw);
        context.setErrorWriter(sw);
        engine.eval("print('hello world')");
        System.out.println("redirected output: " + sw);
		*/
		try
		{
			val engine = ScriptEngineManager().getEngineByExtension("js")
			
			val console = StringWriter()
			
			engine.context.apply {
				writer = console
				errorWriter = console
			}
			
			val result = runCatching { engine.eval(script) }
				.recover { "${it.javaClass.name}(${it.message})" }
				.getOrNull()
			
			val stdOut = console.buffer.toString()
			
			return if (stdOut.isEmpty())
				result.toString()
			else
				"Result: $result\r\nOutput:\r\n$stdOut"
		}
		catch (ex: Exception)
		{
			return ex.toString()
		}
	}
}
