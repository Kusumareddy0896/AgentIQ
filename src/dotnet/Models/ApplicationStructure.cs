using System.Collections.Generic;

namespace Opsbridge.R18A6.Models
{
    public class ApplicationStructure
    {
        public List<DependencyInfo> Dependencies { get; } = new List<DependencyInfo>();
        public List<EndpointInfo> Endpoints { get; } = new List<EndpointInfo>();
        public List<ConfigKey> ConfigKeys { get; } = new List<ConfigKey>();
    }
}
