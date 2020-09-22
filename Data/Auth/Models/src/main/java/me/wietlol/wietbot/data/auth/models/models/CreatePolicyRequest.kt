// hash: #d3e5be1d
// data: serializationKey:c0323270-bfcc-4522-a528-ce66b4ba2edd
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import java.util.UUID
import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable
import me.wietlol.utils.common.emptyHashCode
import me.wietlol.utils.common.toJsonString
import me.wietlol.utils.common.with

// @formatter:on
// @tomplot:customCode:start:gAeCSq
// @tomplot:customCode:end
// @formatter:off


interface CreatePolicyRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("c0323270-bfcc-4522-a528-ce66b4ba2edd")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is CreatePolicyRequest) return false
		
		if (name != other.name) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()}}"""
	
	fun duplicate(): CreatePolicyRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
