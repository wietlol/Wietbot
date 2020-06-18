using System;
using System.Collections.Immutable;
using System.Collections.Specialized;
using System.Dynamic;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Xml;
using System.Xml.Linq;
using CSharpUtils.Containers;
using Microsoft.CodeAnalysis.CSharp.Scripting;
using Microsoft.CodeAnalysis.Scripting;
using Newtonsoft.Json;

namespace Core.Services
{
	public class ScriptRunner
	{
		private ScriptOptions Options { get; } = ScriptOptions.Default
			.WithImports(
				"System",
				"System.Collections.Concurrent",
				"System.Collections.Generic",
				"System.Collections.Immutable",
				"System.Collections.Specialized",
				"System.Diagnostics",
				"System.Dynamic",
				"System.Globalization",
				"System.IO",
				"System.Linq",
				"System.Reflection",
				"System.Text",
				"System.Text.RegularExpressions",
				"System.Threading",
				"System.Threading.Tasks",
				"System.Xml",
				"System.Xml.Linq",
				"System.Xml.Serialization",
				"System.Xml.XPath",
				"Newtonsoft.Json",
				"CSharpUtils.Containers",
				"CSharpUtils.Utility.Extenders",
				"CSharpUtils.Utility.Globals")
			.WithReferences(
				typeof(ImmutableArray).Assembly,
				typeof(StringCollection).Assembly,
				typeof(ExpandoObject).Assembly,
				typeof(Regex).Assembly,
				typeof(XmlDocument).Assembly,
				typeof(XDocument).Assembly,
				typeof(Enumerable).Assembly,
				typeof(JsonConvert).Assembly,
				typeof(IOptional<>).Assembly
			);

		public async Task<String> RunCode(String code)
		{
			using TextWriter console = new StringWriter();

			Object? result;
			try
			{
				var globals = new Globals(console);
				ScriptState<Object?> scriptState = await CSharpScript.RunAsync(code, Options, globals);
				result = scriptState.ReturnValue;
			}
			catch (Exception ex)
			{
				result = $"{ex.GetType().FullName}({ex.Message})";
			}

			String resultString = result?.ToString() ?? "null";

			String stdOut = console.ToString();

			return String.IsNullOrWhiteSpace(stdOut)
				? String.IsNullOrWhiteSpace(resultString)
					? "<empty result>"
					: resultString
				: $"Result: {resultString}\r\nOutput:\r\n{stdOut}";
		}
	}

	public class Globals
	{
		public TextWriter Console { get; }

		public Globals(TextWriter console)
		{
			Console = console;
		}
	}
}