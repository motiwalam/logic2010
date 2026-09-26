package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class C_z_B extends Hashtable {
   C_z_ f1468;
   Vector f1469;
   Hashtable f1470;
   Hashtable f1471;
   C_r_D f1472;

   C_z_B(C_z_ c_z_) {
      this.f1468 = c_z_;
      this.f1469 = new Vector();
      this.f1470 = new Hashtable();
      this.f1471 = new Hashtable();
      this.f1472 = new C_r_D();
   }

   void m2200(String s, Vector vector) {
      int i = s.length();
      int j = 0;

      while (j < i && Character.isWhitespace(s.charAt(j))) {
         j++;
      }

      int k = j;

      while (k < i && !Character.isWhitespace(s.charAt(k))) {
         k++;
      }

      String s1 = s.substring(j, k);
      String s2 = s.substring(k);
      if (this.m2203(s1) != null) {
         System.out.println("redefinition of rule " + s1);
      } else {
         Object object;
         if (s2.indexOf(".:") == -1) {
            object = new C_VB(s1, s2, this, this.f1468);
         } else {
            object = new C_LF(s1, s2);
         }

         String s3 = ((C_VB)object).m1381();
         if (s3 != null) {
            System.out.println("error in rule " + s1 + ": " + s3);
         } else {
            this.f1472.m2068((C_VB)object);
            if (vector != null) {
               this.f1471.put(s1, vector);
            }

            this.m2202((C_VB)object);
         }
      }
   }

   static C_z_B m2201(Reader reader, C_z_ c_z_) {
      if (reader == null) {
         return null;
      } else {
         C_XB c_xb;
         if (reader instanceof C_XB) {
            c_xb = (C_XB)reader;
         } else {
            c_xb = new C_XB(reader, LogicProgram.f537);
         }

         C_z_B c_z_b = new C_z_B(c_z_);

         try {
            Vector vector = null;

            String s;
            while ((s = c_xb.readLine()) != null) {
               if (C_XD.m1511(s)) {
                  if (s.indexOf("#-") == 0) {
                     if (vector == null) {
                        vector = new Vector();
                     }

                     vector.addElement(s.substring(2));
                  }
               } else {
                  c_z_b.m2200(s, vector);
                  vector = null;
               }
            }
         } catch (IOException ioexception1) {
            c_z_b = null;
         } finally {
            try {
               c_xb.close();
            } catch (IOException ioexception) {
            }
         }

         return c_z_b;
      }
   }

   void m2202(C_VB c_vb) {
      this.put(c_vb.f820.toUpperCase(), c_vb);
      this.f1469.addElement(c_vb.f820);
   }

   C_VB m2203(String s) {
      return (C_VB)this.get(s.toUpperCase());
   }

   C_QE m2204(Integer integer) {
      return this.f1468 == null ? null : this.f1468.m2199(integer);
   }

   C_VB m2205(String s) {
      Integer integer = C_VB.m1366(s);
      if (integer != null) {
         return this.m2204(integer);
      } else {
         integer = C_VB.m1367(s);
         if (integer != null) {
            synchronized (this.f1470) {
               C_VB c_vb = (C_VB)this.f1470.get(integer);
               if (c_vb == null) {
                  c_vb = C_VB.m1377(this.m2204(integer));
                  if (c_vb == null) {
                     return null;
                  }

                  this.f1470.put(integer, c_vb);
               }

               return c_vb.m1368(s);
            }
         } else {
            return this.m2203(s);
         }
      }
   }

   void m2206(C_VB c_vb) {
      this.remove(c_vb.f820.toUpperCase());
      this.f1469.removeElement(c_vb.f820);
   }
}
