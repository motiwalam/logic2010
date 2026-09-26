package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class C_UD extends C_GE {
   Socket f809 = null;
   Socket f810 = null;

   public C_UD() {
      this(null);
   }

   public C_UD(PrintWriter printwriter) {
      super(printwriter);
   }

   boolean m1351(String s) {
      this.m1354();
      if (this.f810 == null) {
         try {
            this.m651(this.f810 = new Socket(s, 25));
         } catch (IOException ioexception) {
            this.f810 = null;
            return false;
         }
      }

      return true;
   }

   boolean m1352(String s) {
      this.m1353();
      if (this.f809 == null) {
         try {
            this.m651(this.f809 = new Socket(s, 110));
         } catch (IOException ioexception) {
            this.f809 = null;
            return false;
         }
      }

      return true;
   }

   public void m1353() {
      if (this.f810 != null) {
         try {
            this.f810.close();
         } catch (IOException ioexception) {
         }

         this.f810 = null;
      }
   }

   public void m1354() {
      if (this.f809 != null) {
         try {
            this.f809.close();
         } catch (IOException ioexception) {
         }

         this.f809 = null;
      }
   }

   public void m1355() {
      this.m1353();
      this.m1354();
   }
}
