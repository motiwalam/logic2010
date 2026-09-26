package edu.ucla.phil.logic;

import java.util.Hashtable;

abstract class C_SF {
   static C_SF[] f774 = new C_SF[]{new C_m_E(), new C_p_C(), new C_o_C(), new C_l_C(), new C_v_E(), new C_f_B()};
   static Hashtable f775 = new Hashtable();

   static C_SF m1293(String s) {
      if (s == null) {
         return null;
      } else {
         C_SF c_sf = (C_SF)f775.get(s.toUpperCase());
         if (c_sf != null) {
            return c_sf;
         } else {
            C_e_C c_e_c = new C_e_C(s);
            f775.put(c_e_c.m1294().toUpperCase(), c_e_c);
            return c_e_c;
         }
      }
   }

   abstract String m1294();

   abstract String m1295(String s);

   abstract C_P m1296(String s);

   abstract String m1297(C_P c_p);

   abstract String m1298(C_P c_p);

   String m1299(String s) {
      C_P c_p = this.m1296(s);
      return c_p == null ? s : this.m1298(c_p);
   }

   static {
      for (int i = 0; i < f774.length; i++) {
         f775.put(f774[i].m1294().toUpperCase(), f774[i]);
      }
   }
}
