// hash: #785c126b
// @formatter:off
package me.wietlol.wietbot.data.auth.models

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.registry.RegistrySet
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.wietbot.data.auth.models.serializers.*

object WietbotDataAuth : RegistrySet
{
	override val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>>
		= createModelSerializers()
	
	private fun createModelSerializers(): Map<ModelRegistryKey, ModelSerializer<*, *>> =
		mapOf(
			CommonModelRegistryKey("GetOrCreateUserRequest", "WietbotDataAuth", "Wietbot", "1.0") to GetOrCreateUserRequestSerializer,
			CommonModelRegistryKey("GetOrCreateUserResponse", "WietbotDataAuth", "Wietbot", "1.0") to GetOrCreateUserResponseSerializer,
			CommonModelRegistryKey("CreatePermissionRequest", "WietbotDataAuth", "Wietbot", "1.0") to CreatePermissionRequestSerializer,
			CommonModelRegistryKey("CreatePermissionResponse", "WietbotDataAuth", "Wietbot", "1.0") to CreatePermissionResponseSerializer,
			CommonModelRegistryKey("CreateRoleRequest", "WietbotDataAuth", "Wietbot", "1.0") to CreateRoleRequestSerializer,
			CommonModelRegistryKey("CreateRoleResponse", "WietbotDataAuth", "Wietbot", "1.0") to CreateRoleResponseSerializer,
			CommonModelRegistryKey("CreatePolicyRequest", "WietbotDataAuth", "Wietbot", "1.0") to CreatePolicyRequestSerializer,
			CommonModelRegistryKey("CreatePolicyResponse", "WietbotDataAuth", "Wietbot", "1.0") to CreatePolicyResponseSerializer,
			CommonModelRegistryKey("CreateGrantedAuthorityRequest", "WietbotDataAuth", "Wietbot", "1.0") to CreateGrantedAuthorityRequestSerializer,
			CommonModelRegistryKey("CreateGrantedAuthorityResponse", "WietbotDataAuth", "Wietbot", "1.0") to CreateGrantedAuthorityResponseSerializer,
			CommonModelRegistryKey("CreateRevokedAuthorityRequest", "WietbotDataAuth", "Wietbot", "1.0") to CreateRevokedAuthorityRequestSerializer,
			CommonModelRegistryKey("CreateRevokedAuthorityResponse", "WietbotDataAuth", "Wietbot", "1.0") to CreateRevokedAuthorityResponseSerializer,
			CommonModelRegistryKey("AttachRolePolicyRequest", "WietbotDataAuth", "Wietbot", "1.0") to AttachRolePolicyRequestSerializer,
			CommonModelRegistryKey("AttachRolePolicyResponse", "WietbotDataAuth", "Wietbot", "1.0") to AttachRolePolicyResponseSerializer,
			CommonModelRegistryKey("DetachRolePolicyRequest", "WietbotDataAuth", "Wietbot", "1.0") to DetachRolePolicyRequestSerializer,
			CommonModelRegistryKey("DetachRolePolicyResponse", "WietbotDataAuth", "Wietbot", "1.0") to DetachRolePolicyResponseSerializer,
			CommonModelRegistryKey("GetUserRoleRequest", "WietbotDataAuth", "Wietbot", "1.0") to GetUserRoleRequestSerializer,
			CommonModelRegistryKey("GetUserRoleResponse", "WietbotDataAuth", "Wietbot", "1.0") to GetUserRoleResponseSerializer,
			CommonModelRegistryKey("SetUserRoleRequest", "WietbotDataAuth", "Wietbot", "1.0") to SetUserRoleRequestSerializer,
			CommonModelRegistryKey("SetUserRoleResponse", "WietbotDataAuth", "Wietbot", "1.0") to SetUserRoleResponseSerializer,
			CommonModelRegistryKey("ListRolesRequest", "WietbotDataAuth", "Wietbot", "1.0") to ListRolesRequestSerializer,
			CommonModelRegistryKey("ListRolesResponse", "WietbotDataAuth", "Wietbot", "1.0") to ListRolesResponseSerializer,
			CommonModelRegistryKey("IsUserAuthorizedRequest", "WietbotDataAuth", "Wietbot", "1.0") to IsUserAuthorizedRequestSerializer,
			CommonModelRegistryKey("IsUserAuthorizedResponse", "WietbotDataAuth", "Wietbot", "1.0") to IsUserAuthorizedResponseSerializer,
			CommonModelRegistryKey("User", "WietbotDataAuth", "Wietbot", "1.0") to UserSerializer,
			CommonModelRegistryKey("Role", "WietbotDataAuth", "Wietbot", "1.0") to RoleSerializer,
			CommonModelRegistryKey("Permission", "WietbotDataAuth", "Wietbot", "1.0") to PermissionSerializer,
			CommonModelRegistryKey("Platform", "WietbotDataAuth", "Wietbot", "1.0") to PlatformSerializer,
		)
	
	override fun initialize(registry: ModelRegistry?) =
		modelSerializers.forEach((registry ?: BitBlockBase.modelRegistry)::set)
}
// @formatter:on
