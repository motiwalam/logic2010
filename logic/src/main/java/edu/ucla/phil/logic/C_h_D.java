package edu.ucla.phil.logic;

import java.util.Vector;

class C_h_D {
   Vector f1160 = new Vector();
   Vector f1161 = new Vector();

   void m1839(C_RF c_rf) {
      if (c_rf instanceof C_q_A) {
         C_a_C c_a_c = new C_a_C(c_rf.m1214(), c_rf.m1216());
         if (!this.f1160.contains(c_a_c)) {
            this.f1160.addElement(c_a_c);
         }
      } else if (c_rf instanceof C_n_C) {
         C_g_E c_g_e = new C_g_E(c_rf.m1214(), c_rf.m1216());
         if (!this.f1161.contains(c_g_e)) {
            this.f1161.addElement(c_g_e);
         }
      }

      int j = c_rf == null ? 0 : c_rf.m1216();

      for (int i = 0; i < j; i++) {
         this.m1839(c_rf.m1217(i));
      }
   }

   void m1840(LPInvalidation lpinvalidation) {
      int i = lpinvalidation.symbols == null ? 0 : lpinvalidation.symbols.size();

      for (int j = 0; j < i; j++) {
         C_IE c_ie = (C_IE)lpinvalidation.symbols.elementAt(j);
         if (c_ie instanceof C_a_C) {
            int k = this.f1160.indexOf(c_ie);
            if (k != -1) {
               this.f1160.setElementAt(c_ie, k);
            }
         } else if (c_ie instanceof C_g_E) {
            int l = this.f1161.indexOf(c_ie);
            if (l != -1) {
               this.f1161.setElementAt(c_ie, l);
            }
         }
      }
   }

   Vector m1841() {
      Vector vector = new Vector();
      int j = this.f1160.size();

      for (int i = 0; i < j; i++) {
         vector.addElement(this.f1160.elementAt(i));
      }

      j = this.f1161.size();

      for (int k = 0; k < j; k++) {
         vector.addElement(this.f1161.elementAt(k));
      }

      return vector;
   }
}
