package me.wietlol.wietbot.data.auth.models

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

interface AuthService
{
	fun getOrCreateUser(request: GetOrCreateUserRequest): GetOrCreateUserResponse
	
	fun createPermission(request: CreatePermissionRequest): CreatePermissionResponse
	
	fun createRole(request: CreateRoleRequest): CreateRoleResponse
	
	fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse
	
	fun createGrantedAuthority(request: CreateGrantedAuthorityRequest): CreateGrantedAuthorityResponse
	
	fun createRevokedAuthority(request: CreateRevokedAuthorityRequest): CreateRevokedAuthorityResponse
	
	fun attachRolePolicy(request: AttachRolePolicyRequest): AttachRolePolicyResponse
	
	fun detachRolePolicy(request: DetachRolePolicyRequest): DetachRolePolicyResponse
	
	fun getUserRole(request: GetUserRoleRequest): GetUserRoleResponse
	
	fun setUserRole(request: SetUserRoleRequest): SetUserRoleResponse
	
	fun listRoles(request: ListRolesRequest): ListRolesResponse
	
	fun isUserAuthorized(request: IsUserAuthorizedRequest): IsUserAuthorizedResponse
}
