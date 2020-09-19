package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

import khttp.structures.cookie.Cookie
import khttp.structures.cookie.CookieJar
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.HttpCookie
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect
import java.net.http.HttpClient.Version
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant

const val mainSiteUrl = "https://stackoverflow.com"
const val chatSiteUrl = "https://chat.stackoverflow.com"

fun getMainFKey(cookieJar: MutableMap<String, String>): String =
	khttp.get("$mainSiteUrl/users/login", cookies = cookieJar)
		.apply { cookieJar.putAll(cookies) }
		.run { text.extractFKey() }

//fun login(clientFkey: String, emailAddress: String, password: String, cookieJar: MutableMap<String, String>): Response =
//	khttp.post(
//		"$mainSiteUrl/users/login",
//		data = mapOf(
//			"fkey" to clientFkey,
//			"email" to emailAddress,
//			"password" to password
//		),
//		headers = mapOf(
//			"Content-Type" to "application/x-www-form-urlencoded",
//			"Content-Length" to (clientFkey.length + emailAddress.length + password.length + 2 + 17 + 3 + 2).toString()
//		)
//	)
//		.apply { cookieJar.putAll(cookies) }
//		.apply {
//			if (text.contains("The email or password is incorrect."))
//				throw UnsuccessfulAuthenticationException("The email or password is incorrect.")
//		}
//		.apply {
//			if (text.contains("<title>Human verification - Stack Overflow</title>"))
//				throw LackOfHumanityException("Human verification is required.")
//		}
//		.apply {
//			if (cookies.containsKey("uauth").not())
//				throw UnexpectedSituationException("unable to login for unknown reasons")
//		}


fun login(clientFkey: String, emailAddress: String, password: String, cookieJar: MutableMap<String, String>)
{
	val cookieHandler = CookieManager(null, CookiePolicy.ACCEPT_ALL)
	
	val client: HttpClient = HttpClient.newBuilder()
		.version(Version.HTTP_1_1)
		.followRedirects(Redirect.NORMAL)
		.connectTimeout(Duration.ofSeconds(20))
		.cookieHandler(cookieHandler)
		.build()
	
	val request = HttpRequest.newBuilder()
		.POST(BodyPublishers.ofString(
			mapOf(
				"fkey" to clientFkey,
				"email" to emailAddress,
				"password" to password
			).toList()
				.joinToString("&") { "${URLEncoder.encode(it.first, StandardCharsets.UTF_8)}=${URLEncoder.encode(it.second, StandardCharsets.UTF_8)}" }
		))
		.uri(URI.create("https://stackoverflow.com/users/login"))
		.header("Content-Type", "application/x-www-form-urlencoded")
		.build()
	
	val response: HttpResponse<String> = client.send(request, BodyHandlers.ofString())
	
	val newCookieJar = cookieHandler.cookieStore.get(URI("https://stackoverflow.com/"))
		.toList()
		.map {
			Cookie(
				it.name,
				it.value,
				mapOf(
					"domain" to it.domain,
					"expires" to Instant.EPOCH.plusMillis(it.maxAge).plusMillis(it.whenCreated).toString(), // todo check pattern
					"path" to it.path,
					"HttpOnly" to null
				)
			)
		}
		.toTypedArray()
		.let { CookieJar(*it) }
	
	cookieJar.putAll(newCookieJar)
	
	response.body()
		.apply {
			if (contains("The email or password is incorrect."))
				throw UnsuccessfulAuthenticationException("The email or password is incorrect.")
		}
		.apply {
			if (contains("<title>Human verification - Stack Overflow</title>"))
				throw LackOfHumanityException("Human verification is required.")
		}
	if (cookieJar.containsKey("uauth").not())
		throw UnexpectedSituationException("unable to login for unknown reasons")
}

private val HttpCookie.whenCreated: Long
	get()
	{
		val field = this.javaClass.declaredFields
			.single { it.name == "whenCreated" }
		
		field.isAccessible = true
		
		return field.get(this) as Long
	}

fun getAccountFKey(cookieJar: Map<String, String>): String =
	khttp.get(chatSiteUrl, cookies = cookieJar)
		.run { text.extractFKey() }

fun String.extractFKey(): String =
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
