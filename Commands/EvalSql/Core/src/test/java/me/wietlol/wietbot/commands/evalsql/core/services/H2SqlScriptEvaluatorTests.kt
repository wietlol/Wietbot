package me.wietlol.wietbot.commands.evalsql.core.services

object H2SqlScriptEvaluatorTests
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val evaluator = H2SqlScriptEvaluator(DatabaseSettings(
			"org.h2.Driver",
			"jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1",
			"sa",
			"sa"
		))
		
		evaluator.evaluate("create table test ( id integer )").also { println(it) }
		evaluator.evaluate("insert into test values (200)").also { println(it) }
		evaluator.evaluate("update test set id = id + 100").also { println(it) }
		evaluator.evaluate("select * from test").also { println(it) }
	}
}
