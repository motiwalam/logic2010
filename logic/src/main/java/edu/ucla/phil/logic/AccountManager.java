package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

class AccountManager implements LogicConstants {
   static CourseInfo chooseCourse(UserInfo userinfo, String s, boolean flag, boolean flag1) {
      CourseInfo courseinfo;
      if (flag && ServerConnection.isExamVersionCurrent()) {
         courseinfo = ServerConnection.getConfiguredCourse();
         if (courseinfo == null) {
            MessageDialog.showMessage(Message.get("not079"), null, null, null);
            return null;
         }
      } else {
         String s1 = Message.substitute(Message.getText(s), userinfo.getMessageParams());
         String s2 = Message.substitute(s1, ServerConnection.getCourseMessageParams());
         CourseChooserPanel coursechooserpanel = new CourseChooserPanel(userinfo, flag);
         if (coursechooserpanel.courses == null) {
            return null;
         }

         if (coursechooserpanel.courses.length == 0) {
            Message message = Message.get(userinfo instanceof NewUserInfo ? "not042" : "not043");
            MessageDialog.showMessage(message, Message.params("site", userinfo.getInstitution()), null, null);
            return null;
         }

         SizedPanel sizedpanel = new SizedPanel();
         sizedpanel.setLayout(new BorderLayout());
         MessageTextArea messagetextarea = new MessageTextArea(ProgressDialog.unescape(s2));
         messagetextarea.setLineWrap(false);
         sizedpanel.add(messagetextarea, "North");
         sizedpanel.add(coursechooserpanel, "Center");
         sizedpanel.add(new MessageTextArea(ProgressDialog.unescape("\t")), "South");
         String[] astring;
         if (flag1) {
            astring = new String[]{"Continue", "Switch", flag ? "Quit" : "Cancel"};
         } else {
            astring = new String[]{"OK", flag ? "Quit" : "Cancel"};
         }

         MessageDialog messagedialog = new MessageDialog(null, "Please Choose A Course", sizedpanel, astring);
         coursechooserpanel.dialog = messagedialog;
         messagedialog.setDefaultButtonIndex(0);
         messagedialog.packOnShow = true;
         messagedialog.showAt(MessageDialog.centeredLocation(messagedialog.getPreferredSize()));
         if (flag1 && messagedialog.selectedButton == 0) {
            courseinfo = ServerConnection.getConfiguredCourse();
         } else {
            if (messagedialog.selectedButton != (flag1 ? 1 : 0)) {
               return null;
            }

            courseinfo = coursechooserpanel.getSelectedCourse();
         }
      }

      userinfo.put("institution", courseinfo.institution == null ? "" : courseinfo.institution);
      userinfo.put("term", courseinfo.term == null ? "" : courseinfo.term);
      userinfo.put("className", courseinfo.course == null ? "" : courseinfo.course);
      userinfo.courseUid = courseinfo.courseUid;
      userinfo.dirty = true;
      return courseinfo;
   }

   static String promptForPassword(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator) {
      Boolean obool = ServerConnection.verifyUser(serversession, userinfo, busyindicator, (NetworkTask)null);
      if (obool == null) {
         return null;
      } else if (!obool) {
         if (userinfo instanceof NewUserInfo) {
            Hashtable hashtable = Message.params("sid", (String)userinfo.get("studentID"), "site", (String)userinfo.get("institution"));
            MessageDialog.showMessage(Message.get("not028"), hashtable, null, null);
            return null;
         } else {
            return createPassword(serversession, userinfo, busyindicator);
         }
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         FlexGridLayout flexgridlayout = new FlexGridLayout(2, 1, 0, 0, true, true);
         flexgridlayout.setVgap(1);
         sizedpanel.setLayout(flexgridlayout);
         sizedpanel.add(new LogicLabel("password: "));
         PasswordInputField passwordinputfield;
         sizedpanel.add(passwordinputfield = new PasswordInputField(20));
         String[] astring = new String[]{"OK", "Cancel", "Change"};
         MessageDialog messagedialog = new MessageDialog(null, "Please Enter Your Logic Password", sizedpanel, astring);
         messagedialog.buttons[0].setEnabled(false);
         new PasswordFieldValidator(messagedialog, 0, passwordinputfield);
         passwordinputfield.requestFocus();
         messagedialog.showAt(MessageDialog.centeredLocation(messagedialog.getPreferredSize()));
         String s = null;
         if (messagedialog.selectedButton == 0) {
            s = PasswordEntry.hashPassword(passwordinputfield);
         } else if (messagedialog.selectedButton == 2) {
            s = changePassword(serversession, userinfo, busyindicator);
         }

         return s;
      }
   }

