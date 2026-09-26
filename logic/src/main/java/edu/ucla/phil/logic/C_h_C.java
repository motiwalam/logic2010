package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

class C_h_C extends C_e_D {
   Hashtable f1158;
   boolean f1159;

   C_h_C(boolean flag) {
      this.f1159 = flag;
      this.f1080 = true;
      this.f1158 = null;
   }

   @Override
   void m1099(C_U.C__A c_u$c__a) {
      LPSymbolizer.restating = true;
      C_e_D.C__A c_e_d$c__a = new C_e_D.C__A(this.size(), new C_XD(), new C_d_C(null), this, c_u$c__a);
      SwingUtilities.invokeLater(c_e_d$c__a);
   }

   @Override
   synchronized int m1098(String s, Vector vector, boolean flag) {
      C_XD c_xd = new C_XD(s);
      int i = this.m1775(c_xd, vector, flag);
      if (i != -1 && this.f1158 != null) {
         String s1 = c_xd.m1483(c_xd.m1475('g'));
         if (s1 != null) {
            String s3;
            Vector vector1 = (Vector)this.f1158.get(s3 = s1.trim());
            boolean flag1 = vector1 == null;
            String s2 = c_xd.m1494();
            if (flag1) {
               vector1 = new Vector();
            }

            vector1.addElement(new C_WD(s2, C_d_C.m1707(c_xd, this.f1159)));
            if (flag1) {
               this.f1158.put(s3, vector1);
            }
         }
      }

      return i;
   }

   @Override
   synchronized void m1101(int i) {
      C_XD c_xd = new C_XD(this.m1778(i));
      if (c_xd.m1497() == null && LPSymbolizer.getExerciseTitle(c_xd.m1494()) == null) {
         String s = c_xd.m1483(c_xd.m1475('@'));
         if (s != null) {
            C_OA c_oa = new C_OA("\\.");
            c_oa.m1132(s);

            while (true) {
               String s1 = c_oa.m1135();
               if (s1 == null) {
                  break;
               }

               if (!s1.equals("")) {
                  LPSymbolizer.userKey.remove(s1);
               }
            }
         }
      }

      super.m1101(i);
   }

   @Override
   C_f_F m1102(String s, boolean flag) {
      return new C__C(s, this, flag);
   }

   @Override
   boolean m1103(C_XD c_xd) {
      return LPSymbolizer.hasWork(c_xd);
   }

   @Override
   String m1104(C_XD c_xd) {
      return LPSymbolizer.getWork(c_xd);
   }

   @Override
   String m1105(C_XD c_xd) {
      return LPSymbolizer.removeWork(c_xd);
   }

   @Override
   String m1106(C_XD c_xd) {
      return LPSymbolizer.getProblemStatement(c_xd);
   }

   @Override
   int m1107() {
      return 4;
   }
}
