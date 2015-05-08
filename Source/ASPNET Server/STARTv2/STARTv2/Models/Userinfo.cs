namespace STARTv2.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("projectstart.UserInfo")]
    public partial class UserInfo
    {
        public int id { get; set; }

        [StringLength(20)]
        public string user { get; set; }

        [StringLength(10)]
        public string Type { get; set; }

        [StringLength(20)]
        public string Email { get; set; }

        [StringLength(20)]
        public string CellPhone { get; set; }

        [StringLength(20)]
        public string Location { get; set; }

        public int? FloorNum { get; set; }
    }
}
