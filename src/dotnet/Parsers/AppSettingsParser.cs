using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using Opsbridge.R18A6.Models;

namespace Opsbridge.R18A6.Parsers
{
    public static class AppSettingsParser
    {
        public static List<ConfigKey> ParseAppSettings(string jsonPath)
        {
            var keys = new List<ConfigKey>();
            try
            {
                var txt = File.ReadAllText(jsonPath);
                using var doc = JsonDocument.Parse(txt);
                Walk(doc.RootElement, "", keys);
            }
            catch (Exception)
            {
                // ignore
            }
            return keys;
        }

        private static void Walk(JsonElement el, string prefix, List<ConfigKey> keys)
        {
            if (el.ValueKind == JsonValueKind.Object)
            {
                foreach (var prop in el.EnumerateObject())
                {
                    var key = string.IsNullOrEmpty(prefix) ? prop.Name : prefix + "." + prop.Name;
                    if (prop.Value.ValueKind == JsonValueKind.Object)
                    {
                        Walk(prop.Value, key, keys);
                    }
                    else
                    {
                        keys.Add(new ConfigKey(key));
                    }
                }
            }
        }
    }
}
