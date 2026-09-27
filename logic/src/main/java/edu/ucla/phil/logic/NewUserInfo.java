package edu.ucla.phil.logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

class NewUserInfo extends UserInfo {
   String passwordHash = null;

   public NewUserInfo() {
   }

   @Override
   boolean editInfo(String s) {
      if (s == null) {
         return this.editInfo();
      } else {
         String[] astring = new String[]{"OK", "Cancel"};
         String[] astring1 = new String[]{"Institution: ", "Term: ", "Course: "};
         String[] astring2 = new String[]{"Student ID: "};
         short short1 = 250;
         Institution institution = Institution.forName(this.getInstitution());
         LogicLabel logiclabel = new LogicLabel(this.getInstitution());
         LogicLabel logiclabel1 = new LogicLabel(institution.displayTerm(this.getTerm()));
         LogicLabel logiclabel2 = new LogicLabel(this.getClassName());
         EditableTextPane editabletextpane = new EditableTextPane(this.getStudentId(), short1);
         LogicLabel[] alogiclabel = new LogicLabel[]{logiclabel, logiclabel1, logiclabel2};
         EditableTextPane[] aeditabletextpane = new EditableTextPane[]{editabletextpane};
         SizedPanel sizedpanel = new SizedPanel();
         GridBagLayout gridbaglayout = new GridBagLayout();
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         sizedpanel.setLayout(gridbaglayout);

         for (int i = 0; i < alogiclabel.length; i++) {
            FontLabel fontlabel = new FontLabel(alogiclabel[i].getText());
            FontLabel fontlabel1 = new FontLabel(astring1[i]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            sizedpanel.add(fontlabel1, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            sizedpanel.add(fontlabel, gridbagconstraints);
         }

         for (int j = 0; j < aeditabletextpane.length; j++) {
            FontLabel fontlabel2 = new FontLabel(astring2[j]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            sizedpanel.add(fontlabel2, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            sizedpanel.add(aeditabletextpane[j], gridbagconstraints);
         }

         ModuleFrame moduleframe = new ModuleFrame();
         MessageDialog messagedialog = new MessageDialog(moduleframe, s, sizedpanel, astring);
         messagedialog.setDefaultButtonIndex(0);
         aeditabletextpane[0].requestFocus();
         messagedialog.showAt(null);
         moduleframe.dispose();
         if (messagedialog.selectedButton != 0) {
            return false;
         } else {
            this.put("studentID", editabletextpane.getText());
            return this.dirty = true;
         }
      }
   }

   @Override
   boolean isComplete() {
      return !this.getStudentId(true).equals("");
   }

   @Override
   boolean save() {
      this.dirty = false;
      return true;
   }
}
