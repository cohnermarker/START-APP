using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(STARTv2.Startup))]
namespace STARTv2
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
