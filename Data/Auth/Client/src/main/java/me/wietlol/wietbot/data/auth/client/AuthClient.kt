package me.wietlol.wietbot.data.auth.client

import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.model.InvocationType.RequestResponse
import com.amazonaws.services.lambda.model.InvokeRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleResponse
import me.wietlol.aws.lambda.LambdaException
import me.wietlol.aws.lambda.LambdaExceptionDeserializer
import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.aws.lambda.LambdaResponse
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.LocalModelRegistry
import me.wietlol.bitblock.core.serialization.ImmutableSchema
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.auth.models.WietbotDataAuth
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRoleRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRoleResponse
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequest
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserResponse
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequest
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponse
import me.wietlol.wietbot.data.auth.models.models.ListRolesRequest
import me.wietlol.wietbot.data.auth.models.models.ListRolesResponse

class AuthClient(
	val lambdaClient: AWSLambda,
	val serializer: Schema = LocalModelRegistry()
		.apply(BitBlock::initialize)
		.apply(WietbotDataAuth::initialize)
		.let { ImmutableSchema(WietbotDataAuth::class.java.getResourceAsStream("/me/wietlol/wietbot/data/auth/models/Api.bitschema"), it) },
	val functionPrefix: String = "wietbot-data-auth-dev-"
) : AuthService
{
	override fun getUserRole(request: GetUserRoleRequest): GetUserRoleResponse =
		invoke("getUserRole", request)
	
	override fun setUserRole(request: SetUserRoleRequest): SetUserRoleResponse =
		invoke("setUserRole", request)
	
	override fun getOrCreateUser(request: GetOrCreateUserRequest): GetOrCreateUserResponse =
		invoke("getOrCreateUser", request)
	
	override fun createPermission(request: CreatePermissionRequest): CreatePermissionResponse =
		invoke("createPermission", request)
	
	override fun isUserAuthorized(request: IsUserAuthorizedRequest): IsUserAuthorizedResponse =
		invoke("isUserAuthorized", request)
	
	override fun createRole(request: CreateRoleRequest): CreateRoleResponse =
		invoke("createRole", request)
	
	override fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse =
		invoke("createPolicy", request)
	
	override fun createGrantedAuthority(request: CreateGrantedAuthorityRequest): CreateGrantedAuthorityResponse =
		invoke("createGrantedAuthority", request)
	
	override fun createRevokedAuthority(request: CreateRevokedAuthorityRequest): CreateRevokedAuthorityResponse =
		invoke("createRevokedAuthority", request)
	
	override fun attachRolePolicy(request: AttachRolePolicyRequest): AttachRolePolicyResponse =
		invoke("attachRolePolicy", request)
	
	override fun detachRolePolicy(request: DetachRolePolicyRequest): DetachRolePolicyResponse =
		invoke("detachRolePolicy", request)
	
	override fun listRoles(request: ListRolesRequest): ListRolesResponse =
		invoke("listRoles", request)
	
	private val mapper = ObjectMapper()
		.also { mapper ->
			mapper.registerModule(
				SimpleModule()
					.apply { addDeserializer(LambdaException::class.java, LambdaExceptionDeserializer(mapper)) }
			)
		}
	
	private inline fun <reified Response : Any> invoke(function: String, request: Any): Response
	{
		val lambdaRequest = LambdaRequest(
			serializer.serialize(request)
		)
		
		val response = lambdaClient.invoke(InvokeRequest()
			.withFunctionName(functionPrefix + function)
			.withInvocationType(RequestResponse)
			.withPayload(mapper.writeValueAsString(lambdaRequest))
		)
		
		if (response.functionError != null)
			throw mapper.readValue(response.payload.array(), LambdaException::class.java)
		
		val lambdaResponse = mapper.readValue(response.payload.array(), LambdaResponse::class.java)
		
		return serializer.deserialize(lambdaResponse.payload!!)!!
	}
}