   static String reenterPassword(ServerSession serversession, UserInfo userinfo, PasswordEntry passwordentry, String s) {
      if (passwordentry.newPassword != null) {
         return reenterChangedPassword(serversession, userinfo, passwordentry, s);
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         FlexGridLayout flexgridlayout = new FlexGridLayout(2, 1, 0, 0, true, true);
         flexgridlayout.setVgap(1);
         sizedpanel.setLayout(flexgridlayout);
         sizedpanel.add(new LogicLabel("password: "));
         PasswordInputField passwordinputfield;
         sizedpanel.add(passwordinputfield = new PasswordInputField(20));
         SizedPanel sizedpanel1 = new SizedPanel();
         sizedpanel1.setLayout(new BoxLayout(sizedpanel1, 3));
         sizedpanel1.add(new MessageTextArea(LogicProgram.expandEscapes(s + "\\n" + Message.getText("not056"))));
         sizedpanel1.add(sizedpanel);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(null, "Please Enter Your Logic Password", sizedpanel1, astring);
         messagedialog.buttons[0].setEnabled(false);
         new PasswordFieldValidator(messagedialog, 0, passwordinputfield);
         passwordinputfield.requestFocus();
         messagedialog.showAt(MessageDialog.centeredLocation(messagedialog.getPreferredSize()));
         return messagedialog.selectedButton != 0 ? null : (passwordentry.password = PasswordEntry.hashPassword(passwordinputfield));
      }
   }

   static String createPassword(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator) {
      SizedPanel sizedpanel = new SizedPanel();
      FlexGridLayout flexgridlayout = new FlexGridLayout(2, 2, 0, 0, true, true);
      flexgridlayout.setVgap(1);
      sizedpanel.setLayout(flexgridlayout);
      sizedpanel.add(new LogicLabel("password: "));
      PasswordInputField passwordinputfield;
      sizedpanel.add(passwordinputfield = new PasswordInputField(20));
      sizedpanel.add(new LogicLabel("confirm: "));
      PasswordInputField passwordinputfield1;
      sizedpanel.add(passwordinputfield1 = new PasswordInputField(20));
      sizedpanel.setBorder(new EmptyBorder(10, 5, 10, 5));
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new BorderLayout());
      MessageTextArea messagetextarea = new MessageTextArea(LogicProgram.expandEscapes(Message.getText("not057")));
      sizedpanel1.add(messagetextarea, "North");
      sizedpanel1.add(sizedpanel, "Center");
      sizedpanel1.setBorder(new EmptyBorder(10, 5, 10, 5));
      String[] astring = new String[]{"Create", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(null, "Please Create A Logic Password", sizedpanel1, astring);
      messagedialog.buttons[0].setEnabled(false);
      messagedialog.packOnShow = false;
      new PasswordFieldValidator(messagedialog, 0, passwordinputfield, passwordinputfield1);
      passwordinputfield.requestFocus();
      messagedialog.packOnShow = true;
      messagedialog.showAt(MessageDialog.centeredLocation(messagedialog.getPreferredSize()));
      if (messagedialog.selectedButton != 0) {
         return null;
      } else {
         PasswordEntry passwordentry = new PasswordEntry(passwordinputfield);
         if (busyindicator != null) {
            busyindicator.setBusy(true);
         }

         String s = Institution.forName(userinfo.getInstitution()).getCode();
         Integer integer = ServerConnection.loginUser(
            serversession, userinfo, passwordentry, "Account " + userinfo.getStudentId() + " at " + s + "\nexists and has a different logic password.", 0
         );
         if (busyindicator != null) {
            busyindicator.setBusy(false);
         }

         if (integer == null) {
            return null;
         } else {
            MessageDialog.showMessage(Message.get("not013"), null, null, null);
            return passwordentry.password;
         }
      }
   }

