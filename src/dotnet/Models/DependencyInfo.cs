namespace Opsbridge.R18A6.Models
{
    public class DependencyInfo
    {
        public string PackageId { get; set; }
        public string Version { get; set; }

        public DependencyInfo() { }
        public DependencyInfo(string id, string version)
        {
            PackageId = id;
            Version = version;
        }
    }
}
