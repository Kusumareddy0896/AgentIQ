using System;
using System.Collections.Generic;
using System.IO;
using Opsbridge.R18A6.Models;
using Opsbridge.R18A6.Parsers;
using Opsbridge.R18A6.Scanner;

namespace Opsbridge.R18A6.Parsers
{
    public class DotNetApplicationParser
    {
        public ApplicationStructure Parse(string rootPath)
        {
            var structure = new ApplicationStructure();
            var scanner = new RepositoryScanner();
            var files = scanner.Scan(rootPath);
            foreach (var f in files)
            {
                var name = Path.GetFileName(f).ToLowerInvariant();
                if (name.EndsWith(".csproj"))
                {
                    var deps = DotNetProjectParser.ParseProjectFile(f);
                    deps.ForEach(d => structure.Dependencies.Add(d));
                }
                else if (name.EndsWith("appsettings.json"))
                {
                    var keys = AppSettingsParser.ParseAppSettings(f);
                    keys.ForEach(k => structure.ConfigKeys.Add(k));
                }
                else if (name.EndsWith(".cs"))
                {
                    var endpoints = AspNetControllerParser.ParseFile(f);
                    endpoints.ForEach(e => structure.Endpoints.Add(e));
                }
            }
            return structure;
        }
    }
}
