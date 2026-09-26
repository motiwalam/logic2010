package edu.ucla.phil.logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;

class C_n_E extends SizedPanel implements C_F {
   C_b_A f1305;
   C_b_A f1306;
   C_b_A f1307;
   CourseInfo[] f1308;
   String[] f1309;
   C_s_B f1310;
   SizedPanel f1311;
   MessageDialog f1312 = null;

   C_n_E(UserInfo userinfo, boolean flag) {
      this.f1308 = CourseInfo.m1116(userinfo);
      if (this.f1308 != null && this.f1308.length != 0) {
         CourseInfo courseinfo = this.f1308[0];
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
         if (flag && userinfo instanceof NewUserInfo) {
            astring = CourseInfo.m1111(CourseInfo.m1117());
         } else {
            astring = new String[]{courseinfo.f620};
         }

         int i = LogicProgram.m1051(astring, courseinfo.f620);
         gridbagconstraints.gridy = 0;
         this.add(this.f1305 = new C_b_A(astring, i), gridbagconstraints);
         this.f1305.m1659(this);
         if (flag) {
            this.f1309 = CourseInfo.m1112(CourseInfo.m1118(courseinfo.f620));
         } else {
            this.f1309 = new String[]{courseinfo.f621};
         }

         i = LogicProgram.m1051(this.f1309, courseinfo.f621);
         astring = CourseInfo.m1110(this.f1309, Institution.m1293(courseinfo.f620));
         gridbagconstraints.gridy = 1;
         this.add(this.f1306 = new C_b_A(astring, i), gridbagconstraints);
         this.f1306.m1659(this);
         if (flag) {
            astring = CourseInfo.m1113(CourseInfo.m1119(courseinfo.f620, courseinfo.f621));
         } else {
            astring = new String[]{courseinfo.f622};
         }

         i = LogicProgram.m1051(astring, courseinfo.f622);
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
         this.f1311 = new SizedPanel();
         this.f1311.setLayout(new GridBagLayout());
         this.f1311.setVisible(false);
         this.add(this.f1311, gridbagconstraints);
         this.m1968();
      }
   }

   synchronized CourseInfo m1966() {
      if (this.f1308 != null && this.f1308.length != 0) {
         String s = this.f1305.m1657();
         String s1 = this.f1309[this.f1306.f985];
         String s2 = this.f1307.m1657();
         CourseInfo[] acourseinfo = CourseInfo.m1120(s, s1, s2);
         return acourseinfo != null && acourseinfo.length != 0 ? acourseinfo[0] : null;
      } else {
         return null;
      }
   }

   @Override
   public synchronized void m514(C_b_A c_b_a, int i, int j) {
      if (this.f1308 != null && this.f1308.length != 0) {
         if (j != i) {
            String s = this.f1305.m1657();
            this.f1309 = CourseInfo.m1112(CourseInfo.m1118(s));
            if (c_b_a == this.f1305) {
               String[] astring = CourseInfo.m1110(this.f1309, Institution.m1293(s));
               this.f1306.m1652(astring, astring.length - 1);
            }

            String s1 = this.f1309[this.f1306.f985];
            if (c_b_a == this.f1305 || c_b_a == this.f1306) {
               String[] astring1 = CourseInfo.m1113(CourseInfo.m1119(s, s1));
               this.f1307.m1652(astring1, 0);
            }

            this.m1968();
         }
      }
   }

   static void m1967(SizedPanel sizedpanel, CourseInfo courseinfo) {
      if (courseinfo != null) {
         if (courseinfo.f625 != null || courseinfo.f626 != null) {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            sizedpanel.removeAll();
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 0;
            gridbagconstraints.fill = 0;
            gridbagconstraints.anchor = 17;
            int i = courseinfo.f625 == null ? 0 : courseinfo.f625.length;
            if (i > 0) {
               gridbagconstraints.gridx = 0;
               sizedpanel.add(new C_ZE(i == 1 ? "Instructor: " : "Instructors: "), gridbagconstraints);
               gridbagconstraints.gridx = 1;

               for (int j = 0; j < i; j++) {
                  sizedpanel.add(new C_ZE(courseinfo.f625[j]), gridbagconstraints);
                  gridbagconstraints.gridy++;
               }
            }

            i = courseinfo.f626 == null ? 0 : courseinfo.f626.length;
            if (i > 0) {
               gridbagconstraints.gridx = 0;
               sizedpanel.add(new C_ZE(i == 1 ? "Assistant: " : "Assistants: "), gridbagconstraints);
               gridbagconstraints.gridx = 1;

               for (int k = 0; k < i; k++) {
                  sizedpanel.add(new C_ZE(courseinfo.f626[k]), gridbagconstraints);
                  gridbagconstraints.gridy++;
               }
            }
         }
      }
   }

   synchronized void m1968() {
      CourseInfo courseinfo = this.m1966();
      if (courseinfo != null) {
         if (courseinfo.f625 == null && courseinfo.f626 == null) {
            this.f1310.setText(ProgressDialog.m1292(courseinfo.f624 == null ? "" : courseinfo.f624));
            this.f1310.setVisible(true);
         } else {
            this.f1310.setVisible(false);
         }

         m1967(this.f1311, courseinfo);
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
