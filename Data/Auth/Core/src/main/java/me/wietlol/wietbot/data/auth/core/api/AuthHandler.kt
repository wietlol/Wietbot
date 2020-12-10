package me.wietlol.wietbot.data.auth.core.api

import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.wietbot.data.auth.core.interfaces.AuthRepository
import me.wietlol.wietbot.data.auth.core.setup.DependencyInjection
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultAttachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultCreateGrantedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultCreatePermissionResponse
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultCreatePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultCreateRevokedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRoleRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRoleResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultCreateRoleResponse
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultDetachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequest
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultGetOrCreateUserResponse
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultGetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequest
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultIsUserAuthorizedResponse
import me.wietlol.wietbot.data.auth.models.models.ListRolesRequest
import me.wietlol.wietbot.data.auth.models.models.ListRolesResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultListRolesResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultPlatform
import me.wietlol.wietbot.data.auth.models.models.DefaultRole
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultSetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.DefaultUser
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import org.koin.core.KoinComponent
import org.koin.core.get

@Suppress("unused")
class AuthHandler : KoinComponent, AuthService, BaseHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	private val authRepository: AuthRepository = get()
	
	override val requestSchema: Schema = get()
	override val responseSchema: Schema = requestSchema
	override val logger: Logger<CommonLog> = get()
	
	private val getUserRoleEventIds = FunctionEventIdSet(
		EventId(1156246108, "getUserRole-request"),
		EventId(2043522813, "getUserRole-response"),
		EventId(2027708373, "getUserRole-error"),
	)
	private val setUserRoleEventIds = FunctionEventIdSet(
		EventId(1071603162, "setUserRole-request"),
		EventId(1138585112, "setUserRole-response"),
		EventId(1921763365, "setUserRole-error"),
	)
	private val getOrCreateUserEventIds = FunctionEventIdSet(
		EventId(1421207747, "getOrCreateUser-request"),
		EventId(1634046031, "getOrCreateUser-response"),
		EventId(1369365992, "getOrCreateUser-error"),
	)
	private val createPermissionEventIds = FunctionEventIdSet(
		EventId(1764008249, "createPermission-request"),
		EventId(1542483810, "createPermission-response"),
		EventId(1046556993, "createPermission-error"),
	)
	private val isUserAuthorizedEventIds = FunctionEventIdSet(
		EventId(1173557872, "isUserAuthorized-request"),
		EventId(1637035158, "isUserAuthorized-response"),
		EventId(1052228479, "isUserAuthorized-error"),
	)
	private val createRoleEventIds = FunctionEventIdSet(
		EventId(2144296492, "createRole-request"),
		EventId(1859811039, "createRole-response"),
		EventId(1771707305, "createRole-error"),
	)
	private val createPolicyEventIds = FunctionEventIdSet(
		EventId(1656984633, "createPolicy-request"),
		EventId(1323713517, "createPolicy-response"),
		EventId(1138202132, "createPolicy-error"),
	)
	private val createGrantedAuthorityEventIds = FunctionEventIdSet(
		EventId(1342207955, "createGrantedAuthority-request"),
		EventId(1497193537, "createGrantedAuthority-response"),
		EventId(1685939024, "createGrantedAuthority-error"),
	)
	private val createRevokedAuthorityEventIds = FunctionEventIdSet(
		EventId(1081067575, "createRevokedAuthority-request"),
		EventId(1418554945, "createRevokedAuthority-response"),
		EventId(1937093126, "createRevokedAuthority-error"),
	)
	private val attachRolePolicyEventIds = FunctionEventIdSet(
		EventId(1274988974, "attachRolePolicy-request"),
		EventId(2024885754, "attachRolePolicy-response"),
		EventId(1835770409, "attachRolePolicy-error"),
	)
	private val detachRolePolicyEventIds = FunctionEventIdSet(
		EventId(1329308834, "detachRolePolicy-request"),
		EventId(1204918986, "detachRolePolicy-response"),
		EventId(1899611963, "detachRolePolicy-error"),
	)
	private val listRolesEventIds = FunctionEventIdSet(
		EventId(1038032539, "listRoles-request"),
		EventId(1412393639, "listRoles-response"),
		EventId(1617062995, "listRoles-error"),
	)
	
	fun getUserRoleBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, getUserRoleEventIds, ::getUserRole)
	
	override fun getUserRole(request: GetUserRoleRequest): GetUserRoleResponse
	{
		val role = authRepository.getUserRole(request.localUserId, request.platform.name)
		
		return DefaultGetUserRoleResponse(
			DefaultRole(
				role.id.value,
				role.name
			)
		)
	}
	
	fun setUserRoleBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, setUserRoleEventIds, ::setUserRole)
	
	override fun setUserRole(request: SetUserRoleRequest): SetUserRoleResponse
	{
		authRepository.setUserRole(request.localUserId, request.platform.name, request.role)
		
		return DefaultSetUserRoleResponse()
	}
	
	fun getOrCreateUserBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, getOrCreateUserEventIds, ::getOrCreateUser)
	
	override fun getOrCreateUser(request: GetOrCreateUserRequest): GetOrCreateUserResponse
	{
		val user = authRepository.getOrCreateUser(request.localId, request.platform.name, request.localName)
		
		return DefaultGetOrCreateUserResponse(
			DefaultUser(
				when (request.platform.name)
				{
					DefaultPlatform.stackOverflow.name -> user.stackOverflowId
					DefaultPlatform.discord.name -> user.discordId
					DefaultPlatform.wietbotWebsite.name -> user.wietbotWebsiteId
					else -> "-"
				},
				when (request.platform.name)
				{
					DefaultPlatform.stackOverflow.name -> user.stackOverflowName
					DefaultPlatform.discord.name -> user.discordName
					DefaultPlatform.wietbotWebsite.name -> user.wietbotWebsiteName
					else -> "-"
				},
				request.platform,
				user.id.value,
				user.stackOverflowId,
				user.stackOverflowName,
				user.discordId,
				user.discordName,
				user.wietbotWebsiteId,
				user.wietbotWebsiteName,
				user.role.id.value,
			)
		)
	}
	
	fun createPermissionBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, createPermissionEventIds, ::createPermission)
	
	override fun createPermission(request: CreatePermissionRequest): CreatePermissionResponse
	{
		authRepository.createPermission(request.name)
		
		return DefaultCreatePermissionResponse()
	}
	
	fun isUserAuthorizedBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, isUserAuthorizedEventIds, ::isUserAuthorized)
	
	override fun isUserAuthorized(request: IsUserAuthorizedRequest): IsUserAuthorizedResponse
	{
		val isAuthorized = authRepository.isUserAuthorized(request.userId, request.platform.name, request.permission, request.resource)
		
		return DefaultIsUserAuthorizedResponse(
			isAuthorized
		)
	}
	
	fun createRoleBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, createRoleEventIds, ::createRole)
	
	override fun createRole(request: CreateRoleRequest): CreateRoleResponse
	{
		authRepository.createRole(request.name)
		
		return DefaultCreateRoleResponse()
	}
	
	fun createPolicyBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, createPolicyEventIds, ::createPolicy)
	
	override fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse
	{
		authRepository.createPolicy(request.name)
		
		return DefaultCreatePolicyResponse()
	}
	
	fun createGrantedAuthorityBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, createGrantedAuthorityEventIds, ::createGrantedAuthority)
	
	override fun createGrantedAuthority(request: CreateGrantedAuthorityRequest): CreateGrantedAuthorityResponse
	{
		authRepository.createGrantedAuthority(request.policy, request.permission, request.resource)
		
		return DefaultCreateGrantedAuthorityResponse()
	}
	
	fun createRevokedAuthorityBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, createRevokedAuthorityEventIds, ::createRevokedAuthority)
	
	override fun createRevokedAuthority(request: CreateRevokedAuthorityRequest): CreateRevokedAuthorityResponse
	{
		authRepository.createRevokedAuthority(request.policy, request.permission, request.resource)
		
		return DefaultCreateRevokedAuthorityResponse()
	}
	
	fun attachRolePolicyBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, attachRolePolicyEventIds, ::attachRolePolicy)
	
	override fun attachRolePolicy(request: AttachRolePolicyRequest): AttachRolePolicyResponse
	{
		authRepository.attachRolePolicy(request.role, request.policy)
		
		return DefaultAttachRolePolicyResponse()
	}
	
	fun detachRolePolicyBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, detachRolePolicyEventIds, ::detachRolePolicy)
	
	override fun detachRolePolicy(request: DetachRolePolicyRequest): DetachRolePolicyResponse
	{
		authRepository.detachRolePolicy(request.role, request.policy)
		
		return DefaultDetachRolePolicyResponse()
	}
	
	fun listRolesBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, listRolesEventIds, ::listRoles)
	
	override fun listRoles(request: ListRolesRequest): ListRolesResponse
	{
		val roles = authRepository.listRoles()
		
		return DefaultListRolesResponse(
			roles.map {
				DefaultRole(
					it.id.value,
					it.name
				)
			}
		)
	}
}
