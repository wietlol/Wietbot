package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.LackOfHumanityException
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.UnexpectedSituationException
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.UnsuccessfulAuthenticationException

class HttpSeChatClientTest
{
	companion object
	{
		private const val mainSiteUrl = "https://stackoverflow.com"
		
		@JvmStatic
		fun main(args: Array<String>)
		{
			val email = args[0]
			val password = args[1]
			
			val cookies = mutableMapOf<String, String>()
			val fkey = getMainFKey(cookies)
			
			println(fkey)
			println(cookies.size)
			println(cookies.keys.joinToString())
			
			cookies["fkey"] = fkey // not set by getMainFKey, but in SO still passed
			
			val response = khttp.post(
				"$mainSiteUrl/users/login",
				data = mapOf(
					"fkey" to fkey,
					"email" to email,
					"password" to password,
					"ssrc" to "head",
					"oauth_version" to "",
					"oauth_server" to ""
				),
				params = mapOf(
					"ssrc" to "head",
					"returnurl" to mainSiteUrl
				),
				headers = mapOf(
					"Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
					"Accept-Encoding" to "gzip, deflate, br",
					"accept-language" to "nl-NL,nl;q=0.9,en-US;q=0.8,en;q=0.7",
					"cache-control" to "max-age=0",
					"Content-Type" to "application/x-www-form-urlencoded",
					"Cookie" to "prov=e6b9995f-7f6b-23e2-a00e-4746ea8d448d; _ga=GA1.2.754630069.1590896443; _gid=GA1.2.2069097735.1590896443; _gat=1; __qca=P0-480900304-1590896443188; fkey=147088789b90d5746a8e22163ee5bb1760403d9d40664f9a7ea2d0904924ce8e",
					"origin" to mainSiteUrl,
					"referer" to "$mainSiteUrl/users/login",
					"sec-fetch-dest" to "document",
					"sec-fetch-mode" to "navigate",
					"sec-fetch-site" to "same-origin",
					"sec-fetch-user" to "?1",
					"upgrade-insecure-requests" to "1",
					"User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36"
				),
				cookies = cookies,
				allowRedirects = true
			)
			println(response.statusCode)
			println(response.cookies.size)
			println(response.cookies.keys.joinToString())
			response
				.apply {
					if (text.contains("The email or password is incorrect."))
						throw UnsuccessfulAuthenticationException("The email or password is incorrect.")
				}
				.apply {
					if (text.contains("<title>Human verification - Stack Overflow</title>"))
						throw LackOfHumanityException("Human verification is required.")
				}
				.apply {
					if (cookies.containsKey("uauth").not())
						throw UnexpectedSituationException("unable to login for unknown reasons")
				}
		}
		
		private fun getMainFKey(cookieJar: MutableMap<String, String>): String =
			khttp.get("${me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.mainSiteUrl}/users/login", cookies = cookieJar)
				.apply { cookieJar.putAll(cookies) }
				.run { text.extractFKey() }
		
		private fun String.extractFKey(): String =
			sequenceOf(
				"<input type=\"hidden\" name=\"fkey\" value=\"",
				"<input id=\"fkey\" name=\"fkey\" type=\"hidden\" value=\""
			)
				.map {
					val head = indexOf(it)
					if (head > 0)
					{
						val start = head + it.length
						val end = indexOf("\"", start)
						substring(start, end)
					}
					else
						null
				}
				.filterNotNull()
				.firstOrNull()
				?: throw UnexpectedSituationException("there is no fkey for unknown reasons")
	}
}
