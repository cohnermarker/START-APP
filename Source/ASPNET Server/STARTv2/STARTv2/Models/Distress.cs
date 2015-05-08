namespace STARTv2.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("projectstart.Distress")]
    public partial class Distress
    {
        public int id { get; set; }

        [StringLength(5)]
        public string safe { get; set; }

        [StringLength(20)]
        public string status { get; set; }

        [StringLength(20)]
        public string nature { get; set; }

        [StringLength(50)]
        public string gps { get; set; }

        [StringLength(20)]
        public string user { get; set; }

        [StringLength(20)]
        public string cellphone { get; set; }

        [StringLength(20)]
        public string timerec { get; set; }

        [StringLength(20)]
        public string timeres { get; set; }
    }
}
