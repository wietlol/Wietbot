package me.wietlol.wietbot.commands.abbreviation.core

import me.wietlol.wietbot.commands.abbreviation.core.models.Definition
import me.wietlol.wietbot.commands.abbreviation.core.services.AbbreviationsHtmlScraper
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.junit.Test

class ScrapingTest
{
	@Test
	fun foo()
	{
//		val html = javaClass.getResourceAsStream("AbbreviationSoc.html")
//			.reader()
//			.use { it.readText() }
//
//		val resolver = AbbreviationsHtmlScraper()
//
//		resolver.findDefinitions("soc")
//
//		val document = Jsoup.parse(html)
//
//		val rows = document.getElementsByClass("table tdata no-margin")
//			.first()
//			.child(1)
//			.childNodes()
//			.filter { it.nodeName() == "tr" }
//
//		val definitions = rows
//			.map { parseNode(it) }
	}
	
	@Test
	fun `assert that mess will result in no matches without failing`()
	{
//		val document = javaClass.getResourceAsStream("AbbreviationMess.html")
//			.reader()
//			.use { it.readText() }
//			.let { Jsoup.parse(it) }
//
//		val x = document.getElementsByClass("no-items")
//
//		if (x.isNotEmpty())
//		{
//			val y = x.first().child(0).child(2)
//				.text()
//
//			println(y)
//		}
//
//		val rows = document.getElementsByClass("no-items")
//			.first()
//			.child(1)
//			.childNodes()
//			.filter { it.nodeName() == "tr" }
//
//		val definitions = rows
//			.map { parseNode(it) }
	}
	
	private fun parseNode(node: Node): Definition
	{
		val children = node.childNodes().filterIsInstance<Element>()
		
		val abbreviation = (children[0].childNode(0) as Element).text()
		
		val c = children[1].childNodes().filterIsInstance<Element>()
		val description = c[0].text()
		
		val tags = c[1]
			.childNodes()
			.filterIsInstance<Element>()
			.filter { it.className() != "more" }
			.map { it.text() }
		
		val rating = children
			.last()
			.childNodes()
			.filterIsInstance<Element>()
			.last()
			.childNodes()
			.filterIsInstance<Element>()
			.count { it.className() == "sf" }
		
		return Definition(
			abbreviation,
			description,
			rating,
			tags
		)
	}
}
