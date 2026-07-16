using Microsoft.AspNetCore.Mvc;

namespace Opsbridge.R18A6.Sample.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class SampleController : ControllerBase
    {
        [HttpGet]
        public IActionResult Get() => Ok(new { message = "hello" });

        [HttpGet("{id}")]
        public IActionResult GetById(int id) => Ok(new { id });

        [HttpPost]
        public IActionResult Post([FromBody] object payload) => Created("", payload);

        [HttpPut("{id}")]
        public IActionResult Put(int id, [FromBody] object payload) => NoContent();

        [HttpDelete("{id}")]
        public IActionResult Delete(int id) => NoContent();
    }
}
