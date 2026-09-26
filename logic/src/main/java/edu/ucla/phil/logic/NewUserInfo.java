package edu.ucla.phil.logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

class NewUserInfo extends UserInfo {
   String f381 = null;

   public NewUserInfo() {
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
         Institution institution = Institution.m1293(this.getInstitution());
         C_ZE c_ze = new C_ZE(this.getInstitution());
         C_ZE c_ze1 = new C_ZE(institution.m1299(this.getTerm()));
         C_ZE c_ze2 = new C_ZE(this.getClassName());
         EditableTextPane editabletextpane = new EditableTextPane(this.getStudentId(), short1);
         C_ZE[] ac_ze = new C_ZE[]{c_ze, c_ze1, c_ze2};
         EditableTextPane[] aeditabletextpane = new EditableTextPane[]{editabletextpane};
         SizedPanel sizedpanel = new SizedPanel();
         GridBagLayout gridbaglayout = new GridBagLayout();
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         sizedpanel.setLayout(gridbaglayout);

         for (int i = 0; i < ac_ze.length; i++) {
            C_d_D c_d_d = new C_d_D(ac_ze[i].getText());
            C_d_D c_d_d1 = new C_d_D(astring1[i]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            sizedpanel.add(c_d_d1, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            sizedpanel.add(c_d_d, gridbagconstraints);
         }

         for (int j = 0; j < aeditabletextpane.length; j++) {
            C_d_D c_d_d2 = new C_d_D(astring2[j]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            sizedpanel.add(c_d_d2, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            sizedpanel.add(aeditabletextpane[j], gridbagconstraints);
         }

         ModuleFrame moduleframe = new ModuleFrame();
         MessageDialog messagedialog = new MessageDialog(moduleframe, s, sizedpanel, astring);
         messagedialog.m1314(0);
         aeditabletextpane[0].requestFocus();
         messagedialog.m1322(null);
         moduleframe.dispose();
         if (messagedialog.f790 != 0) {
            return false;
         } else {
            this.put("studentID", editabletextpane.getText());
            return this.dirty = true;
         }
      }
   }

   @Override
   boolean m683() {
      return !this.m1154(true).equals("");
   }

   @Override
   boolean save() {
      this.dirty = false;
      return true;
   }
}
