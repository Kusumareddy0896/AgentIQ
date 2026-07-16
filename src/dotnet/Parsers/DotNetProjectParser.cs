using System;
using System.Collections.Generic;
using System.IO;
using System.Xml;
using Opsbridge.R18A6.Models;

namespace Opsbridge.R18A6.Parsers
{
    public static class DotNetProjectParser
    {
        public static List<DependencyInfo> ParseProjectFile(string csprojPath)
        {
            var deps = new List<DependencyInfo>();
            try
            {
                var doc = new XmlDocument();
                doc.Load(csprojPath);
                var nsmgr = new XmlNamespaceManager(doc.NameTable);
                nsmgr.AddNamespace("ms", doc.DocumentElement.NamespaceURI);
                var nodes = doc.SelectNodes("//PackageReference");
                foreach (XmlNode node in nodes)
                {
                    var include = node.Attributes? ["Include"]?.Value;
                    var version = node.Attributes? ["Version"]?.Value;
                    if (include != null)
                    {
                        deps.Add(new DependencyInfo(include, null, version));
                    }
                }
            }
            catch (Exception)
            {
                // ignore
            }
            return deps;
        }
    }
}
