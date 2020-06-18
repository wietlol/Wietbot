using System;
using System.Threading.Tasks;
using Amazon.Lambda.Core;
using Core.Api.Models;
using Core.Services;
using CSharpUtils.Utility.Extenders;
using JsonSerializer = Amazon.Lambda.Serialization.Json.JsonSerializer;

namespace Core.Api
{
	public class EvalCSharpHandler
	{
		private ScriptRunner ScriptRunner { get; } = new ScriptRunner();
		private Boolean HasInitialized { get; set; } = false;
		
		[LambdaSerializer(typeof(JsonSerializer))]
		public async Task<EvalResponse?> EvalCSharpPrivate(EvalRequest request)
		{
			if (HasInitialized.Not())
			{
				await ScriptRunner.RunCode("0");
				HasInitialized = true;
			}
			
			if (request.Code == null)
				return null;

			String responseBody = await ScriptRunner.RunCode(request.Code);

			return new EvalResponse
			{
				Result = responseBody,
			};
		}
	}
}