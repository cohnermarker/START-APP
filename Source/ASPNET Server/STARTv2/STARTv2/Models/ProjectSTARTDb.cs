using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace STARTv2.Models
{
    public class ProjectSTARTDb : DbContext
    {
        public ProjectSTARTDb() : base("name=MYSQL") { }

        public DbSet<Distress> Distress { get; set; }

        public DbSet<UserInfo> Userinfo { get; set; }

        public DbSet<login> Login { get; set; }
    }
}