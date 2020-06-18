package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import khttp.structures.cookie.Cookie
import khttp.structures.cookie.CookieJar
import me.wietlol.serialization.JacksonSerializerAdapter
//import org.apache.http.Consts
//import org.apache.http.client.config.CookieSpecs
//import org.apache.http.client.config.RequestConfig
//import org.apache.http.client.entity.UrlEncodedFormEntity
//import org.apache.http.client.methods.CloseableHttpResponse
//import org.apache.http.client.methods.HttpGet
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.client.methods.HttpUriRequest
//import org.apache.http.impl.client.CloseableHttpClient
//import org.apache.http.impl.client.HttpClients
//import org.apache.http.message.BasicNameValuePair
//import org.apache.http.util.EntityUtils
//import org.jsoup.Jsoup
//import org.jsoup.nodes.Document
import java.io.IOException
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore
import java.net.HttpCookie
import java.net.InetSocketAddress
import java.net.ProxySelector
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect
import java.net.http.HttpClient.Version
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant

object LoginTest
{
	@JvmStatic
	fun main(args: Array<String>)
	{
//		testOkHttp()
//		testKhttp()
//		testMibael()
//		testHttpClient()
		
		testFactory()
		
		println("success")
	}
	
	private fun testFactory()
	{
		val serializer = ObjectMapper()
			.also { it.registerModule(KotlinModule()) }
			.let { JacksonSerializerAdapter(it) }
		
		val logger = { log: String -> }
		
		val clientFactory = HttpSeChatClientFactory(serializer, logger)
		
		val credentials = SeCredentials(
			"hawk.wiet.lol@gmail.com",
			"z0WMyNeKWwzOcy6k"
		)
		
		val client = clientFactory.create(credentials)
		
		client.sendMessage(1, "Hello, World!")
	}
	
//	private fun testOkHttp()
//	{
//		val client: OkHttpClient = OkHttpClient().newBuilder()
//			.build()
//		val body: RequestBody = RequestBody.create(
//			MediaType.parse("application/x-www-form-urlencoded"),
//			"fkey=147088789b90d5746a8e22163ee5bb1760403d9d40664f9a7ea2d0904924ce8e&email=hawk.wiet.lol%40gmail.com&password=z0WMyNeKWwzOcy6k")
//		val request: Request = Request.Builder()
//			.url("https://stackoverflow.com/users/login")
//			.method("POST", body)
//			.addHeader("Content-Type", "application/x-www-form-urlencoded")
//			.addHeader("Content-Length", "127")
//			.addHeader("Host", "stackoverflow.com")
//			.build()
//
//		println("headers 1:")
//		request.headers().toMultimap().forEach { t, u -> println("$t: ${u.joinToString()}") }
//		println()
//		val response: Response = client.newCall(request).execute()
//
//		println("headers 2:")
//		response.headers().toMultimap().forEach { t, u -> println("$t: ${u.joinToString()}") }
//		println()
//
//		println(response.body().charStream().use { it.readText() })
//	}
	
	private fun testKhttp()
	{
		val headers = mapOf(
			"Content-Length" to "127",
			"Content-Type" to "application/x-www-form-urlencoded"
		)
		
		val data = mapOf(
			"fkey" to "147088789b90d5746a8e22163ee5bb1760403d9d40664f9a7ea2d0904924ce8e",
			"email" to "hawk.wiet.lol@gmail.com",
			"password" to "z0WMyNeKWwzOcy6k"
		)

//		val url = "http://localhost:8080/hello"
		val url = "https://stackoverflow.com/users/login"
		val response = khttp.post(url, headers = headers, data = data)
		
		response
			.headers
			.asSequence()
			.sortedBy { it.key }
			.forEach {
				println("${it.key}: ${it.value}")
			}
		
		println()
		
		response
			.cookies
			.asSequence()
			.sortedBy { it.key }
			.forEach {
				println("${it.key}: ${it.value}")
			}
		
		println()
		
		println(response.text)
	}
	
