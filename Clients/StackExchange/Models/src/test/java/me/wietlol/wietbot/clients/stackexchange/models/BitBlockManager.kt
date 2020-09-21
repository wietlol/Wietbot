package me.wietlol.wietbot.clients.stackexchange.models

import me.wietlol.bitblock.codegenerator.BitModuleProcessor
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.WietbotClientsStackExchange
import org.junit.Test
import java.io.File

class BitBlockManager
{
	@Test
	fun processBitModule()
	{
		// bitblock processBitModule <filepath>
		BitModuleProcessor.processBitModule(File("src/main/resources/me/wietlol/wietbot/clients/stackexchange/models/SeChatMessages.bitmodule"))
	}
	
	@Test
	fun buildBitSchema()
	{
		BitSchemaBuilder.buildSchema(File("src/main/resources/me/wietlol/wietbot/clients/stackexchange/models/SeChatMessages.bitschema"), WietbotClientsStackExchange.modelSerializers)
	}
}
