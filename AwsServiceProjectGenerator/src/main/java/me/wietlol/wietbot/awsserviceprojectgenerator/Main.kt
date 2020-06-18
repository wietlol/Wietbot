package me.wietlol.wietbot.awsserviceprojectgenerator

import com.amazonaws.regions.Regions
import com.amazonaws.services.codecommit.AWSCodeCommit
import com.amazonaws.services.codecommit.AWSCodeCommitClientBuilder
import com.amazonaws.services.codecommit.model.CreateCommitRequest
import com.amazonaws.services.codecommit.model.CreateRepositoryRequest
import com.amazonaws.services.codecommit.model.CreateRepositoryResult
import com.amazonaws.services.codecommit.model.DeleteRepositoryRequest
import com.amazonaws.services.codecommit.model.PutFileEntry
import com.amazonaws.services.codecommit.model.RepositoryMetadata
import java.nio.ByteBuffer

object Main
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		try
		{
			println("creating a new Wietbot project")
			println("enter the project name")
			val projectName = readLine()!!
			
			createProject(projectName)
		}
		catch (ex: Exception)
		{
			ex.printStackTrace()
			println("press [enter] to exit...")
			readLine()
		}
	}
	
	private fun createProject(projectName: String)
	{
		val projectMavenName = projectName
			.asSequence()
			.flatMap {
				if (it.isUpperCase())
					sequenceOf('-', it.toLowerCase())
				else
					sequenceOf(it)
			}
			.joinToString("")
			.substring(1)
		
		createProjectRepository("Wietbot$projectName", listFiles(projectMavenName))
	}
	
	private fun listFiles(projectMavenName: String): List<ProjectFile> =
		sequenceOf(
			"/Client/pom.xml",
			"/Core/pom.xml",
			"/Core/serverless.yml",
			"/Models/pom.xml",
			"/.gitignore",
			"/pom.xml"
		)
			.map { path ->
				val content = javaClass
					.getResourceAsStream("/projecttemplate$path")
					.bufferedReader()
					.use { it.readText() }
					.replace("__maven-project-name__", projectMavenName)
					.replace("__projectpackage__", projectMavenName.replace("-", ""))
				val actualPath = path
					.replace("__projectpackage__", projectMavenName.replace("-", ""))
				
				ProjectFile(actualPath, content)
			}.toList()
	
	private fun createProjectRepository(name: String, files: List<ProjectFile>): RepositoryMetadata
	{
		val client: AWSCodeCommit = AWSCodeCommitClientBuilder
			.standard()
			.withRegion(Regions.EU_WEST_1)
			.build()
		
		client.deleteRepository(DeleteRepositoryRequest()
			.withRepositoryName(name))
		
		val repository: CreateRepositoryResult = client.createRepository(CreateRepositoryRequest()
			.withRepositoryName(name))
		
		try
		{
			client.createCommit(CreateCommitRequest()
				.withRepositoryName(name)
				.withBranchName("master")
				.withCommitMessage("initial commit")
				.withPutFiles(
					files.map {
						PutFileEntry()
							.withFilePath(it.path)
							.withFileContent(ByteBuffer.wrap(it.content.toByteArray()))
					}
				)
			)
			
			return repository.repositoryMetadata
		}
		catch (ex: Exception)
		{
			client.deleteRepository(DeleteRepositoryRequest()
				.withRepositoryName(name))
			throw ex
		}
	}
	
	private data class ProjectFile(
		val path: String,
		val content: String
	)
}
