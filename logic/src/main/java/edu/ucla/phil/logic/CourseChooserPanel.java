package edu.ucla.phil.logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;

class CourseChooserPanel extends SizedPanel implements ChoiceListener {
   ChoiceButton institutionChoice;
   ChoiceButton termChoice;
   ChoiceButton courseChoice;
   CourseInfo[] courses;
   String[] termCodes;
   MessageTextArea commentArea;
   SizedPanel staffPanel;
   MessageDialog dialog = null;

   CourseChooserPanel(UserInfo userinfo, boolean flag) {
      this.courses = CourseInfo.findCoursesFor(userinfo);
      if (this.courses != null && this.courses.length != 0) {
         CourseInfo courseinfo = this.courses[0];
         this.setLayout(new GridBagLayout());
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         gridbagconstraints.gridx = 0;
         gridbagconstraints.gridy = 0;
         gridbagconstraints.fill = 0;
         gridbagconstraints.anchor = 21;
         this.add(new LogicLabel("Institution: "), gridbagconstraints);
         gridbagconstraints.gridy = 1;
         this.add(new LogicLabel("Term: "), gridbagconstraints);
         gridbagconstraints.gridy = 2;
         this.add(new LogicLabel("Course: "), gridbagconstraints);
         gridbagconstraints.gridx = 1;
         gridbagconstraints.anchor = 21;
         gridbagconstraints.fill = 1;
         String[] astring;
         if (flag && userinfo instanceof NewUserInfo) {
            astring = CourseInfo.listInstitutions(CourseInfo.getAllCourses());
         } else {
            astring = new String[]{courseinfo.institution};
         }

         int i = LogicProgram.indexOf(astring, courseinfo.institution);
         gridbagconstraints.gridy = 0;
         this.add(this.institutionChoice = new ChoiceButton(astring, i), gridbagconstraints);
         this.institutionChoice.addChoiceListener(this);
         if (flag) {
            this.termCodes = CourseInfo.listTerms(CourseInfo.findCourses(courseinfo.institution));
         } else {
            this.termCodes = new String[]{courseinfo.term};
         }

         i = LogicProgram.indexOf(this.termCodes, courseinfo.term);
         astring = CourseInfo.formatTermNames(this.termCodes, Institution.forName(courseinfo.institution));
         gridbagconstraints.gridy = 1;
         this.add(this.termChoice = new ChoiceButton(astring, i), gridbagconstraints);
         this.termChoice.addChoiceListener(this);
         if (flag) {
            astring = CourseInfo.listCourses(CourseInfo.findCourses(courseinfo.institution, courseinfo.term));
         } else {
            astring = new String[]{courseinfo.course};
         }

         i = LogicProgram.indexOf(astring, courseinfo.course);
         gridbagconstraints.gridy = 2;
         this.add(this.courseChoice = new ChoiceButton(astring, i), gridbagconstraints);
         this.courseChoice.addChoiceListener(this);
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
         this.commentArea = new MessageTextArea();
         this.commentArea.setLineWrap(false);
         this.commentArea.setVisible(false);
         this.add(this.commentArea, gridbagconstraints);
         gridbagconstraints.gridx = 0;
         gridbagconstraints.gridy = 4;
         gridbagconstraints.gridwidth = 3;
         gridbagconstraints.fill = 0;
         this.staffPanel = new SizedPanel();
         this.staffPanel.setLayout(new GridBagLayout());
         this.staffPanel.setVisible(false);
         this.add(this.staffPanel, gridbagconstraints);
         this.updateCourseDetails();
      }
   }

   synchronized CourseInfo getSelectedCourse() {
      if (this.courses != null && this.courses.length != 0) {
         String s = this.institutionChoice.getDisplayText();
         String s1 = this.termCodes[this.termChoice.selectedIndex];
         String s2 = this.courseChoice.getDisplayText();
         CourseInfo[] acourseinfo = CourseInfo.findCourses(s, s1, s2);
         return acourseinfo != null && acourseinfo.length != 0 ? acourseinfo[0] : null;
      } else {
         return null;
      }
   }

   @Override
   public synchronized void choiceChanged(ChoiceButton choicebutton, int i, int j) {
      if (this.courses != null && this.courses.length != 0) {
         if (j != i) {
            String s = this.institutionChoice.getDisplayText();
            this.termCodes = CourseInfo.listTerms(CourseInfo.findCourses(s));
            if (choicebutton == this.institutionChoice) {
               String[] astring = CourseInfo.formatTermNames(this.termCodes, Institution.forName(s));
               this.termChoice.setChoices(astring, astring.length - 1);
            }

            String s1 = this.termCodes[this.termChoice.selectedIndex];
            if (choicebutton == this.institutionChoice || choicebutton == this.termChoice) {
               String[] astring1 = CourseInfo.listCourses(CourseInfo.findCourses(s, s1));
               this.courseChoice.setChoices(astring1, 0);
            }

            this.updateCourseDetails();
         }
      }
   }

   static void fillStaffPanel(SizedPanel sizedpanel, CourseInfo courseinfo) {
      if (courseinfo != null) {
         if (courseinfo.instructors != null || courseinfo.assistants != null) {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            sizedpanel.removeAll();
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 0;
            gridbagconstraints.fill = 0;
            gridbagconstraints.anchor = 17;
            int i = courseinfo.instructors == null ? 0 : courseinfo.instructors.length;
            if (i > 0) {
               gridbagconstraints.gridx = 0;
               sizedpanel.add(new LogicLabel(i == 1 ? "Instructor: " : "Instructors: "), gridbagconstraints);
               gridbagconstraints.gridx = 1;

               for (int j = 0; j < i; j++) {
                  sizedpanel.add(new LogicLabel(courseinfo.instructors[j]), gridbagconstraints);
                  gridbagconstraints.gridy++;
               }
            }

            i = courseinfo.assistants == null ? 0 : courseinfo.assistants.length;
            if (i > 0) {
               gridbagconstraints.gridx = 0;
               sizedpanel.add(new LogicLabel(i == 1 ? "Assistant: " : "Assistants: "), gridbagconstraints);
               gridbagconstraints.gridx = 1;

               for (int k = 0; k < i; k++) {
                  sizedpanel.add(new LogicLabel(courseinfo.assistants[k]), gridbagconstraints);
                  gridbagconstraints.gridy++;
               }
            }
         }
      }
   }

   synchronized void updateCourseDetails() {
      CourseInfo courseinfo = this.getSelectedCourse();
      if (courseinfo != null) {
         if (courseinfo.instructors == null && courseinfo.assistants == null) {
            this.commentArea.setText(ProgressDialog.unescape(courseinfo.comment == null ? "" : courseinfo.comment));
            this.commentArea.setVisible(true);
         } else {
            this.commentArea.setVisible(false);
         }

         fillStaffPanel(this.staffPanel, courseinfo);
         this.staffPanel.setVisible(true);
         if (this.dialog != null) {
            this.dialog.pack();
         }
      }
   }

   static String joinWithAnd(String[] astring) {
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
