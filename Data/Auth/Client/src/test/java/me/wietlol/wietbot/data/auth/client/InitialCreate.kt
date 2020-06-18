package me.wietlol.wietbot.data.auth.client

import com.amazonaws.regions.Regions.EU_WEST_1
import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityRequest

object InitialCreate
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val lambdaClient = AWSLambdaClientBuilder.standard()
			.withRegion(EU_WEST_1)
			.build()
		val authClient: AuthService = AuthClient(lambdaClient)
		
		with(authClient) {
			
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"evalNode"
//			))
//
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"join"
//			))
//
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"leave"
//			))
//
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"listCommands"
//			))
//
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"admin",
//				"command-execute",
//				"shutdown"
//			))

//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"evalCSharp"
//			))

			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
				"pleb",
				"command-execute",
				"evalSql"
			))
			
			
//			createPermission(CreatePermissionRequest.of("command-execute"))
			
//			createPolicy(CreatePolicyRequest.of("admin"))
//			createPolicy(CreatePolicyRequest.of("moderator"))
//			createPolicy(CreatePolicyRequest.of("pleb"))
			
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"admin",
//				"command-execute",
//				"*"
//			))
//
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"moderator",
//				"command-execute",
//				"update-and-restart"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"moderator",
//				"command-execute",
//				"shutdown"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"moderator",
//				"command-execute",
//				"ban"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"moderator",
//				"command-execute",
//				"eval-groovy"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"moderator",
//				"command-execute",
//				"eval-js"
//			))
//
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"version"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"info"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"join-room"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"leave-room"
//			))
//			createGrantedAuthority(CreateGrantedAuthorityRequest.of(
//				"pleb",
//				"command-execute",
//				"fun-friday"
//			))
//
//			createRole(CreateRoleRequest.of("admin"))
//			createRole(CreateRoleRequest.of("moderator"))
//			createRole(CreateRoleRequest.of("pleb"))
//			createRole(CreateRoleRequest.of("prisoner"))
//
//			attachRolePolicy(AttachRolePolicyRequest.of("admin", "admin"))
//			attachRolePolicy(AttachRolePolicyRequest.of("admin", "moderator"))
//			attachRolePolicy(AttachRolePolicyRequest.of("admin", "pleb"))
//
//			attachRolePolicy(AttachRolePolicyRequest.of("moderator", "moderator"))
//			attachRolePolicy(AttachRolePolicyRequest.of("moderator", "pleb"))
//
//			attachRolePolicy(AttachRolePolicyRequest.of("pleb", "pleb"))
//
//			getOrCreateUser(GetOrCreateUserRequest.of(
//				User.of(
//					2764866,
//					"Wietlol"
//				)
//			))
//
//			setUserRole(SetUserRoleRequest.of(2764866, "admin"))
		}
	}
}