   static String changePassword(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator) {
      SizedPanel sizedpanel = new SizedPanel();
      FlexGridLayout flexgridlayout = new FlexGridLayout(2, 3, 0, 0, true, true);
      flexgridlayout.setVgap(1);
      sizedpanel.setLayout(flexgridlayout);
      sizedpanel.add(new LogicLabel("old password: "));
      PasswordInputField passwordinputfield;
      sizedpanel.add(passwordinputfield = new PasswordInputField(20));
      sizedpanel.add(new LogicLabel("new password: "));
      PasswordInputField passwordinputfield1;
      sizedpanel.add(passwordinputfield1 = new PasswordInputField(20));
      sizedpanel.add(new LogicLabel("confirm: "));
      PasswordInputField passwordinputfield2;
      sizedpanel.add(passwordinputfield2 = new PasswordInputField(20));
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(null, "Please Supply A New Logic Password", sizedpanel, astring);
      messagedialog.buttons[0].setEnabled(false);
      new PasswordFieldValidator(messagedialog, 0, passwordinputfield1, passwordinputfield2, passwordinputfield);
      passwordinputfield.requestFocus();
      messagedialog.showAt(MessageDialog.centeredLocation(messagedialog.getPreferredSize()));
      if (messagedialog.selectedButton != 0) {
         return null;
      } else {
         PasswordEntry passwordentry = new PasswordEntry(passwordinputfield, passwordinputfield1);
         if (busyindicator != null) {
            busyindicator.setBusy(true);
         }

         Integer integer = ServerConnection.loginUser(serversession, userinfo, passwordentry, "The old logic password is incorrect.", 2);
         if (busyindicator != null) {
            busyindicator.setBusy(false);
         }

         if (integer == null) {
            return null;
         } else {
            if (busyindicator != null) {
               busyindicator.setBusy(true);
            }

            boolean flag = ServerConnection.changePassword(serversession, integer, passwordentry.password, passwordentry.newPassword);
            if (busyindicator != null) {
               busyindicator.setBusy(false);
            }

            return flag ? passwordentry.newPassword : null;
         }
      }
   }

