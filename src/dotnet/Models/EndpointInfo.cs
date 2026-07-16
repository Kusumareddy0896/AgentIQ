namespace Opsbridge.R18A6.Models
{
    public class EndpointInfo
    {
        public string Controller { get; set; }
        public string Path { get; set; }
        public string HttpMethod { get; set; }

        public EndpointInfo() { }
        public EndpointInfo(string controller, string path, string method)
        {
            Controller = controller;
            Path = path;
            HttpMethod = method;
        }
    }
}
