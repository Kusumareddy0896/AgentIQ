using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Opsbridge.R18A6.Scanner
{
    public class RepositoryScanner
    {
        public List<string> Scan(string rootPath)
        {
            var results = new List<string>();
            try
            {
                var files = Directory.EnumerateFiles(rootPath, "*.*", SearchOption.AllDirectories);
                foreach (var f in files)
                {
                    var n = Path.GetFileName(f).ToLowerInvariant();
                    if (n.EndsWith(".cs") || n.EndsWith(".csproj") || n == "appsettings.json")
                    {
                        results.Add(f);
                    }
                }
            }
            catch (Exception)
            {
                // ignore
            }
            return results;
        }
    }
}