   static String reenterChangedPassword(ServerSession serversession, UserInfo userinfo, PasswordEntry passwordentry, String s) {
      SizedPanel sizedpanel = new SizedPanel();
      FlexGridLayout flexgridlayout = new FlexGridLayout(2, 3, 0, 0, true, true);
      flexgridlayout.setVgap(1);
      sizedpanel.setLayout(flexgridlayout);
      sizedpanel.add(new LogicLabel("old password: "));
      PasswordInputField passwordinputfield;
      sizedpanel.add(passwordinputfield = new PasswordInputField(20));
      sizedpanel.add(new LogicLabel("new password: "));
      PasswordInputField passwordinputfield1;
      sizedpanel.add(passwordinputfield1 = new PasswordInputField(20));
      sizedpanel.add(new LogicLabel("confirm: "));
      PasswordInputField passwordinputfield2;
      sizedpanel.add(passwordinputfield2 = new PasswordInputField(20));
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new VerticalStackLayout());
      sizedpanel1.add(new MessageTextArea(LogicProgram.expandEscapes(s + "\\n" + Message.getText("not056"))));
      sizedpanel1.add(sizedpanel);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(null, "Please Supply A New Logic Password", sizedpanel1, astring);
      messagedialog.buttons[0].setEnabled(false);
      new PasswordFieldValidator(messagedialog, 0, passwordinputfield1, passwordinputfield2, passwordinputfield);
      passwordinputfield.requestFocus();
      messagedialog.showAt(MessageDialog.centeredLocation(messagedialog.getPreferredSize()));
      if (messagedialog.selectedButton != 0) {
         return null;
      } else {
         passwordentry.password = PasswordEntry.hashPassword(passwordinputfield);
         return passwordentry.newPassword = PasswordEntry.hashPassword(passwordinputfield1);
      }
   }

   static NewUserInfo createNewUser() {
      return createNewUser(null);
   }

   static NewUserInfo createNewUser(String s) {
      NewUserInfo newuserinfo = new NewUserInfo();

      while (!newuserinfo.isComplete()) {
         if (!newuserinfo.editInfo(s)) {
            return null;
         }
      }

      return newuserinfo;
   }

   static NewUserInfo identifyPrivilegedUser(String s, Hashtable hashtable) {
      ServerSession serversession = ServerConnection.openSession(null);
      if (serversession == null) {
         return null;
      } else {
         NewUserInfo newuserinfo = createNewUser(Message.substitute(Message.getText(s), hashtable));
         PasswordEntry passwordentry = newuserinfo == null ? null : new PasswordEntry(promptForPassword(serversession, newuserinfo, null));
         if (passwordentry != null && passwordentry.password != null) {
            hashtable = Message.params("site", newuserinfo.getInstitution(), "sid", newuserinfo.getStudentId());
            String s1 = Message.substitute(Message.getText("not051"), hashtable);
            Integer integer = ServerConnection.loginUser(serversession, newuserinfo, passwordentry, s1, 1);
            if (integer == null) {
               newuserinfo = null;
            }

            ServerConnection.closeSession(serversession, null);
            return newuserinfo;
         } else {
            ServerConnection.closeSession(serversession, null);
            return null;
         }
      }
   }

   static Boolean verifyAccount(UserInfo userinfo) {
      if (userinfo.demo) {
         return Boolean.TRUE;
      } else {
         ServerSession serversession = ServerConnection.openSession(null);
         Boolean obool = serversession == null ? Boolean.FALSE : ServerConnection.checkRegistration(serversession, userinfo);

         try {
            if (obool == null) {
               if (createPassword(serversession, userinfo, null) == null) {
                  return Boolean.FALSE;
               }
            } else if (!obool) {
               Message message = Message.get("not029");
               ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
               MessageDialog.showMessage(message, null, null, buttonchoicehandler);
               if (buttonchoicehandler.choice != 1 || ServerConnection.deleteWork() != 0) {
                  return Boolean.FALSE;
               }

               return null;
            }

            return Boolean.TRUE;
         } finally {
            if (serversession != null) {
               ServerConnection.closeSession(serversession, null);
            }
         }
      }
   }

   static void showSubmissionResults(Submission submission) {
      showSubmissionResults(submission.succeededNames, submission.failedNames);
   }

   static void showSubmissionResults(String[] astring, String[] astring1) {
      Dimension dimension = new Dimension(24 * LogicProgram.fontSize, 30 * LogicProgram.fontSize);
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new BorderLayout());
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new VerticalStackLayout());
      Hashtable hashtable = Message.params("user", LogicProgram.user.getFullName());
      if (astring1.length == 0 && astring.length == 0) {
         sizedpanel1.add(new LogicLabel("There were no problems to submit."));
         sizedpanel.add(sizedpanel1, "North");
      } else {
         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setLayout(new VerticalStackLayout());
         if (astring1.length == 0) {
            String s = Message.substitute("All problems were successfully\nsubmitted for\n<user>:", hashtable);
            sizedpanel1.add(new LogicTextArea(LogicProgram.expandEscapes(s)));
            int k1 = astring.length;

            for (int i1 = 0; i1 < k1; i1++) {
               sizedpanel2.add(new LogicLabel(astring[i1]));
            }
         } else if (astring.length == 0) {
            sizedpanel1.add(new LogicLabel("No problems could be submitted for"));
            sizedpanel1.add(new LogicLabel(LogicProgram.user.getFullName() + ":"));
            int j1 = astring1.length;

            for (int l = 0; l < j1; l++) {
               sizedpanel2.add(new LogicLabel(astring1[l]));
            }
         } else {
            sizedpanel1.add(new LogicLabel("Submission results for"));
            sizedpanel1.add(new LogicLabel(LogicProgram.user.getFullName() + ":"));
            sizedpanel2.add(new LogicLabel("Succeeded:"));
            int j = astring.length;

            for (int i = 0; i < j; i++) {
               sizedpanel2.add(new LogicLabel("  " + astring[i]));
            }

            sizedpanel2.add(new LogicLabel("Failed:"));
            j = astring1.length;

            for (int k = 0; k < j; k++) {
               sizedpanel2.add(new LogicLabel("  " + astring1[k]));
            }
         }

         sizedpanel.add(sizedpanel1, "North");
         JScrollPane jscrollpane = new JScrollPane(sizedpanel2);
         sizedpanel.add(jscrollpane, "Center");
      }

      String[] astring2 = new String[]{"OK"};
      MessageDialog messagedialog = new MessageDialog(null, "Submission Results", sizedpanel, astring2);
      messagedialog.setSize(dimension);
      messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
   }

   static void showUploadResults(ProblemUpload problemupload) {
      showUploadResults(problemupload.succeededNames, problemupload.failedNames, problemupload.resultText);
   }

   static void showUploadResults(String[] astring, String[] astring1, String s) {
      Dimension dimension = new Dimension(16 * LogicProgram.fontSize, 20 * LogicProgram.fontSize);
      SizedPanel sizedpanel = new SizedPanel();
      boolean flag = false;
      sizedpanel.setLayout(new BorderLayout());
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new VerticalStackLayout());
      if (astring1.length == 0 && astring.length == 0) {
         sizedpanel1.add(new LogicLabel("There were no problems to upload."));
         sizedpanel.add(sizedpanel1, "North");
      } else {
         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setLayout(new VerticalStackLayout());
         if (astring1.length == 0) {
            sizedpanel1.add(new LogicLabel("All problems were successfully uploaded."));
            int k1 = astring.length;

            for (int i1 = 0; i1 < k1; i1++) {
               sizedpanel2.add(new LogicLabel(astring[i1]));
            }

            sizedpanel1.add(sizedpanel2);
         } else if (astring.length == 0) {
            sizedpanel1.add(new LogicLabel("No problems could be uploaded."));
            int j1 = astring1.length;

            for (int l = 0; l < j1; l++) {
               sizedpanel2.add(new LogicLabel(astring1[l]));
            }

            sizedpanel1.add(sizedpanel2);
            flag = true;
         } else {
            sizedpanel1.add(new LogicLabel("Upload results:"));
            sizedpanel2.add(new LogicLabel("Succeeded:"));
            int j = astring.length;

            for (int i = 0; i < j; i++) {
               sizedpanel2.add(new LogicLabel("  " + astring[i]));
            }

            sizedpanel2.add(new LogicLabel("Failed:"));
            j = astring1.length;

            for (int k = 0; k < j; k++) {
               sizedpanel2.add(new LogicLabel("  " + astring1[k]));
            }

            sizedpanel1.add(sizedpanel2);
            flag = true;
         }

         if (flag) {
            Message message = Message.get("not092");
            sizedpanel1.add(new LogicTextArea(LogicProgram.expandEscapes(message.text)));
         }

         sizedpanel.add(sizedpanel1, "North");
         JScrollPane jscrollpane = new JScrollPane(sizedpanel2);
         sizedpanel.add(jscrollpane, "Center");
         if (astring1.length != 0 && s != null) {
            EditableTextPane editabletextpane = new EditableTextPane(LogicProgram.expandEscapes(s));
            editabletextpane.setWrapLines(true);
            editabletextpane.setWrapWords(true);
            editabletextpane.setEnabled(false);
            sizedpanel.add(editabletextpane, "South");
         }
      }

      String[] astring2 = new String[]{"OK"};
      MessageDialog messagedialog = new MessageDialog(null, "Upload Results", sizedpanel, astring2);
      messagedialog.setSize(dimension);
      messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
   }
}
