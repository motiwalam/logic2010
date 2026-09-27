package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class UserSetup implements LogicConstants {
   static Vector verifiedPasswordUsers = new Vector();
   static Vector verifiedRelations = new Vector();

   static boolean hasAccess(String s, String s1) {
      return checkAccess(s, s1) == null;
   }

   static ErrorRef checkAccess(String s, String s1) {
      return checkAccess(s, s1, null);
   }

   static ErrorRef checkAccess(String s, String s1, String s2) {
      if (s1 != null && !LogicProgram.noNetwork) {
         if (verifiedRelations.contains(s1.toLowerCase())) {
            return null;
         } else {
            Hashtable hashtable = Message.params("relation", s1);
            NewUserInfo newuserinfo = AccountManager.identifyPrivilegedUser("not055", hashtable);
            if (newuserinfo == null) {
               return new ErrorRef(null);
            } else if (newuserinfo.hasRelation(s1)) {
               if (!LogicProgram.repeatAuth) {
                  verifiedRelations.addElement(s1.toLowerCase());
               }

               return null;
            } else {
               Toolkit.getDefaultToolkit().beep();
               return new ErrorRef("not053", hashtable);
            }
         }
      } else if (s == null) {
         return new ErrorRef(null);
      } else {
         Credentials credentials;
         if ((credentials = LogicProgram.getCredentials(s)) == null) {
            return new ErrorRef("not012", Message.params("user", s));
         } else if (verifiedPasswordUsers.contains(s.toLowerCase())) {
            return null;
         } else if (credentials.password != null && credentials.password.length() >= 6) {
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new VerticalStackLayout());
            Message message;
            if (s2 != null && (message = Message.get(s2)) != null) {
               jpanel.add(new EditableTextPane(LogicProgram.expandEscapes(message.text)));
            }

            message = Message.get("not050");
            jpanel.add(new LogicLabel(Message.substitute(message.text, Message.params("user", credentials.user))));
            PasswordInputField passwordinputfield;
            jpanel.add(passwordinputfield = new PasswordInputField(20));
            passwordinputfield.setEchoChar('*');
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(null, message.id, jpanel, astring);
            messagedialog.pack();
            messagedialog.setDefaultButtonIndex(0);
            passwordinputfield.requestFocus();
            messagedialog.showAt(null, true);
            if (messagedialog.selectedButton != 0) {
               return new ErrorRef(null);
            } else if (new String(passwordinputfield.getPassword()).equals(credentials.password)) {
               if (!LogicProgram.repeatAuth) {
                  verifiedPasswordUsers.addElement(credentials.user.toLowerCase());
               }

               return null;
            } else {
               Toolkit.getDefaultToolkit().beep();
               return new ErrorRef("not011", Message.params("user", s));
            }
         } else {
            return new ErrorRef("not012", Message.params("user", s));
         }
      }
   }

   static int showErrorChoice(ErrorRef errorref) {
      String s;
      if (errorref != null && (s = errorref.getId()) != null) {
         Message message = Message.get(s);
         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
         MessageDialog.showMessage(message, errorref.getParams(), null, buttonchoicehandler);
         return buttonchoicehandler.choice;
      } else {
         return -1;
      }
   }

   static boolean confirmSubmitAll(String s) {
      if (LogicProgram.noNetwork) {
         return false;
      } else {
         Message message = Message.get("not069");
         Hashtable hashtable = Message.params("module", s);
         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
         MessageDialog.showMessage(message, hashtable, null, buttonchoicehandler);
         return buttonchoicehandler.choice == 0;
      }
   }

   static Boolean confirmIdentity(UserInfo userinfo, boolean flag) {
      Message message = Message.get(flag ? "not035" : "not022");
      Hashtable hashtable = userinfo.getMessageParams();
      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
      MessageDialog.showMessage(message, hashtable, null, buttonchoicehandler);
      if (buttonchoicehandler.choice < 0 || buttonchoicehandler.choice > (flag ? 1 : 2)) {
         return Boolean.FALSE;
      } else if (buttonchoicehandler.choice == (flag ? 0 : 1)) {
         ServerConnection.deleteWork();
         return null;
      } else if ((flag || buttonchoicehandler.choice != 2) && userinfo.getCourse() != null) {
         return Boolean.TRUE;
      } else {
         String s = !flag && buttonchoicehandler.choice == 2 ? "not044" : "not045";
         return AccountManager.chooseCourse(userinfo, s, true, false) == null ? Boolean.FALSE : Boolean.TRUE;
      }
   }

   static Boolean confirmIdentityAndPreferences(UserInfo userinfo, boolean flag, boolean flag1) {
      Message message;
      if (flag1) {
         message = Message.get("not094");
      } else if (flag) {
         message = Message.get("not035");
      } else {
         message = Message.get("not022");
      }

      Hashtable hashtable = userinfo.getMessageParams();
      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
      String s = message.text;
      if (hashtable != null) {
         s = Message.substitute(s, hashtable);
      }

      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLimitWidth(LogicProgram.screenSize.width * 3 / 4);
      sizedpanel.setMaximumOnly(true);
      sizedpanel.setLayout(new BorderLayout());
      sizedpanel.setBackground(LogicConstants.bruinAsh);
      EditableTextPane editabletextpane = new EditableTextPane(LogicProgram.expandEscapes(s));
      editabletextpane.setWrapLines(true);
      editabletextpane.setWrapWords(true);
      editabletextpane.setEnabled(false);
      editabletextpane.setDisabledTextColor(LogicConstants.bruinBlack);
      editabletextpane.setBackground(LogicConstants.bruinAsh);
      sizedpanel.add(editabletextpane, "North");
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new FlowLayout());
      sizedpanel1.setBackground(LogicConstants.bruinAsh);
      boolean flag3 = LogicProgram.monochrome;
      sizedpanel1.add(new FontLabel("Monochrome: "));
      ScaledCheckBox scaledcheckbox;
      sizedpanel1.add(scaledcheckbox = new ScaledCheckBox(""));
      scaledcheckbox.setSelected(flag3);
      sizedpanel1.add(new SizedSeparator(LogicProgram.fontSize, 2, true, Color.black));
      sizedpanel1.add(new FontLabel("Set Font Size: "));
      String s2 = new Integer(LogicProgram.fontSize).toString();
      EditableTextPane editabletextpane1 = new EditableTextPane(s2);
      editabletextpane1.setBackground(LogicConstants.bruinWhite);
      sizedpanel1.add(editabletextpane1);
      sizedpanel.add(sizedpanel1, "West");
      String[] astring = new String[]{"OK"};
      int i = 0;
      if (buttonchoicehandler != null) {
         astring = buttonchoicehandler.getLabels();
         i = buttonchoicehandler.getDefaultIndex();
      }

      MessageDialog messagedialog = new MessageDialog(null, message.id, sizedpanel, astring);
      messagedialog.pack();
      messagedialog.addHandler(buttonchoicehandler);
      messagedialog.setDefaultButtonIndex(i);
      if (i != -1) {
         messagedialog.buttons[i].requestFocus();
      }

      messagedialog.showAt(null);
      boolean flag2 = scaledcheckbox.isSelected();
      if (flag2 != flag3) {
         LogicProgram.workPrefs.putPref("monochrome", flag2 ? "true" : "false");
         LogicProgram.workPrefs.save(LogicProgram.workPrefsFile);
         LogicProgram.applyColorPrefs();
      }

      String s1 = editabletextpane1.getText().trim();
      if (!s1.equals(s2)) {
         int j = LogicProgram.parseFontSize(s1);
         if (j < 6) {
            s1 = "6";
         } else if (j > ProgressDialog.getDefaultFontSize() * 5 / 3) {
            s1 = "5/3";
         }

         LogicProgram.prefs.putPref("font size", s1);
         LogicProgram.prefs.save(LogicProgram.prefsFile);
         LogicProgram.applyPrefsFontSize();
      }

      if (flag1) {
         return buttonchoicehandler.choice != 0 ? Boolean.FALSE : Boolean.TRUE;
      } else if (buttonchoicehandler.choice < 0 || buttonchoicehandler.choice > (flag ? 1 : 2)) {
         return Boolean.FALSE;
      } else if (buttonchoicehandler.choice == (flag ? 0 : 1)) {
         ServerConnection.deleteWork();
         return null;
      } else if ((flag || buttonchoicehandler.choice != 2) && userinfo.getCourse() != null) {
         return Boolean.TRUE;
      } else {
         String s3 = !flag && buttonchoicehandler.choice == 2 ? "not044" : "not045";
         return AccountManager.chooseCourse(userinfo, s3, true, false) == null ? Boolean.FALSE : Boolean.TRUE;
      }
   }

   static File chooseInstalledVersion(File file1, File file2) {
      Hashtable hashtable = LogicProgram.readLinks(file1, false);
      Hashtable hashtable1 = LogicProgram.readLinks(file2, false);
      if (hashtable == null || LogicProgram.getValue(hashtable, "textDir", null) == null) {
         hashtable = null;
      }

      if (hashtable1 == null || LogicProgram.getValue(hashtable1, "textDir", null) == null) {
         hashtable1 = null;
      }

      if (hashtable == null && hashtable1 == null) {
         return null;
      } else {
         Message message = Message.get("not087");
         String s = hashtable == null ? "Student.Local" : (hashtable1 == null ? "Student.Instructor" : "Student.Instructor.Local");
         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(s);
         MessageDialog.showMessage(message, null, null, buttonchoicehandler);
         if (buttonchoicehandler.choice >= 0) {
            String s1 = buttonchoicehandler.labels[buttonchoicehandler.choice];
            if ("Student".equals(s1)) {
               return null;
            }

            if ("Instructor".equals(s1)) {
               return file1;
            }

            if ("Local".equals(s1)) {
               return file2;
            }

            if ("Remove".equals(s1)) {
               deleteRecursively(file1);
               deleteRecursively(file2);
            }
         }

         return null;
      }
   }

   static boolean deleteRecursively(File file1) {
      boolean flag = true;
      if (file1 != null && file1.exists()) {
         if (file1.isDirectory()) {
            String[] astring = file1.list();
            int i = astring == null ? 0 : astring.length;

            for (int j = 0; j < i; j++) {
               if (!deleteRecursively(new File(file1, astring[j]))) {
                  flag = false;
               }
            }
         }

         if (!file1.delete()) {
            flag = false;
         }

         return flag;
      } else {
         return flag;
      }
   }

   static Boolean askContinueOrDelete(UserInfo userinfo) {
      Message message = Message.get("not074");
      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
      MessageDialog.showMessage(message, null, null, buttonchoicehandler);
      if (buttonchoicehandler.choice == 0) {
         return ServerConnection.deleteWork() == 0 ? null : Boolean.FALSE;
      } else {
         return buttonchoicehandler.choice == 1 ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   static boolean offerDeleteWork() {
      Message message = Message.get("not078");
      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
      MessageDialog.showMessage(message, null, null, buttonchoicehandler);
      return buttonchoicehandler.choice != 0 ? false : ServerConnection.deleteWork(true) == 0;
   }

   static boolean askRestoreOrQuit(UserInfo userinfo) {
      Hashtable hashtable = Message.params("institution", userinfo.getInstitution(), "studentID", userinfo.getStudentId());
      Message message = Message.get("not075");
      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
      MessageDialog.showMessage(message, hashtable, null, buttonchoicehandler);
      return buttonchoicehandler.choice != 0;
   }

   static Boolean askRegister(UserInfo userinfo) {
      Hashtable hashtable = Message.params("institution", userinfo.getInstitution(), "studentID", userinfo.getStudentId());
      Message message = Message.get("not076");
      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
      MessageDialog.showMessage(message, hashtable, null, buttonchoicehandler);
      if (buttonchoicehandler.choice == 0) {
         return Boolean.TRUE;
      } else {
         return buttonchoicehandler.choice == 1 ? Boolean.FALSE : null;
      }
   }

   static boolean offerRegisterOrRestore(String s, String s1, boolean flag) {
      boolean flag1 = !LogicProgram.noNetwork && (s != null || s1 != null);
      if (!flag && !flag1) {
         return false;
      } else {
         Message message = Message.get(flag1 ? (flag ? "not019" : "not065") : "not038");
         if (LogicProgram.noNetwork) {
            message = Message.get("not088");
         }

         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
         MessageDialog.showMessage(message, null, null, buttonchoicehandler);
         if (flag && buttonchoicehandler.choice == 0) {
            ServerConnection.deleteWork(false);
            return true;
         } else if (flag1 && buttonchoicehandler.choice == (flag ? 1 : 0)) {
            ServerConnection.deleteWork(false);
            return ServerConnection.restoreWork(s, s1, null);
         } else {
            return false;
         }
      }
   }

   static boolean offerBackupBeforeQuit(BusyIndicator busyindicator) {
      boolean flag = !LogicProgram.noNetwork && LogicProgram.backupNeeded && LogicProgram.backupName != null;
      boolean flag1 = LogicProgram.copyNeeded && LogicProgram.copyDir != null;
      if (!flag && !flag1) {
         return true;
      } else {
         Message message;
         if (flag && !flag1) {
            message = Message.get("not067");
         } else if (!flag && flag1) {
            message = Message.get("not068");
         } else {
            message = Message.get("not031");
         }

         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
         MessageDialog.showMessage(message, null, null, buttonchoicehandler);
         int i = buttonchoicehandler.choice;
         if (i == -1) {
            return false;
         } else if (flag && flag1 ? i != 3 : i != 1) {
            if (flag && flag1 ? i != 4 : i != 2) {
               boolean flag2 = true;
               boolean flag3 = true;
               if (flag && (i == 0 || flag1 && i == 2)) {
                  flag2 = ServerConnection.backupWork(LogicProgram.backupName, busyindicator);
               }

               if (flag1 && (flag ? i == 1 || i == 2 : i == 0)) {
                  flag3 = ServerConnection.copyWork(LogicProgram.workDir, LogicProgram.copyDir);
               }

               return flag2 && flag3;
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   static boolean chooseBackup(BackupRequest backuprequest) {
      int i = backuprequest.backups == null ? 0 : backuprequest.backups.length;
      Vector vector = new Vector();
      ProblemListView problemlistview = new ProblemListView(false);

      for (int j = 0; j < i; j++) {
         BackupEntry backupentry = backuprequest.backups[j];
         if (backupentry.isValid() && backupentry.key.equalsIgnoreCase(backuprequest.backupKey)) {
            vector.addElement(backupentry);
            problemlistview.addItem(new LogicLabel(backupentry.date));
         }
      }

      if ((i = vector.size()) == 0) {
         MessageDialog.showMessage(Message.get("not014"), null, null, null);
         return false;
      } else {
         JScrollPane jscrollpane = new JScrollPane();
         jscrollpane.setViewportView(problemlistview);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(null, "Please Choose a Backup", jscrollpane, astring);
         problemlistview.setDialog(messagedialog, 0);
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 15 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.setBoundsKey("bakChosen");
         problemlistview.setSelectedIndex(i - 1);
         problemlistview.requestFocus();
         messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
         if (messagedialog.selectedButton != 0) {
            return false;
         } else {
            int k = problemlistview.getSelectedIndex();
            if (k == -1) {
               return false;
            } else {
               backuprequest.selectedBackupId = ((BackupEntry)vector.elementAt(k)).backupId;
               return true;
            }
         }
      }
   }

   static boolean confirmDeleteWork(String s) {
      Message message = Message.get(s);
      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
      MessageDialog.showMessage(message, null, null, buttonchoicehandler);
      int i = buttonchoicehandler.choice;
      return i == 0 && ServerConnection.deleteWork(true) == 0;
   }

   static String stripFirstWord(String s) {
      if (s == null) {
         return null;
      } else {
         int i = s.indexOf(" ");
         if (i != -1) {
            s = s.substring(i);
         }

         return s.trim();
      }
   }

   static String stripPrefix(String s, String s1) {
      if (s == null) {
         return null;
      } else {
         if (s.toUpperCase().startsWith(s1.toUpperCase())) {
            s = s.substring(s1.length());
         }

         return s.trim();
      }
   }
}
