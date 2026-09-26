package edu.ucla.phil.logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;

class C_n_E extends C_LB implements C_F {
   C_b_A f1305;
   C_b_A f1306;
   C_b_A f1307;
   C_MF[] f1308;
   String[] f1309;
   C_s_B f1310;
   C_LB f1311;
   C_UA f1312 = null;

   C_n_E(C_OE c_oe, boolean flag) {
      this.f1308 = C_MF.m1116(c_oe);
      if (this.f1308 != null && this.f1308.length != 0) {
         C_MF c_mf = this.f1308[0];
         this.setLayout(new GridBagLayout());
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         gridbagconstraints.gridx = 0;
         gridbagconstraints.gridy = 0;
         gridbagconstraints.fill = 0;
         gridbagconstraints.anchor = 21;
         this.add(new C_ZE("Institution: "), gridbagconstraints);
         gridbagconstraints.gridy = 1;
         this.add(new C_ZE("Term: "), gridbagconstraints);
         gridbagconstraints.gridy = 2;
         this.add(new C_ZE("Course: "), gridbagconstraints);
         gridbagconstraints.gridx = 1;
         gridbagconstraints.anchor = 21;
         gridbagconstraints.fill = 1;
         String[] astring;
         if (flag && c_oe instanceof C_HB) {
            astring = C_MF.m1111(C_MF.m1117());
         } else {
            astring = new String[]{c_mf.f620};
         }

         int i = LogicProgram.m1051(astring, c_mf.f620);
         gridbagconstraints.gridy = 0;
         this.add(this.f1305 = new C_b_A(astring, i), gridbagconstraints);
         this.f1305.m1659(this);
         if (flag) {
            this.f1309 = C_MF.m1112(C_MF.m1118(c_mf.f620));
         } else {
            this.f1309 = new String[]{c_mf.f621};
         }

         i = LogicProgram.m1051(this.f1309, c_mf.f621);
         astring = C_MF.m1110(this.f1309, C_SF.m1293(c_mf.f620));
         gridbagconstraints.gridy = 1;
         this.add(this.f1306 = new C_b_A(astring, i), gridbagconstraints);
         this.f1306.m1659(this);
         if (flag) {
            astring = C_MF.m1113(C_MF.m1119(c_mf.f620, c_mf.f621));
         } else {
            astring = new String[]{c_mf.f622};
         }

         i = LogicProgram.m1051(astring, c_mf.f622);
         gridbagconstraints.gridy = 2;
         this.add(this.f1307 = new C_b_A(astring, i), gridbagconstraints);
         this.f1307.m1659(this);
         gridbagconstraints.gridx = 2;
         gridbagconstraints.gridy = 0;
         gridbagconstraints.gridheight = 2;
         gridbagconstraints.weightx = 1.0;
         this.add(Box.createGlue(), gridbagconstraints);
         gridbagconstraints.gridx = 0;
         gridbagconstraints.gridy = 3;
         gridbagconstraints.gridwidth = 3;
         gridbagconstraints.fill = 1;
         gridbagconstraints.anchor = 21;
         this.f1310 = new C_s_B();
         this.f1310.setLineWrap(false);
         this.f1310.setVisible(false);
         this.add(this.f1310, gridbagconstraints);
         gridbagconstraints.gridx = 0;
         gridbagconstraints.gridy = 4;
         gridbagconstraints.gridwidth = 3;
         gridbagconstraints.fill = 0;
         this.f1311 = new C_LB();
         this.f1311.setLayout(new GridBagLayout());
         this.f1311.setVisible(false);
         this.add(this.f1311, gridbagconstraints);
         this.m1968();
      }
   }

   synchronized C_MF m1966() {
      if (this.f1308 != null && this.f1308.length != 0) {
         String s = this.f1305.m1657();
         String s1 = this.f1309[this.f1306.f985];
         String s2 = this.f1307.m1657();
         C_MF[] ac_mf = C_MF.m1120(s, s1, s2);
         return ac_mf != null && ac_mf.length != 0 ? ac_mf[0] : null;
      } else {
         return null;
      }
   }

   @Override
   public synchronized void m514(C_b_A c_b_a, int i, int j) {
      if (this.f1308 != null && this.f1308.length != 0) {
         if (j != i) {
            String s = this.f1305.m1657();
            this.f1309 = C_MF.m1112(C_MF.m1118(s));
            if (c_b_a == this.f1305) {
               String[] astring = C_MF.m1110(this.f1309, C_SF.m1293(s));
               this.f1306.m1652(astring, astring.length - 1);
            }

            String s1 = this.f1309[this.f1306.f985];
            if (c_b_a == this.f1305 || c_b_a == this.f1306) {
               String[] astring1 = C_MF.m1113(C_MF.m1119(s, s1));
               this.f1307.m1652(astring1, 0);
            }

            this.m1968();
         }
      }
   }

   static void m1967(C_LB c_lb, C_MF c_mf) {
      if (c_mf != null) {
         if (c_mf.f625 != null || c_mf.f626 != null) {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            c_lb.removeAll();
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 0;
            gridbagconstraints.fill = 0;
            gridbagconstraints.anchor = 17;
            int i = c_mf.f625 == null ? 0 : c_mf.f625.length;
            if (i > 0) {
               gridbagconstraints.gridx = 0;
               c_lb.add(new C_ZE(i == 1 ? "Instructor: " : "Instructors: "), gridbagconstraints);
               gridbagconstraints.gridx = 1;

               for (int j = 0; j < i; j++) {
                  c_lb.add(new C_ZE(c_mf.f625[j]), gridbagconstraints);
                  gridbagconstraints.gridy++;
               }
            }

            i = c_mf.f626 == null ? 0 : c_mf.f626.length;
            if (i > 0) {
               gridbagconstraints.gridx = 0;
               c_lb.add(new C_ZE(i == 1 ? "Assistant: " : "Assistants: "), gridbagconstraints);
               gridbagconstraints.gridx = 1;

               for (int k = 0; k < i; k++) {
                  c_lb.add(new C_ZE(c_mf.f626[k]), gridbagconstraints);
                  gridbagconstraints.gridy++;
               }
            }
         }
      }
   }

   synchronized void m1968() {
      C_MF c_mf = this.m1966();
      if (c_mf != null) {
         if (c_mf.f625 == null && c_mf.f626 == null) {
            this.f1310.setText(C_SD.m1292(c_mf.f624 == null ? "" : c_mf.f624));
            this.f1310.setVisible(true);
         } else {
            this.f1310.setVisible(false);
         }

         m1967(this.f1311, c_mf);
         this.f1311.setVisible(true);
         if (this.f1312 != null) {
            this.f1312.pack();
         }
      }
   }

   static String m1969(String[] astring) {
      String s = "";
      int i = astring.length;

      for (int j = 0; j < i; j++) {
         if (j > 0) {
            if (i > 2) {
               s = s + ", ";
            } else if (i == 2) {
               s = s + " ";
            }

            if (j == i - 1) {
               s = s + "and ";
            }
         }

         s = s + astring[j];
      }

      return s;
   }
}
