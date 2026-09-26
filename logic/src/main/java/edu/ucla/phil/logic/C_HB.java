package edu.ucla.phil.logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

class C_HB extends C_OE {
   String f381 = null;

   public C_HB() {
   }

   @Override
   boolean m682(String s) {
      if (s == null) {
         return this.m1170();
      } else {
         String[] astring = new String[]{"OK", "Cancel"};
         String[] astring1 = new String[]{"Institution: ", "Term: ", "Course: "};
         String[] astring2 = new String[]{"Student ID: "};
         short short1 = 250;
         C_SF c_sf = C_SF.m1293(this.m1156());
         C_ZE c_ze = new C_ZE(this.m1156());
         C_ZE c_ze1 = new C_ZE(c_sf.m1299(this.m1157()));
         C_ZE c_ze2 = new C_ZE(this.m1159());
         C_p_A c_p_a = new C_p_A(this.m1153(), short1);
         C_ZE[] ac_ze = new C_ZE[]{c_ze, c_ze1, c_ze2};
         C_p_A[] ac_p_a = new C_p_A[]{c_p_a};
         C_LB c_lb = new C_LB();
         GridBagLayout gridbaglayout = new GridBagLayout();
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         c_lb.setLayout(gridbaglayout);

         for (int i = 0; i < ac_ze.length; i++) {
            C_d_D c_d_d = new C_d_D(ac_ze[i].getText());
            C_d_D c_d_d1 = new C_d_D(astring1[i]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            c_lb.add(c_d_d1, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            c_lb.add(c_d_d, gridbagconstraints);
         }

         for (int j = 0; j < ac_p_a.length; j++) {
            C_d_D c_d_d2 = new C_d_D(astring2[j]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            c_lb.add(c_d_d2, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            c_lb.add(ac_p_a[j], gridbagconstraints);
         }

         C_0E c_0e = new C_0E();
         C_UA c_ua = new C_UA(c_0e, s, c_lb, astring);
         c_ua.m1314(0);
         ac_p_a[0].requestFocus();
         c_ua.m1322(null);
         c_0e.dispose();
         if (c_ua.f790 != 0) {
            return false;
         } else {
            this.put("studentID", c_p_a.getText());
            return this.f662 = true;
         }
      }
   }

   @Override
   boolean m683() {
      return !this.m1154(true).equals("");
   }

   @Override
   boolean m684() {
      this.f662 = false;
      return true;
   }
}
