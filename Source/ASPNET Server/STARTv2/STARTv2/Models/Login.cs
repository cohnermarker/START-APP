namespace STARTv2.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("projectstart.login")]
    public partial class login
    {
        public int id { get; set; }

        [Required]
        [StringLength(60)]
        public string username { get; set; }

        [Required]
        [StringLength(64)]
        public string password { get; set; }
    }
}