	private fun testHttpClient()
	{
		val cookieHandler = CookieManager(null, CookiePolicy.ACCEPT_ALL)
		
		val client: HttpClient = HttpClient.newBuilder()
			.version(Version.HTTP_1_1)
			.followRedirects(Redirect.NORMAL)
			.connectTimeout(Duration.ofSeconds(20))
			.cookieHandler(cookieHandler)
			.build()
		
		val request = HttpRequest.newBuilder()
			.POST(ofFormData(mapOf(
				"fkey" to "147088789b90d5746a8e22163ee5bb1760403d9d40664f9a7ea2d0904924ce8e",
				"email" to "hawk.wiet.lol@gmail.com",
				"password" to "z0WMyNeKWwzOcy6k"
			)))
			.uri(URI.create("https://stackoverflow.com/users/login"))
			.header("Content-Type", "application/x-www-form-urlencoded")
			.build()
		
		val response: HttpResponse<String> = client.send(request, BodyHandlers.ofString())
		
		val sourceCookie = cookieHandler.cookieStore.get(URI("https://stackoverflow.com/")).first()
		
		val targetJar = khttp.get("https://stackoverflow.com/uses/login").cookies
		val targetCookie = targetJar.getCookie("prov")
		
		val cookieJar = cookieHandler.cookieStore.get(URI("https://stackoverflow.com/"))
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
		
		response.headers().map().forEach { t, u -> println("$t : ${u.joinToString()}") }
		
		val newCookie = Cookie(
			sourceCookie.name,
			sourceCookie.value,
			mapOf(
				"domain" to sourceCookie.domain,
				"expires" to Instant.EPOCH.plusMillis(sourceCookie.maxAge).plusMillis(sourceCookie.whenCreated),
				"path" to sourceCookie.path,
				"HttpOnly" to null
			)
		)
		
		// expires = Fri, 01-Jan-2055 00:00:00 GMT
		// maxAge =         1090072607 * 1000
		// whenCreated = 1592301792285
		//               1593391864892
		
		println()
	}
	
	private val HttpCookie.whenCreated: Long
		get()
		{
			val field = this.javaClass.declaredFields
				.single { it.name == "whenCreated" }
			
			field.isAccessible = true
			
			return field.get(this) as Long
		}
	
	// Sample: 'password=123&custom=secret&username=abc&ts=1570704369823'
	fun ofFormData(data: Map<Any, Any>): BodyPublisher
	{
		val builder = StringBuilder()
		for (entry in data.entries)
		{
			if (builder.length > 0)
			{
				builder.append("&")
			}
			builder.append(URLEncoder.encode(entry.key.toString(), StandardCharsets.UTF_8))
			builder.append("=")
			builder.append(URLEncoder.encode(entry.value.toString(), StandardCharsets.UTF_8))
		}
		return BodyPublishers.ofString(builder.toString())
	}
	
//	private fun testMibael()
//	{
//
//		/*
//		 * 4/2/2020: You cannot just call HttpClients.createDefault(). When
//		 * this method is used, Apache's HTTP library does not properly
//		 * parse the cookies it receives when logging into StackOverflow,
//		 * and thus cannot join any chat rooms.
//		 *
//		 * Solution found here: https://stackoverflow.com/q/36473478/13379
//		 */
//
//		/*
//		 * 4/2/2020: You cannot just call HttpClients.createDefault(). When
//		 * this method is used, Apache's HTTP library does not properly
//		 * parse the cookies it receives when logging into StackOverflow,
//		 * and thus cannot join any chat rooms.
//		 *
//		 * Solution found here: https://stackoverflow.com/q/36473478/13379
//		 */
//		val httpClient = if (false)
//			HttpClients.createDefault()
//		else
//			HttpClients.custom()
//				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
//				.build()
//
//		val response = login("hawk.wiet.lol@gmail.com", "z0WMyNeKWwzOcy6k", httpClient)
//
//		println(response.bodyHtml)
//
//		println(httpClient)
//	}
//
//	private fun login(email: String, password: String, client: CloseableHttpClient): CloseableHttpResponse
//	{
//		val formResponse = send(client, HttpGet("https://stackoverflow.com/users/login"))
//		val loginPage = formResponse.bodyHtml
//		val elements = loginPage.select("input[name=fkey]")
//		if (elements.isEmpty())
//		{
//			throw IOException("\"fkey\" field not found on Stack Exchange login page, cannot login.")
//		}
//
//		val fkey = elements.first().attr("value")
//		println(fkey)
//
//		val loginResponse = send(client, HttpPost("https://stackoverflow.com/users/login").also {
//			val params = listOf(
//				BasicNameValuePair("email", email),
//				BasicNameValuePair("password", password),
//				BasicNameValuePair("fkey", fkey)
//			)
//
//			it.entity = UrlEncodedFormEntity(params, Consts.UTF_8)
//		})
//
//		return loginResponse
//	}
//
//	private fun send(client: CloseableHttpClient, request: HttpUriRequest): CloseableHttpResponse
//	{
//		return client.execute(request)
//	}
//
//	private val CloseableHttpResponse.bodyHtml: Document
//		get() = Jsoup.parse(EntityUtils.toString(entity), "https://stackoverflow.com/users/login")
}
