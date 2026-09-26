package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_a_F {
   String f979;
   int f980;

   C_a_F(String s, int i) {
      this.f979 = s;
      this.f980 = i;
   }

   static C_a_F m1642(C_0C c_0c, C_OE c_oe, C_x_A c_x_a, int i) {
      String s = c_oe instanceof C_HB ? "not051" : "not052";
      return m1643(c_0c, c_oe, c_x_a, s, i);
   }

   static C_a_F m1643(C_0C c_0c, C_OE c_oe, C_x_A c_x_a, String s, int i) {
      C__F c__f = new C__F(C_j_C.m1864(c_0c, c_oe, c_x_a));
      if (c__f.f932 == null) {
         return null;
      } else {
         Hashtable hashtable = C_H.m667("site", c_oe.m1156(), "sid", c_oe.m1153());
         if (!(c_oe instanceof C_HB)) {
            C_H.m664(hashtable, "name", c_oe.m1169());
         }

         String s1 = C_H.m661(C_H.m412(s), hashtable);
         if (c_x_a != null) {
            c_x_a.m2162(true);
         }

         Integer integer = C_KC.m908(c_0c, c_oe, c__f, s1, i);
         if (c_x_a != null) {
            c_x_a.m2162(false);
         }

         if (integer == null) {
            return null;
         } else {
            c_oe.f664 = integer;
            if (c_oe instanceof C_HB) {
               ((C_HB)c_oe).f381 = c__f.f932;
            }

            return new C_a_F(c__f.f932, integer);
         }
      }
   }
}
