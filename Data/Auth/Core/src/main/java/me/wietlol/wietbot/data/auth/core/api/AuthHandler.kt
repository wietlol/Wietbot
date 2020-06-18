package me.wietlol.wietbot.data.auth.core.api

import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.aws.lambda.LambdaResponse
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.data.auth.core.interfaces.AuthRepository
import me.wietlol.wietbot.data.auth.core.setup.DependencyInjection
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionResponse
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.CreatePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityResponse
import me.wietlol.wietbot.data.auth.models.models.CreateRoleRequest
import me.wietlol.wietbot.data.auth.models.models.CreateRoleResponse
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyRequest
import me.wietlol.wietbot.data.auth.models.models.DetachRolePolicyResponse
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequest
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserResponse
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequest
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponse
import me.wietlol.wietbot.data.auth.models.models.ListRolesRequest
import me.wietlol.wietbot.data.auth.models.models.ListRolesResponse
import me.wietlol.wietbot.data.auth.models.models.Role
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleRequest
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleResponse
import me.wietlol.wietbot.data.auth.models.models.User
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

class AuthHandler : KoinComponent, AuthService
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val authRepository: AuthRepository = get()
	val schema: Schema = get()
	
	fun getUserRoleBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<GetUserRoleRequest>(it) }
			?.let { getUserRole(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun getUserRole(request: GetUserRoleRequest): GetUserRoleResponse
	{
		val role = authRepository.getUserRole(request.userId)
		
		return GetUserRoleResponse.of(
			Role.of(
				role.id.value,
				role.name
			)
		)
	}
	
	fun setUserRoleBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<SetUserRoleRequest>(it) }
			?.let { setUserRole(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun setUserRole(request: SetUserRoleRequest): SetUserRoleResponse
	{
		authRepository.setUserRole(request.userId, request.role)
		
		return SetUserRoleResponse.of()
	}
	
	fun getOrCreateUserBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<GetOrCreateUserRequest>(it) }
			?.let { getOrCreateUser(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun getOrCreateUser(request: GetOrCreateUserRequest): GetOrCreateUserResponse
	{
		val user = authRepository.getOrCreateUser(request.user.id, request.user.name)
		
		return GetOrCreateUserResponse.of(
			User.of(
				user.stackExchangeId,
				user.name
			)
		)
	}
	
	fun createPermissionBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<CreatePermissionRequest>(it) }
			?.let { createPermission(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun createPermission(request: CreatePermissionRequest): CreatePermissionResponse
	{
		authRepository.createPermission(request.name)
		
		return CreatePermissionResponse.of()
	}
	
	fun isUserAuthorizedBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<IsUserAuthorizedRequest>(it) }
			?.let { isUserAuthorized(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun isUserAuthorized(request: IsUserAuthorizedRequest): IsUserAuthorizedResponse
	{
		val isAuthorized = authRepository.isUserAuthorized(request.userId, request.permission, request.resource)
		
		return IsUserAuthorizedResponse.of(
			isAuthorized
		)
	}
	
	fun createRoleBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<CreateRoleRequest>(it) }
			?.let { createRole(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun createRole(request: CreateRoleRequest): CreateRoleResponse
	{
		authRepository.createRole(request.name)
		
		return CreateRoleResponse.of()
	}
	
	fun createPolicyBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<CreatePolicyRequest>(it) }
			?.let { createPolicy(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse
	{
		authRepository.createPolicy(request.name)
		
		return CreatePolicyResponse.of()
	}
	
	fun createGrantedAuthorityBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<CreateGrantedAuthorityRequest>(it) }
			?.let { createGrantedAuthority(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun createGrantedAuthority(request: CreateGrantedAuthorityRequest): CreateGrantedAuthorityResponse
	{
		authRepository.createGrantedAuthority(request.policy, request.permission, request.resource)
		
		return CreateGrantedAuthorityResponse.of()
	}
	
	fun createRevokedAuthorityBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<CreateRevokedAuthorityRequest>(it) }
			?.let { createRevokedAuthority(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun createRevokedAuthority(request: CreateRevokedAuthorityRequest): CreateRevokedAuthorityResponse
	{
		authRepository.createRevokedAuthority(request.policy, request.permission, request.resource)
		
		return CreateRevokedAuthorityResponse.of()
	}
	
	fun attachRolePolicyBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<AttachRolePolicyRequest>(it) }
			?.let { attachRolePolicy(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun attachRolePolicy(request: AttachRolePolicyRequest): AttachRolePolicyResponse
	{
		authRepository.attachRolePolicy(request.role, request.policy)
		
		return AttachRolePolicyResponse.of()
	}
	
	fun detachRolePolicyBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<DetachRolePolicyRequest>(it) }
			?.let { detachRolePolicy(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun detachRolePolicy(request: DetachRolePolicyRequest): DetachRolePolicyResponse
	{
		authRepository.detachRolePolicy(request.role, request.policy)
		
		return DetachRolePolicyResponse.of()
	}
	
	fun listRolesBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<ListRolesRequest>(it) }
			?.let { listRoles(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun listRoles(request: ListRolesRequest): ListRolesResponse
	{
		val roles = authRepository.listRoles()
		
		return ListRolesResponse.of(
			roles.map {
				Role.of(
					it.id.value,
					it.name
				)
			}
		)
	}
}
