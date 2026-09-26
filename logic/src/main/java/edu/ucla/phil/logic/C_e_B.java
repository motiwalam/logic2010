package edu.ucla.phil.logic;

import java.util.Vector;

class C_e_B {
   String f1069;
   Vector f1070;

   C_e_B(String s, Vector vector) {
      this.f1069 = s;
      this.f1070 = vector;
   }

   C_e_B(String s) {
      this(s, null);
   }

   C_e_B() {
      this(null, null);
   }

   int m1757() {
      return this.f1069 == null ? 0 : this.f1069.length();
   }

   int m1758() {
      return this.f1070 == null ? 0 : this.f1070.size();
   }

   C_e_B m1759() {
      if (this.f1070 == null) {
         return new C_e_B(this.f1069, null);
      } else {
         C_e_B c_e_b1 = new C_e_B(this.f1069, new Vector());
         int i = this.m1758();

         for (int j = 0; j < i; j++) {
            C_n_F c_n_f = (C_n_F)this.f1070.elementAt(j);
            c_e_b1.f1070.addElement(c_n_f.m1974(true));
         }

         return c_e_b1;
      }
   }

   C_e_B m1760(C_e_B c_e_b1) {
      int i = this.m1757();
      int j = this.m1758();
      int k = c_e_b1.m1758();
      if (k > j) {
         if (this.f1070 == null) {
            this.f1070 = new Vector();
         }

         this.f1070.setSize(k);
      }

      for (int l = 0; l < k; l++) {
         C_n_F c_n_f = (C_n_F)this.f1070.elementAt(l);
         C_n_F c_n_f1 = (C_n_F)c_e_b1.f1070.elementAt(l);
         if (c_n_f1 != null) {
            c_n_f1 = c_n_f1.m1974(true).m1981(i);
            if (c_n_f == null) {
               this.f1070.setElementAt(c_n_f1, l);
            } else {
               c_n_f.m1975(c_n_f1);
            }
         }
      }

      return this.m1761(c_e_b1.f1069);
   }

   C_e_B m1761(String s) {
      if (this.f1069 == null) {
         this.f1069 = s;
      } else if (s != null) {
         this.f1069 = this.f1069 + s;
      }

      return this;
   }

   C_e_B m1762(int i, int j) {
      int k = this.m1757();
      if (i > k) {
         i = k;
      }

      if (j > k) {
         j = k;
      }

      C_e_B c_e_b1 = new C_e_B();
      if (this.f1069 != null) {
         c_e_b1.f1069 = this.f1069.substring(i, j);
      }

      if (this.f1070 != null) {
         C_n_F c_n_f = C_n_F.m1971(i, j - i);
         int l = this.m1758();
         c_e_b1.f1070 = new Vector();

         for (int i1 = 0; i1 < l; i1++) {
            C_n_F c_n_f1 = (C_n_F)this.f1070.elementAt(i1);
            c_e_b1.f1070.addElement(c_n_f1 == null ? null : c_n_f1.m1977(c_n_f).m1981(-i));
         }
      }

      return c_e_b1;
   }

   C_e_B m1763(int i) {
      return this.m1762(i, this.m1757());
   }

   boolean m1764() {
      int i = this.m1758();

      for (int j = 0; j < i; j++) {
         C_n_F c_n_f = (C_n_F)this.f1070.elementAt(j);
         if (c_n_f != null && !c_n_f.m1982()) {
            return true;
         }
      }

      return false;
   }

   boolean m1765(C_e_B c_e_b1) {
      int i = Math.min(this.m1758(), c_e_b1.m1758());

      for (int j = 0; j < i; j++) {
         C_n_F c_n_f = (C_n_F)this.f1070.elementAt(j);
         C_n_F c_n_f1 = (C_n_F)c_e_b1.f1070.elementAt(j);
         if (c_n_f != null && !c_n_f.m1982() && c_n_f1 != null && !c_n_f1.m1982()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_e_B)) {
         return false;
      } else {
         C_e_B c_e_b3 = (C_e_B)object;
         if (this.m1757() == 0 ? c_e_b3.m1757() == 0 : this.f1069.equals(c_e_b3.f1069)) {
            C_e_B c_e_b1;
            C_e_B c_e_b2;
            if (this.m1758() < c_e_b3.m1758()) {
               c_e_b1 = this;
               c_e_b2 = c_e_b3;
            } else {
               c_e_b1 = c_e_b3;
               c_e_b2 = this;
            }

            int j = c_e_b1.m1758();
            int k = c_e_b2.m1758();

            for (int i = 0; i < j; i++) {
               C_n_F c_n_f = (C_n_F)c_e_b1.f1070.elementAt(i);
               C_n_F c_n_f1 = (C_n_F)c_e_b2.f1070.elementAt(i);
               boolean flag = c_n_f == null || c_n_f.m1982();
               boolean flag1 = c_n_f1 == null || c_n_f1.m1982();
               if ((!flag || !flag1) && (flag || flag1 || !c_n_f.equals(c_n_f1))) {
                  return false;
               }
            }

            for (int l = j; l < k; l++) {
               C_n_F c_n_f2 = (C_n_F)c_e_b2.f1070.elementAt(l);
               if (c_n_f2 != null && !c_n_f2.m1982()) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = this.m1757() == 0 ? 0 : this.f1069.hashCode();
      int j = this.m1758();

      for (int k = 0; k < j; k++) {
         i *= 40701;
         C_n_F c_n_f = (C_n_F)this.f1070.elementAt(k);
         if (c_n_f != null && !c_n_f.m1982()) {
            i += c_n_f.hashCode();
         }
      }

      return i;
   }
}
