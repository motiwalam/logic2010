package edu.ucla.phil.logic;

import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JCheckBox;

class C_e_A extends C_LB {
   C_IE f1064;
   int f1065;
   int f1066;
   int f1067;
   C_QF f1068;

   C_e_A(C_IE c_ie, int i) {
      this.f1064 = c_ie;
      this.f1065 = i;
      this.f1068 = null;
      this.f1066 = c_ie.f423 == 0 ? 1 : i;
      this.f1067 = 1;

      for (int j = 1; j < c_ie.f423; j++) {
         this.f1067 *= i;
      }

      if (this.f1066 != 0) {
         this.setLayout(this.f1068 = new C_QF(this.f1066, this.f1067));
         this.f1068.setHgap(10);
         int[] aint = new int[c_ie.f423];
         if (c_ie instanceof C_a_C) {
            C_a_C c_a_c = (C_a_C)c_ie;
            if (c_ie.f423 == 0) {
               C_NE c_ne;
               this.add(c_ne = new C_NE(c_ie.f422), new Point(0, 0));
               c_ne.setSelected(c_a_c.f969 != null && !c_a_c.f969.isEmpty());
            } else {
               for (int i1 = 0; i1 < this.f1067; i1++) {
                  String s = "";
                  int l = i1;

                  for (int k = 1; k < c_ie.f423; k++) {
                     s = s + "," + (aint[k - 1] = l % i);
                     l /= i;
                  }

                  for (int k1 = 0; k1 < this.f1066; k1++) {
                     C_NE c_ne1;
                     this.add(c_ne1 = new C_NE(c_ie.f422 + "(" + s + k1 + ")"), new Point(k1, i1));
                     aint[c_ie.f423 - 1] = k1;
                     Boolean obool = (Boolean)c_ie.m708(aint);
                     c_ne1.setSelected(obool != null && obool);
                  }
               }
            }
         } else if (c_ie instanceof C_g_E) {
            C_g_E c_g_e = (C_g_E)c_ie;
            if (c_ie.f423 == 0) {
               C_d_E c_d_e;
               this.add(c_d_e = new C_d_E(c_ie.f422, i), new Point(0, 0));
               c_d_e.m1745(c_g_e.f1135 == null ? 0 : c_g_e.f1135);
            } else {
               for (int j1 = 0; j1 < this.f1067; j1++) {
                  String s1 = "";
                  int j2 = j1;

                  for (int l1 = 1; l1 < c_ie.f423; l1++) {
                     s1 = s1 + "," + (aint[l1 - 1] = j2 % i);
                     j2 /= i;
                  }

                  for (int i2 = 0; i2 < this.f1066; i2++) {
                     C_d_E c_d_e1;
                     this.add(c_d_e1 = new C_d_E(c_ie.f422 + "(" + s1 + i2 + ")", i), new Point(i2, j1));
                     aint[c_ie.f423 - 1] = i2;
                     Integer integer = (Integer)c_ie.m708(aint);
                     c_d_e1.m1745(integer == null ? 0 : integer);
                  }
               }
            }
         }
      }
   }

   void m1756() {
      int[] aint = new int[this.f1064.f423];
      if (this.f1064 instanceof C_a_C) {
         C_a_C c_a_c = (C_a_C)this.f1064;
         Vector vector = new Vector();
         if (this.f1064.f423 == 0) {
            JCheckBox jcheckbox1 = (JCheckBox)this.f1068.m1197(new Point(0, 0));
            if (jcheckbox1.isSelected()) {
               vector.addElement(new C_e_());
            }
         } else {
            for (int i = 0; i < this.f1067; i++) {
               int k = i;

               for (int j = 1; j < this.f1064.f423; j++) {
                  aint[j - 1] = k % this.f1065;
                  k /= this.f1065;
               }

               for (int i1 = 0; i1 < this.f1066; i1++) {
                  JCheckBox jcheckbox = (JCheckBox)this.f1068.m1197(new Point(i1, i));
                  aint[this.f1064.f423 - 1] = i1;
                  if (jcheckbox.isSelected()) {
                     C_e_ c_e_ = new C_e_();
                     c_e_.m1747(aint);
                     vector.addElement(c_e_);
                  }
               }
            }
         }

         c_a_c.f969 = vector;
      } else if (this.f1064 instanceof C_g_E) {
         C_g_E c_g_e = (C_g_E)this.f1064;
         if (this.f1064.f423 == 0) {
            C_d_E c_d_e = (C_d_E)this.f1068.m1197(new Point(0, 0));
            c_g_e.f1134 = null;
            c_g_e.f1135 = new Integer(c_d_e.m1744());
         } else {
            Hashtable hashtable = new Hashtable();

            for (int l = 0; l < this.f1067; l++) {
               int l1 = l;

               for (int j1 = 1; j1 < this.f1064.f423; j1++) {
                  aint[j1 - 1] = l1 % this.f1065;
                  l1 /= this.f1065;
               }

               for (int k1 = 0; k1 < this.f1066; k1++) {
                  C_d_E c_d_e1 = (C_d_E)this.f1068.m1197(new Point(k1, l));
                  aint[this.f1064.f423 - 1] = k1;
                  C_e_ c_e_1 = new C_e_();
                  c_e_1.m1747(aint);
                  hashtable.put(c_e_1, new Integer(c_d_e1.m1744()));
               }
            }

            c_g_e.f1134 = hashtable;
            c_g_e.f1135 = null;
         }
      }
   }
}
