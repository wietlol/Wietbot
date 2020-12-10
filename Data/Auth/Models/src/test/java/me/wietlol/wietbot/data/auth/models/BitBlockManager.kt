package me.wietlol.wietbot.data.auth.models

import me.wietlol.bitblock.codegenerator.BitModuleProcessor
import me.wietlol.bitblock.core.BitSchemaBuilder
import org.junit.Test
import java.io.File

class BitBlockManager
{
	@Test
	fun processBitModule()
	{
		// bitblock processBitModule <filepath>
		BitModuleProcessor.processBitModule(
			File("src/main/resources/me/wietlol/wietbot/data/auth/models/Api.bitmodule")
		)
	}
	
	@Test
	fun buildBitSchema()
	{
		BitSchemaBuilder.buildSchema(
			File("src/main/resources/me/wietlol/wietbot/data/auth/models/Api.bitschema"),
			WietbotDataAuth.modelSerializers
		)
	}
}
