package me.wietlol.wietbot.commands.abbreviation.core.services

import me.wietlol.wietbot.commands.abbreviation.core.interfaces.AbbreviationResolver
import me.wietlol.wietbot.commands.abbreviation.core.models.AbbreviationSearchResult
import me.wietlol.wietbot.commands.abbreviation.core.models.Definition
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

class AbbreviationsHtmlScraper : AbbreviationResolver
{
	override fun findDefinitions(abbreviation: String): AbbreviationSearchResult
	{
		val html = khttp.get("https://www.abbreviations.com/serp.php", params = mapOf(
			"st" to abbreviation,
			"p" to "99999"
		)).text
		
		val document = Jsoup.parse(html)
		
		val suggestions = getSuggestionsFromDocument(document)
		
		return if (suggestions.isNotEmpty())
			AbbreviationSearchResult(
				emptyList(),
				suggestions
			)
		else
			AbbreviationSearchResult(
				getDefinitionsFromDocument(document),
				emptyList()
			)
	}
	
	private fun getSuggestionsFromDocument(document: Document): List<String> =
		document.getElementsByClass("no-items")
			.takeIf { it.isNotEmpty() }
			?.first()
			?.child(0)
			?.child(2)
			?.childNodes()
			?.filterIsInstance<Element>()
			?.map { it.text() }
			?: emptyList()
	
	private fun getDefinitionsFromDocument(document: Document): List<Definition> =
		document.getElementsByClass("table tdata no-margin")
			.first()
			.child(1)
			.childNodes()
			.filter { it.nodeName() == "tr" }
			.map { parseNode(it) }
	
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
