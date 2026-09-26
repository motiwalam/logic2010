package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class NetworkTask {
   InputStream f1344 = null;
   OutputStream f1345 = null;
   C_o_ f1346 = null;
   String f1347;
   String f1348;
   ProgressDialog f1349 = null;
   long f1350 = 1L;
   long f1351 = 15000L;
   boolean f1352;

   NetworkTask(String s, String s1, long i) {
      this.f1347 = s;
      this.f1348 = s1;
      this.f1350 = i;
   }

   NetworkTask(String s, String s1) {
      this(s, s1, 1L);
   }

   void m2051(C_o_ c_o_) {
      this.f1346 = c_o_;
      if (Thread.currentThread() == c_o_) {
         c_o_.m1989();
      } else {
         this.f1352 = false;
         this.f1349 = new ProgressDialog(this.f1347, this.f1348, new String[]{"Abort"}, 0);
         c_o_.start();
         if (this.f1350 == 1L) {
            this.f1350 = 10000L;
         }

         try {
            c_o_.join(this.f1350);
         } catch (InterruptedException interruptedexception1) {
         }

         if (c_o_.isAlive()) {
            this.f1349.m1284(20, 10);
            this.m2060();

            try {
               c_o_.join(this.f1351);
            } catch (InterruptedException interruptedexception) {
            }

            if (c_o_.isAlive()) {
               c_o_.stop();
            }
         } else {
            this.f1349.dispose();
            this.m2060();
         }
      }
   }

   Object m2052() {
      return this.f1346.m1990();
   }

   int m2053() {
      return this.f1349 == null ? -1 : this.f1349.f766;
   }

   void m2054() {
      if (this.f1349 != null) {
         this.f1349.dispose();
      }
   }

   synchronized void m2055(String s) {
      if (this.f1349 != null) {
         this.f1349.m1289(s);
      }
   }

   synchronized InputStream m2056() {
      return this.f1344;
   }

   synchronized void m2057(InputStream inputstream) throws IOException {
      if (this.f1352) {
         throw new IOException("input stream set too late");
      } else {
         this.f1344 = inputstream;
      }
   }

   synchronized OutputStream m2058() {
      return this.f1345;
   }

   synchronized void m2059(OutputStream outputstream) throws IOException {
      if (this.f1352) {
         throw new IOException("output stream set too late");
      } else {
         this.f1345 = outputstream;
      }
   }

   synchronized void m2060() {
      if (this.f1344 != null) {
         try {
            this.f1344.close();
         } catch (IOException ioexception1) {
         }
      }

      this.f1344 = null;
      if (this.f1345 != null) {
         try {
            this.f1345.close();
         } catch (IOException ioexception) {
         }
      }

      this.f1345 = null;
      this.f1352 = true;
   }
}
