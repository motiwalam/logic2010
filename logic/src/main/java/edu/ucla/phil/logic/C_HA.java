package edu.ucla.phil.logic;

import java.util.Vector;

class C_HA {
   C_RF f377;
   C_VC f378;
   Vector f379;
   boolean[] f380;

   C_HA(C_RF c_rf) {
      this.f378 = null;
      this.f379 = new Vector();
      this.m672(this.f377 = c_rf);
      this.m675();
   }

   C_HA(C_VC c_vc) {
      this.f377 = null;
      this.f378 = c_vc;
      this.f379 = new Vector();
      int i = c_vc.f827.length;

      for (int j = 0; j < i; j++) {
         this.m672(c_vc.f827[j]);
      }

      this.m672(c_vc.f828);
      this.m675();
   }

   void m672(C_RF c_rf) {
      if (c_rf instanceof C_q_F) {
         int i = c_rf.m1216();

         for (int j = 0; j < i; j++) {
            this.m672(c_rf.m1217(j));
         }
      } else if (c_rf != null && this.m678(c_rf) == -1) {
         this.f379.addElement(c_rf);
      }
   }

   C_c_B m673(String s) {
      Vector vector = new Vector();

      while (s != null) {
         int i = s.indexOf(46);
         String s1;
         if (i == -1) {
            s1 = s;
            s = null;
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         if ((s1 = s1.trim()).length() != 0) {
            C_RF c_rf;
            try {
               c_rf = LogicProgram.m1006(s1);
            } catch (C_k_B c_k_b) {
               c_rf = null;
            }

            if (c_rf == null) {
               vector.addElement(new C_c_B("truerr016", C_H.m666("unparsed", s1)));
            } else {
               vector.addElement(c_rf);
            }
         }
      }

      return this.m674(vector);
   }

   C_c_B m674(Vector vector) {
      int j = vector.size();
      int[] aint = new int[j];

      for (int i = 0; i < j; i++) {
         Object object = vector.elementAt(i);
         if (object instanceof C_c_B) {
            return (C_c_B)object;
         }

         aint[i] = this.m678((C_RF)object);
         if (aint[i] == -1) {
            return new C_c_B("truerr017", C_H.m666("sentence", object + ""));
         }

         for (int k = 0; k < i; k++) {
            if (aint[i] == aint[k]) {
               return new C_c_B("truerr018", C_H.m666("sentence", object + ""));
            }
         }
      }

      if (this.f379.size() != j) {
         return new C_c_B("truerr015");
      } else {
         Vector vector1 = new Vector();

         for (int l = 0; l < j; l++) {
            vector1.addElement(this.f379.elementAt(aint[l]));
         }

         this.f379 = vector1;
         this.m675();
         return new C_c_B(null);
      }
   }

   void m675() {
      int i = this.f379.size();
      this.f380 = new boolean[i == 0 ? 0 : 1 << i];
      if (this.f377 != null) {
         this.m676(this.f377, this.f380);
      } else if (this.f378 != null) {
         int j = this.f378.f827.length;
         this.m676(this.f378.f828, this.f380);
         boolean[] aboolean = new boolean[this.f380.length];

         for (int k = 0; k < j; k++) {
            this.m676(this.f378.f827[k], aboolean);

            for (int l = 0; l < this.f380.length; l++) {
               if (!aboolean[l]) {
                  this.f380[l] = true;
               }
            }
         }
      }
   }

   void m676(C_RF c_rf, boolean[] aboolean) {
      int i = aboolean.length;

      for (int j = 0; j < i; j++) {
         aboolean[j] = this.m677(c_rf, this.m679(j));
      }
   }

   boolean m677(C_RF c_rf, boolean[] aboolean) {
      if (c_rf instanceof C_q_F) {
         String s = c_rf.m1214();
         if (s.equals("~")) {
            return !this.m677(c_rf.m1217(0), aboolean);
         } else if (s.equals("&")) {
            return this.m677(c_rf.m1217(0), aboolean) & this.m677(c_rf.m1217(1), aboolean);
         } else if (s.equals("|")) {
            return this.m677(c_rf.m1217(0), aboolean) | this.m677(c_rf.m1217(1), aboolean);
         } else if (s.equals("->")) {
            return !this.m677(c_rf.m1217(0), aboolean) | this.m677(c_rf.m1217(1), aboolean);
         } else {
            return s.equals("<->") ? this.m677(c_rf.m1217(0), aboolean) == this.m677(c_rf.m1217(1), aboolean) : false;
         }
      } else {
         int i = this.m678(c_rf);
         return i == -1 ? false : aboolean[i];
      }
   }

   int m678(C_RF c_rf) {
      if (c_rf == null) {
         return -1;
      } else {
         int i = this.f379.size();

         for (int j = 0; j < i; j++) {
            if (c_rf.m1236((C_RF)this.f379.elementAt(j), new C_MB())) {
               return j;
            }
         }

         return -1;
      }
   }

   boolean[] m679(long i) {
      int j = this.f379.size();
      boolean[] aboolean = new boolean[j];

      for (int k = 0; k < j; k++) {
         aboolean[k] = (i & 1L) != 0L;
         i >>= 1;
      }

      return aboolean;
   }

   boolean m680() {
      int i = this.f380.length;

      for (int j = 0; j < i; j++) {
         if (!this.f380[j]) {
            return false;
         }
      }

      return true;
   }

   static boolean m681(C_RF c_rf, C_RF c_rf1) {
      if (c_rf instanceof C_y_A && c_rf1 instanceof C_y_A) {
         C_q_F c_q_f = new C_q_F("<->");
         c_q_f.m1215(c_rf);
         c_q_f.m1215(c_rf1);
         return new C_HA(c_q_f).m680();
      } else {
         return false;
      }
   }
}
