package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

class C_z_ extends Hashtable {
   C_n_F f1464 = new C_n_F();
   Hashtable f1465 = new Hashtable();

   void m2197(String s, Vector vector) {
      int i = s.length();
      int j = 0;

      while (j < i && Character.isWhitespace(s.charAt(j))) {
         j++;
      }

      int k = j;

      while (k < i && !Character.isWhitespace(s.charAt(k))) {
         k++;
      }

      Integer integer;
      try {
         integer = Integer.valueOf(s.substring(j, k));
      } catch (NumberFormatException numberformatexception) {
         System.out.println("Bad theorem number format: " + s.substring(j, k));
         return;
      }

      C_QE c_qe = new C_QE(integer, s.substring(k));
      String s1 = c_qe.m1381();
      if (s1 != null) {
         System.out.println("error in theorem T" + integer + ": " + s1);
      } else {
         if (vector != null) {
            this.f1465.put(integer, vector);
         }

         this.f1464.m1975(C_n_F.m1970(integer));
         this.put(integer, c_qe);
      }
   }

   static C_z_ m2198(Reader reader) {
      if (reader == null) {
         return null;
      } else {
         C_XB c_xb;
         if (reader instanceof C_XB) {
            c_xb = (C_XB)reader;
         } else {
            c_xb = new C_XB(reader, LogicProgram.f537);
         }

         C_z_ c_z_ = new C_z_();

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
                  c_z_.m2197(s, vector);
                  vector = null;
               }
            }
         } catch (IOException ioexception1) {
            c_z_ = null;
         } finally {
            try {
               c_xb.close();
            } catch (IOException ioexception) {
            }
         }

         return c_z_;
      }
   }

   C_QE m2199(Integer integer) {
      return integer == null ? null : (C_QE)this.get(integer);
   }
}
