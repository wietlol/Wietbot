package me.wietlol.wietbot.data.auth.core.repository.models

interface Authority
{
	var policy: Policy
	var permission: Permission
	var resource: String
}
