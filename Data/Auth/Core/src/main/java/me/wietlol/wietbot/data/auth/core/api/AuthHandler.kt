package me.wietlol.wietbot.data.auth.core.api

import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.wietbot.data.auth.core.interfaces.AuthRepository
import me.wietlol.wietbot.data.auth.core.setup.DependencyInjection
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyResponseImpl
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityResponseImpl
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionResponse
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionResponseImpl
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyResponseImpl
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityResponseImpl
import me.wietlol.wietbot.data.auth.models.models.CreateRoleRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRoleResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRoleResponseImpl
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyResponseImpl
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequest
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserResponse
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserResponseImpl
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleResponseImpl
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequest
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponse
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponseImpl
import me.wietlol.wietbot.data.auth.models.models.ListRolesRequest
import me.wietlol.wietbot.data.auth.models.models.ListRolesResponse
import me.wietlol.wietbot.data.auth.models.models.ListRolesResponseImpl
import me.wietlol.wietbot.data.auth.models.models.RoleImpl
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleResponseImpl
import me.wietlol.wietbot.data.auth.models.models.UserImpl
import org.koin.core.KoinComponent
import org.koin.core.get

@Suppress("unused")
class AuthHandler : KoinComponent, AuthService
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val authRepository: AuthRepository = get()
	val schema: Schema = get()
	
	fun getUserRoleBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<GetUserRoleRequest>(it) }
			?.let { getUserRole(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun getUserRole(request: GetUserRoleRequest): GetUserRoleResponse
	{
		val role = authRepository.getUserRole(request.userId)
		
		return GetUserRoleResponseImpl(
			RoleImpl(
				role.id.value,
				role.name
			)
		)
	}
	
	fun setUserRoleBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<SetUserRoleRequest>(it) }
			?.let { setUserRole(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun setUserRole(request: SetUserRoleRequest): SetUserRoleResponse
	{
		authRepository.setUserRole(request.userId, request.role)
		
		return SetUserRoleResponseImpl()
	}
	
	fun getOrCreateUserBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<GetOrCreateUserRequest>(it) }
			?.let { getOrCreateUser(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun getOrCreateUser(request: GetOrCreateUserRequest): GetOrCreateUserResponse
	{
		val user = authRepository.getOrCreateUser(request.user.id, request.user.name)
		
		return GetOrCreateUserResponseImpl(
			UserImpl(
				user.stackExchangeId,
				user.name
			)
		)
	}
	
	fun createPermissionBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<CreatePermissionRequest>(it) }
			?.let { createPermission(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun createPermission(request: CreatePermissionRequest): CreatePermissionResponse
	{
		authRepository.createPermission(request.name)
		
		return CreatePermissionResponseImpl()
	}
	
	fun isUserAuthorizedBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<IsUserAuthorizedRequest>(it) }
			?.let { isUserAuthorized(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun isUserAuthorized(request: IsUserAuthorizedRequest): IsUserAuthorizedResponse
	{
		val isAuthorized = authRepository.isUserAuthorized(request.userId, request.permission, request.resource)
		
		return IsUserAuthorizedResponseImpl(
			isAuthorized
		)
	}
	
	fun createRoleBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<CreateRoleRequest>(it) }
			?.let { createRole(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun createRole(request: CreateRoleRequest): CreateRoleResponse
	{
		authRepository.createRole(request.name)
		
		return CreateRoleResponseImpl()
	}
	
	fun createPolicyBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<CreatePolicyRequest>(it) }
			?.let { createPolicy(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse
	{
		authRepository.createPolicy(request.name)
		
		return CreatePolicyResponseImpl()
	}
	
	fun createGrantedAuthorityBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<CreateGrantedAuthorityRequest>(it) }
			?.let { createGrantedAuthority(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun createGrantedAuthority(request: CreateGrantedAuthorityRequest): CreateGrantedAuthorityResponse
	{
		authRepository.createGrantedAuthority(request.policy, request.permission, request.resource)
		
		return CreateGrantedAuthorityResponseImpl()
	}
	
	fun createRevokedAuthorityBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<CreateRevokedAuthorityRequest>(it) }
			?.let { createRevokedAuthority(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun createRevokedAuthority(request: CreateRevokedAuthorityRequest): CreateRevokedAuthorityResponse
	{
		authRepository.createRevokedAuthority(request.policy, request.permission, request.resource)
		
		return CreateRevokedAuthorityResponseImpl()
	}
	
	fun attachRolePolicyBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<AttachRolePolicyRequest>(it) }
			?.let { attachRolePolicy(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun attachRolePolicy(request: AttachRolePolicyRequest): AttachRolePolicyResponse
	{
		authRepository.attachRolePolicy(request.role, request.policy)
		
		return AttachRolePolicyResponseImpl()
	}
	
	fun detachRolePolicyBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<DetachRolePolicyRequest>(it) }
			?.let { detachRolePolicy(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun detachRolePolicy(request: DetachRolePolicyRequest): DetachRolePolicyResponse
	{
		authRepository.detachRolePolicy(request.role, request.policy)
		
		return DetachRolePolicyResponseImpl()
	}
	
	fun listRolesBit(request: ByteWrapper): ByteWrapper? =
		request
			.payload
			?.let { schema.deserialize<ListRolesRequest>(it) }
			?.let { listRoles(it) }
			?.let { schema.serialize(it) }
			?.let { ByteWrapper(it) }
	
	override fun listRoles(request: ListRolesRequest): ListRolesResponse
	{
		val roles = authRepository.listRoles()
		
		return ListRolesResponseImpl(
			roles.map {
				RoleImpl(
					it.id.value,
					it.name
				)
			}
		)
	}
}
