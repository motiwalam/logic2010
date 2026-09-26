package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.util.Hashtable;
import java.util.Vector;

class C_AB extends C_LB implements C_n_A {
   static String[] f120 = LogicProgram.f596;
   LPRecognition f121;
   C_VC f122;
   String f123;
   String f124;
   Vector f125;
   Vector f126;
   String f127;
   String f128;
   C_LB f129;
   C_LB f130;
   C_LB f131;
   C_LB f132;
   C_LB f133;
   C_LB f134;
   C_LB f135;
   C_ZE f136;
   C_ZE f137;
   C_s_B f138;
   C_YC f139;

   C_AB(LPRecognition lprecognition) {
      this.f121 = lprecognition;
      this.f129 = new C_LB();
      this.f129.add(this.f130 = new C_LB(), "North");
      this.f130.setLayout(new C_m_A());
      this.f129.add(new C_CC(5, 1, false, lprecognition.colors[0]), "Center");
      this.f129.add(this.f131 = new C_LB(), "South");
      this.f131.setLayout(new C_m_A());
      this.f131.add(this.f136 = new C_ZE());
      this.f132 = new C_LB();
      this.f132.setLayout(new C_m_A(4));
      this.f132.add(new C_ZE(LogicProgram.m1004(this.m213())));
      this.f132.add(this.f139 = new C_YC(lprecognition));
      this.f139.setForeground(lprecognition.colors[1]);
      this.f139.setBackground(lprecognition.colors[0]);
      this.f139.m1787(false);
      this.f139.m1789(false);
      this.f139.f1329 = lprecognition.fontSize * 3;
      C_LB c_lb = new C_LB();
      c_lb.setLayout(new FlowLayout(0, 0, 0));
      c_lb.add(this.f129);
      c_lb.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]));
      c_lb.add(this.f132);
      C_LB c_lb1 = new C_LB();
      c_lb1.add(new C_CC(lprecognition.fontSize * 2, 0, false, lprecognition.colors[0]), "North");
      c_lb1.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "West");
      c_lb1.add(c_lb, "Center");
      this.add(c_lb1, "North");
      this.f133 = new C_LB();
      this.f133.add(this.f137 = new C_ZE(), "West");
      this.f133.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "Center");
      C_LB c_lb2 = new C_LB();
      c_lb2.add(this.f133, "North");
      this.f134 = new C_LB();
      this.f134.add(this.f138 = new C_s_B());
      C_LB c_lb3 = new C_LB();
      c_lb3.add(c_lb2, "West");
      c_lb3.add(this.f134, "Center");
      C_LB c_lb4 = new C_LB();
      c_lb4.add(new C_CC(lprecognition.fontSize * 2, 0, false, lprecognition.colors[0]), "North");
      c_lb4.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "West");
      c_lb4.add(c_lb3, "Center");
      this.add(this.f135 = c_lb4, "Center");
      this.m214();
   }

   String m213() {
      C_H c_h = C_BF.m411("recnot005");
      String s = c_h == null ? null : c_h.f372;
      if (s == null) {
         s = "Please enter the rule that applies, or enter \"None\".";
      }

      return s;
   }

   void m214() {
      this.f122 = null;
      this.f123 = null;
      this.f124 = null;
      this.f125 = null;
      this.f126 = null;
      this.f127 = null;
      this.f128 = null;
      this.f130.removeAll();
      this.f136.setText("");
      this.f139.setText("");
      this.f137.setText("");
      this.f138.setText("");
   }

   void m215(C_XD c_xd) {
      this.m214();
      this.f121.setProblemTitle(c_xd.m1494());
      this.f124 = c_xd.m1483(c_xd.m1475('='));
      this.f121.titlePanel.m1823(LogicProgram.m995(this.f124, maggie, f120));
      this.f122 = C_VC.m1382(this.f124);
      this.f128 = c_xd.m1483(c_xd.m1475('*'));
      String s = LPRecognition.exercises == null ? null : LPRecognition.exercises.m1780(this.f123);
      c_xd = new C_XD(s);
      this.f125 = m216(c_xd.m1483(c_xd.m1475('@')));
      this.f126 = m216(c_xd.m1483(c_xd.m1475('~')));
      this.f127 = c_xd.m1483(c_xd.m1475('&'));
      if (this.f122 != null) {
         int i = this.f122.f827 == null ? 0 : this.f122.f827.length;

         for (int j = 0; j < i; j++) {
            C_ZE c_ze = new C_ZE(LogicProgram.m995(this.f122.f825[j], maggie, f120));
            this.f130.add(c_ze);
            c_ze.setForeground(this.f121.colors[0]);
         }

         this.f136.setText(LogicProgram.m995(this.f122.f826, maggie, f120));
         this.f136.setForeground(this.f121.colors[0]);
      }

      if (this.f128 != null) {
         this.f139.setText(this.f128);
      }
   }

   static Vector m216(String s) {
      if (s == null) {
         return null;
      } else {
         Vector vector = new Vector();
         C_OA c_oa = new C_OA("\\.");
         c_oa.m1132(s);

         String s1;
         while ((s1 = c_oa.m1135()) != null) {
            if ((s1 = s1.trim()).length() != 0) {
               vector.addElement(s1.toUpperCase());
            }
         }

         if (vector.size() == 0) {
            return null;
         } else {
            vector.trimToSize();
            return vector;
         }
      }
   }

   void m217() {
      this.f128 = this.f139.getText().trim();
      if (this.f128.length() == 0) {
         this.f128 = null;
      }
   }

   String m218() {
      this.m217();
      String s = "";
      s = s + C_XD.m1508(this.f123, '$');
      s = s + C_XD.m1508(this.f124, '=');
      return s + C_XD.m1508(this.f128, '*');
   }

   void m219() {
      this.f128 = null;
      this.f139.setText("");
      this.f137.setText("");
      this.f138.setText("");
   }

   boolean m220(boolean flag, String s) {
      if (flag) {
         this.f137.setText("Correct");
         this.f137.setForeground(dialogGreen);
         if (s == null) {
            this.f138.setText("Congratulations!");
         } else {
            this.f138.setText(LogicProgram.m1004(s.trim()));
         }
      } else {
         this.f137.setText("Incorrect");
         this.f137.setForeground(dialogRed);
         this.f138.setText("Try Again");
         if (s == null) {
            this.f138.setText("Try Again.");
         } else {
            this.f138.setText(LogicProgram.m1004(s.trim()));
         }
      }

      return flag;
   }

   boolean m221() {
      this.m217();
      if (this.f128 == null) {
         return this.m220(false, C_BF.m412("recnot001"));
      } else if (this.f125 != null && this.f125.indexOf(this.f128.toUpperCase()) != -1) {
         return this.m220(true, this.f127);
      } else if (this.f126 != null && this.f126.indexOf(this.f128.toUpperCase()) != -1) {
         String s2 = C_BF.m412("recnot006");
         if (s2 != null) {
            String s5 = this.f122.f827 != null && this.f122.f827.length == 1 ? "this premise" : "these premises";
            Hashtable hashtable1 = C_H.m667("ruleName", this.f128, "thisPremise", s5);
            s2 = C_H.m661(s2, hashtable1);
         }

         return this.m220(false, s2);
      } else if (this.f128.toUpperCase().equals("NONE")) {
         return this.f125 == null && (this.f122 == null || this.f122.m1389(this.f121.activeRules()) != 2)
            ? this.m220(true, this.f127)
            : this.m220(false, C_BF.m412("recnot001"));
      } else {
         C_VB c_vb = LogicProgram.m1026(this.f128);
         if (c_vb == null) {
            String s4 = C_BF.m412("recnot002");
            if (s4 != null) {
               s4 = C_H.m661(s4, C_H.m666("ruleName", this.f128));
            }

            return this.m220(false, s4);
         } else if (!this.f121.ruleActive(c_vb)) {
            String s3 = C_BF.m412("recnot007");
            if (s3 != null) {
               s3 = C_H.m661(s3, C_H.m666("ruleName", c_vb.f820));
            }

            return this.m220(false, s3);
         } else if (this.f122 == null) {
            return this.m220(false, C_BF.m412("recnot004"));
         } else {
            int i = this.f122.m1389(c_vb);
            if (i == 2) {
               return this.m220(true, this.f127);
            } else if (i != 1 && !this.m222(c_vb)) {
               String s6 = C_BF.m412("recnot003");
               if (s6 != null) {
                  String s7 = this.f122.f827 != null && this.f122.f827.length == 1 ? "this premise" : "these premises";
                  Hashtable hashtable2 = C_H.m667("ruleName", c_vb.f820, "thisPremise", s7);
                  s6 = C_H.m661(s6, hashtable2);
               }

               return this.m220(false, s6);
            } else {
               String s = C_BF.m412("recnot006");
               if (s != null) {
                  String s1 = this.f122.f827 != null && this.f122.f827.length == 1 ? "this premise" : "these premises";
                  Hashtable hashtable = C_H.m667("ruleName", c_vb.f820, "thisPremise", s1);
                  s = C_H.m661(s, hashtable);
               }

               return this.m220(false, s);
            }
         }
      }
   }

   boolean m222(C_VB c_vb) {
      if (this.f122.f827 != null && this.f122.f827.length == 1) {
         if (c_vb.f820.equalsIgnoreCase("EG")) {
            return true;
         }

         if (c_vb.f820.equalsIgnoreCase("AV")) {
            return true;
         }

         if (c_vb.f820.equalsIgnoreCase("AV3")) {
            return true;
         }
      }

      return false;
   }
}
