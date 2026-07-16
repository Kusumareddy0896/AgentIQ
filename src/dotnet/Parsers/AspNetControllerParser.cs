using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;
using Opsbridge.R18A6.Models;

namespace Opsbridge.R18A6.Parsers
{
    public static class AspNetControllerParser
    {
        private static Regex ClassRegex = new Regex("class\\s+(\\w+)", RegexOptions.Compiled);
        private static Regex RouteRegex = new Regex("\\[Route\\(\\\"([^\\\"]*)\\\")\\]", RegexOptions.Compiled);
        private static Regex HttpMeth = new Regex("\\[(HttpGet|HttpPost|HttpPut|HttpDelete)(?:\\(\\\"([^\\\"]*)\\\"\\))?\\]", RegexOptions.Compiled);

        public static List<EndpointInfo> ParseFile(string csPath)
        {
            var endpoints = new List<EndpointInfo>();
            try
            {
                var src = File.ReadAllText(csPath);
                if (!src.Contains("[ApiController]") && !src.Contains("Controller")) return endpoints;

                var m = ClassRegex.Match(src);
                var className = m.Success ? m.Groups[1].Value : "UnknownController";

                var classRoute = "";
                var rm = RouteRegex.Match(src);
                if (rm.Success) classRoute = rm.Groups[1].Value;

                var mm = HttpMeth.Matches(src);
                foreach (Match hit in mm)
                {
                    var attr = hit.Groups[1].Value;
                    var path = hit.Groups[2].Success ? hit.Groups[2].Value : "";
                    var http = "GET";
                    if (attr == "HttpPost") http = "POST";
                    else if (attr == "HttpPut") http = "PUT";
                    else if (attr == "HttpDelete") http = "DELETE";
                    var full = (classRoute + path).Trim();
                    if (string.IsNullOrEmpty(full)) full = "/";
                    endpoints.Add(new EndpointInfo(className, full, http));
                }
            }
            catch (Exception)
            {
                // ignore
            }
            return endpoints;
        }
    }
}
