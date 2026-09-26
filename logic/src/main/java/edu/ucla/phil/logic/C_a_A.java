package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Vector;

class C_a_A implements Enumeration {
   int f963;
   Vector f964 = null;

   C_a_A(C_e_D c_e_d) {
      this.m1635();
      if (c_e_d != null) {
         synchronized (c_e_d) {
            int i = c_e_d.size();
            if (i != 0) {
               this.f964 = new Vector();

               for (int j = 0; j < i; j++) {
                  this.f964.addElement(c_e_d.m1778(j));
               }
            }
         }
      }
   }

   C_a_A(String s) {
      this.m1635();
      this.f964 = new Vector();
      this.f964.addElement(s);
   }

   void m1634(C_e_D c_e_d) {
      if (this.f964 != null) {
         if (c_e_d == null) {
            this.f964.removeAllElements();
         }

         int i = 0;

         while (i < this.f964.size()) {
            if (c_e_d.m1780(C_XD.m1493((String)this.f964.elementAt(i))) == null) {
               this.f964.removeElementAt(i);
            } else {
               i++;
            }
         }
      }
   }

   @Override
   public boolean hasMoreElements() {
      return this.f964 != null && this.f963 < this.f964.size();
   }

   @Override
   public Object nextElement() {
      return this.f964 != null && this.f963 < this.f964.size() ? this.f964.elementAt(this.f963++) : null;
   }

   void m1635() {
      this.f963 = 0;
   }
}
