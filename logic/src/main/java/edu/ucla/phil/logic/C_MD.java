package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

class C_MD extends C_e_D {
   Hashtable f618 = null;

   synchronized void m1097(C_XD c_xd) {
      String s = c_xd.m1494();
      String s1 = LPDerivation.getProblemRuleProven(c_xd);
      if (s != null && s1 != null) {
         Vector vector = new Vector();
         C_n_F c_n_f = new C_n_F();
         LPDerivation.listRules(s1, vector, c_n_f, false);
         if (this.f618 == null) {
            this.f618 = new Hashtable();
         }

         Enumeration enumeration = vector.elements();

         while (enumeration.hasMoreElements()) {
            C_VB c_vb = LogicProgram.f534.m2203((String)enumeration.nextElement());
            if (c_vb != null) {
               C_LF[] ac_lf = c_vb.m1374();
               int i = ac_lf.length;

               for (int j = 0; j < i; j++) {
                  String s2 = ac_lf[j].f820;
                  Vector vector1 = (Vector)this.f618.get(s2);
                  if (vector1 == null) {
                     vector1 = new Vector();
                     vector1.addElement(s);
                     this.f618.put(s2, vector1);
                  } else if (!vector1.contains(s)) {
                     vector1.addElement(s);
                  }
               }
            }
         }

         enumeration = c_n_f.m1985();

         while (enumeration.hasMoreElements()) {
            C_QE c_qe = LogicProgram.m1025((Integer)enumeration.nextElement());
            if (c_qe != null) {
               Integer integer = c_qe.f700;
               Vector vector2 = (Vector)this.f618.get(integer);
               if (vector2 == null) {
                  vector2 = new Vector();
                  vector2.addElement(s);
                  this.f618.put(integer, vector2);
               }

               if (!vector2.contains(s)) {
                  vector2.addElement(s);
               }
            }
         }
      }
   }

   @Override
   synchronized int m1098(String s, Vector vector, boolean flag) {
      C_XD c_xd = new C_XD(s);
      int i = this.m1775(c_xd, vector, flag);
      if (i != -1 && this.f618 != null) {
         this.m1097(c_xd);
      }

      return i;
   }

   @Override
   void m1099(C_U.C__A c_u$c__a) {
      LPDerivation.restating = true;
      C_e_D.C__A c_e_d$c__a = new C_e_D.C__A(this.size(), new C_XD(), new LPDerivation(false), c_u$c__a);
      SwingUtilities.invokeLater(c_e_d$c__a);
   }

   synchronized void m1100() {
      int i = this.size();
      LPDerivation.userRules = new C_z_B(null);

      for (int j = 0; j < i; j++) {
         C_XD c_xd = new C_XD(this.m1778(j));
         String s = c_xd.m1494();
         if (s != null && s.toUpperCase().startsWith("UR")) {
            C_DB c_db = new C_DB(s);
            if (c_db.f823 == null) {
               LPDerivation.userRules.m2202(c_db);
            }
         }
      }
   }

   @Override
   synchronized void m1101(int i) {
      C_XD c_xd = new C_XD(this.m1778(i));
      String s = c_xd.m1494();
      if (s != null && s.toUpperCase().equals("UR")) {
         C_DB c_db = (C_DB)LPDerivation.userRules.m2203(s);
         if (c_db != null) {
            LPDerivation.userRules.m2206(c_db);
         }
      }

      super.m1101(i);
   }

   @Override
   C_f_F m1102(String s, boolean flag) {
      return new C_EE(s, flag);
   }

   @Override
   boolean m1103(C_XD c_xd) {
      return LPDerivation.hasWork(c_xd);
   }

   @Override
   String m1104(C_XD c_xd) {
      return LPDerivation.getWork(c_xd);
   }

   @Override
   String m1105(C_XD c_xd) {
      return LPDerivation.removeWork(c_xd);
   }

   @Override
   String m1106(C_XD c_xd) {
      return LPDerivation.getProblemStatement(c_xd);
   }

   @Override
   int m1107() {
      return 0;
   }
}
