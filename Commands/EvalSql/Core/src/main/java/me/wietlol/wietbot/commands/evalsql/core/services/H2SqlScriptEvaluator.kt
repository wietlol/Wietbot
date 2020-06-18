package me.wietlol.wietbot.commands.evalsql.core.services

import me.wietlol.wietbot.commands.evalsql.core.interfaces.ScriptEvaluator
import java.sql.Connection
import java.sql.DriverManager


class H2SqlScriptEvaluator(
	databaseSettings: DatabaseSettings
) : ScriptEvaluator
{
	init
	{
		Class.forName(databaseSettings.driver)
	}
	
	private val connection: Connection = DriverManager.getConnection(databaseSettings.url, databaseSettings.user, databaseSettings.password)
	
	override fun evaluate(script: String): String =
		connection.prepareStatement(script)
			.use { statement ->
				try
				{
					statement.executeQuery().use { resultSet ->
						generateSequence { resultSet.next() }
							.takeWhile { it }
							.map {
								val columnCount = resultSet.metaData.columnCount
								(1..columnCount)
									.asSequence()
									.map {
										@Suppress("USELESS_CAST") // cast not useless, required to ensure result is nullable and therefor use the Any?.toString extension
										(resultSet.getObject(it) as Any?).toString()
									}
									.joinToString(", ")
							}
							.let { data ->
								(1..resultSet.metaData.columnCount)
									.joinToString(", ") { resultSet.metaData.getColumnName(it) }
									.plus("\n")
									.let { sequenceOf(it) }
									.plus(data)
							}
							.joinToString("\n")
					}
				}
				catch (ex: Exception)
				{
					if (ex.javaClass.name == "org.h2.jdbc.JdbcSQLNonTransientException")
					{
						try
						{
							val resultCount = statement.executeUpdate()
							
							"Success, $resultCount records affected."
						}
						catch (ex2: Exception)
						{
							"${ex2.javaClass.name}(${ex2.message})"
						}
					}
					else
						"${ex.javaClass.name}(${ex.message})"
				}
			}
			.let {
				it.lineSequence()
					.map { "    $it" }
					.joinToString("\n")
			}
}
