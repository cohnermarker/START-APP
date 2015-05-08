using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using STARTv2.Models;

namespace STARTv2.Controllers
{
    [Authorize (Roles = "Admin, User")]
    public class DistressesController : Controller
    {
        private ProjectSTARTDb db = new ProjectSTARTDb();

        // GET: Distresses
        public ActionResult Index()
        {
            return View(db.Distress.ToList());
        }

        // GET: Distresses/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Distress distress = db.Distress.Find(id);
            if (distress == null)
            {
                return HttpNotFound();
            }
            return View(distress);
        }

        // GET: Distresses/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Distresses/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id,safe,status,nature,gps,user,cellphone,timerec,timeres")] Distress distress)
        {
            if (ModelState.IsValid)
            {
                db.Distress.Add(distress);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(distress);
        }

        // GET: Distresses/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Distress distress = db.Distress.Find(id);
            if (distress == null)
            {
                return HttpNotFound();
            }
            return View(distress);
        }

        // POST: Distresses/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,safe,status,nature,gps,user,cellphone,timerec,timeres")] Distress distress)
        {
            if (ModelState.IsValid)
            {
                db.Entry(distress).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(distress);
        }

        // GET: Distresses/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Distress distress = db.Distress.Find(id);
            if (distress == null)
            {
                return HttpNotFound();
            }
            return View(distress);
        }

        // POST: Distresses/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Distress distress = db.Distress.Find(id);
            db.Distress.Remove(distress);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
