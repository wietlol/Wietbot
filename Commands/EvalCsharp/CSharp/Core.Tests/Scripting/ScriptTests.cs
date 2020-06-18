using System.Threading.Tasks;
using Core.Api;
using Core.Api.Models;
using Xunit;
using Xunit.Abstractions;

namespace Core.Tests.Scripting
{
	public class ScriptTests
	{
		private readonly ITestOutputHelper _testOutputHelper;

		public ScriptTests(ITestOutputHelper testOutputHelper)
		{
			_testOutputHelper = testOutputHelper;
		}

		[Fact]
		public async Task AssertThat_HelloWorld_IsEvaluated()
		{
			var service = new EvalCSharpHandler();
			
			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "\"Hello, World!\""});

			Assert.Equal("Hello, World!", response.Result);
		}

		[Fact]
		public async Task AssertThat_ConsoleLogging_IsEvaluated()
		{
			var service = new EvalCSharpHandler();

			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "Console.WriteLine(\"Hello, World!\")"});

			Assert.Equal("Result: null\r\nOutput:\r\nHello, World!\r\n", response.Result);
		}

		[Fact]
		public async Task AssertThat_ImportsWork()
		{
			var service = new EvalCSharpHandler();

			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "Int32.MaxValue"});

			Assert.Equal("2147483647", response.Result);
		}

		[Fact]
		public async Task AssertThat_LinqImportsWork()
		{
			var service = new EvalCSharpHandler();

			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "new [] { 0, 1, 2, 3, 4, }.Where(it => it < 3).Select(it => it.ToString()).ToList().Aggregate((l, r) => l + \", \" + r)"});

			Assert.Equal("0, 1, 2", response.Result);
		}

		[Fact]
		public async Task AssertThat_ClassDeclarations_Work()
		{
			var service = new EvalCSharpHandler();

			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "public class Test { public String Foo => \"Hullo!\"; } new Test().Foo"});

			Assert.Equal("Hullo!", response.Result);
		}

		[Fact]
		public async Task AssertThat_CSharpUtils_Works()
		{
			var service = new EvalCSharpHandler();
			
			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "Optional.OfNullable(\"text\")"});

			Assert.Equal("Optional(text)", response.Result);
		}

		[Fact]
		public async Task AssertThat_CSharpUtilsExtenders_Work()
		{
			var service = new EvalCSharpHandler();
			
			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "new List<String>().NotContains(\"empty\")"});

			Assert.Equal("True", response.Result);
		}

		[Fact]
		public async Task AssertThat_CSharpUtilsGlobals_Work()
		{
			var service = new EvalCSharpHandler();

			EvalResponse response = await service.EvalCSharpPrivate(new EvalRequest {Code = "ListOf(1, 2, 3)"});

			Assert.Equal("System.Collections.Generic.List`1[System.Int32]", response.Result);
		}
	}
}