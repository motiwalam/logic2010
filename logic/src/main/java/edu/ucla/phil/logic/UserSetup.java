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
   static Vector f1396 = new Vector();
   static Vector f1397 = new Vector();

   static boolean m2101(String s, String s1) {
      return m2102(s, s1) == null;
   }

   static ErrorRef m2102(String s, String s1) {
      return m2103(s, s1, null);
   }

   static ErrorRef m2103(String s, String s1, String s2) {
      if (s1 != null && !LogicProgram.noNetwork) {
         if (f1397.contains(s1.toLowerCase())) {
            return null;
         } else {
            Hashtable hashtable = Message.params("relation", s1);
            NewUserInfo newuserinfo = AccountManager.m1871("not055", hashtable);
            if (newuserinfo == null) {
               return new ErrorRef(null);
            } else if (newuserinfo.m1171(s1)) {
               if (!LogicProgram.f589) {
                  f1397.addElement(s1.toLowerCase());
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
         } else if (f1396.contains(s.toLowerCase())) {
            return null;
         } else if (credentials.password != null && credentials.password.length() >= 6) {
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_m_A());
            Message message;
            if (s2 != null && (message = Message.get(s2)) != null) {
               jpanel.add(new EditableTextPane(LogicProgram.m1004(message.text)));
            }

            message = Message.get("not050");
            jpanel.add(new C_ZE(Message.substitute(message.text, Message.params("user", credentials.user))));
            C_q_ c_q_;
            jpanel.add(c_q_ = new C_q_(20));
            c_q_.setEchoChar('*');
            String[] astring = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(null, message.id, jpanel, astring);
            messagedialog.pack();
            messagedialog.m1314(0);
            c_q_.requestFocus();
            messagedialog.m1323(null, true);
            if (messagedialog.f790 != 0) {
               return new ErrorRef(null);
            } else if (new String(c_q_.getPassword()).equals(credentials.password)) {
               if (!LogicProgram.f589) {
                  f1396.addElement(credentials.user.toLowerCase());
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

   static int m2104(ErrorRef errorref) {
      String s;
      if (errorref != null && (s = errorref.m716()) != null) {
         Message message = Message.get(s);
         C_b_E c_b_e = new C_b_E(message.buttons);
         MessageDialog.showMessage(message, errorref.m717(), null, c_b_e);
         return c_b_e.f1027;
      } else {
         return -1;
      }
   }

   static boolean m2105(String s) {
      if (LogicProgram.noNetwork) {
         return false;
      } else {
         Message message = Message.get("not069");
         Hashtable hashtable = Message.params("module", s);
         C_b_E c_b_e = new C_b_E(message.buttons);
         MessageDialog.showMessage(message, hashtable, null, c_b_e);
         return c_b_e.f1027 == 0;
      }
   }

   static Boolean m2106(UserInfo userinfo, boolean flag) {
      Message message = Message.get(flag ? "not035" : "not022");
      Hashtable hashtable = userinfo.m1165();
      C_b_E c_b_e = new C_b_E(message.buttons);
      MessageDialog.showMessage(message, hashtable, null, c_b_e);
      if (c_b_e.f1027 < 0 || c_b_e.f1027 > (flag ? 1 : 2)) {
         return Boolean.FALSE;
      } else if (c_b_e.f1027 == (flag ? 0 : 1)) {
         ServerConnection.m902();
         return null;
      } else if ((flag || c_b_e.f1027 != 2) && userinfo.m1163() != null) {
         return Boolean.TRUE;
      } else {
         String s = !flag && c_b_e.f1027 == 2 ? "not044" : "not045";
         return AccountManager.m1863(userinfo, s, true, false) == null ? Boolean.FALSE : Boolean.TRUE;
      }
   }

   static Boolean m2107(UserInfo userinfo, boolean flag, boolean flag1) {
      Message message;
      if (flag1) {
         message = Message.get("not094");
      } else if (flag) {
         message = Message.get("not035");
      } else {
         message = Message.get("not022");
      }

      Hashtable hashtable = userinfo.m1165();
      C_b_E c_b_e = new C_b_E(message.buttons);
      String s = message.text;
      if (hashtable != null) {
         s = Message.substitute(s, hashtable);
      }

      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.m934(LogicProgram.f541.width * 3 / 4);
      sizedpanel.m937(true);
      sizedpanel.setLayout(new BorderLayout());
      sizedpanel.setBackground(LogicConstants.bruinAsh);
      EditableTextPane editabletextpane = new EditableTextPane(LogicProgram.m1004(s));
      editabletextpane.m1787(true);
      editabletextpane.m1789(true);
      editabletextpane.setEnabled(false);
      editabletextpane.setDisabledTextColor(LogicConstants.bruinBlack);
      editabletextpane.setBackground(LogicConstants.bruinAsh);
      sizedpanel.add(editabletextpane, "North");
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new FlowLayout());
      sizedpanel1.setBackground(LogicConstants.bruinAsh);
      boolean flag3 = LogicProgram.f603;
      sizedpanel1.add(new C_d_D("Monochrome: "));
      C_NE c_ne;
      sizedpanel1.add(c_ne = new C_NE(""));
      c_ne.setSelected(flag3);
      sizedpanel1.add(new C_CC(LogicProgram.fontSize, 2, true, Color.black));
      sizedpanel1.add(new C_d_D("Set Font Size: "));
      String s2 = new Integer(LogicProgram.fontSize).toString();
      EditableTextPane editabletextpane1 = new EditableTextPane(s2);
      editabletextpane1.setBackground(LogicConstants.bruinWhite);
      sizedpanel1.add(editabletextpane1);
      sizedpanel.add(sizedpanel1, "West");
      String[] astring = new String[]{"OK"};
      int i = 0;
      if (c_b_e != null) {
         astring = c_b_e.m445();
         i = c_b_e.m446();
      }

      MessageDialog messagedialog = new MessageDialog(null, message.id, sizedpanel, astring);
      messagedialog.pack();
      messagedialog.m1315(c_b_e);
      messagedialog.m1314(i);
      if (i != -1) {
         messagedialog.f791[i].requestFocus();
      }

      messagedialog.m1322(null);
      boolean flag2 = c_ne.isSelected();
      if (flag2 != flag3) {
         LogicProgram.workPrefs.put("monochrome", flag2 ? "true" : "false");
         LogicProgram.workPrefs.m2155(LogicProgram.f558);
         LogicProgram.m1059();
      }

      String s1 = editabletextpane1.getText().trim();
      if (!s1.equals(s2)) {
         int j = LogicProgram.m1028(s1);
         if (j < 6) {
            s1 = "6";
         } else if (j > ProgressDialog.m1288() * 5 / 3) {
            s1 = "5/3";
         }

         LogicProgram.prefs.put("font size", s1);
         LogicProgram.prefs.m2155(LogicProgram.f557);
         LogicProgram.m1058();
      }

      if (flag1) {
         return c_b_e.f1027 != 0 ? Boolean.FALSE : Boolean.TRUE;
      } else if (c_b_e.f1027 < 0 || c_b_e.f1027 > (flag ? 1 : 2)) {
         return Boolean.FALSE;
      } else if (c_b_e.f1027 == (flag ? 0 : 1)) {
         ServerConnection.m902();
         return null;
      } else if ((flag || c_b_e.f1027 != 2) && userinfo.m1163() != null) {
         return Boolean.TRUE;
      } else {
         String s3 = !flag && c_b_e.f1027 == 2 ? "not044" : "not045";
         return AccountManager.m1863(userinfo, s3, true, false) == null ? Boolean.FALSE : Boolean.TRUE;
      }
   }

   static File m2108(File file1, File file2) {
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
         C_b_E c_b_e = new C_b_E(s);
         MessageDialog.showMessage(message, null, null, c_b_e);
         if (c_b_e.f1027 >= 0) {
            String s1 = c_b_e.f272[c_b_e.f1027];
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
               m2109(file1);
               m2109(file2);
            }
         }

         return null;
      }
   }

   static boolean m2109(File file1) {
      boolean flag = true;
      if (file1 != null && file1.exists()) {
         if (file1.isDirectory()) {
            String[] astring = file1.list();
            int i = astring == null ? 0 : astring.length;

            for (int j = 0; j < i; j++) {
               if (!m2109(new File(file1, astring[j]))) {
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

   static Boolean m2110(UserInfo userinfo) {
      Message message = Message.get("not074");
      C_b_E c_b_e = new C_b_E(message.buttons);
      MessageDialog.showMessage(message, null, null, c_b_e);
      if (c_b_e.f1027 == 0) {
         return ServerConnection.m902() == 0 ? null : Boolean.FALSE;
      } else {
         return c_b_e.f1027 == 1 ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   static boolean m2111() {
      Message message = Message.get("not078");
      C_b_E c_b_e = new C_b_E(message.buttons);
      MessageDialog.showMessage(message, null, null, c_b_e);
      return c_b_e.f1027 != 0 ? false : ServerConnection.m903(true) == 0;
   }

   static boolean m2112(UserInfo userinfo) {
      Hashtable hashtable = Message.params("institution", userinfo.getInstitution(), "studentID", userinfo.getStudentId());
      Message message = Message.get("not075");
      C_b_E c_b_e = new C_b_E(message.buttons);
      MessageDialog.showMessage(message, hashtable, null, c_b_e);
      return c_b_e.f1027 != 0;
   }

   static Boolean m2113(UserInfo userinfo) {
      Hashtable hashtable = Message.params("institution", userinfo.getInstitution(), "studentID", userinfo.getStudentId());
      Message message = Message.get("not076");
      C_b_E c_b_e = new C_b_E(message.buttons);
      MessageDialog.showMessage(message, hashtable, null, c_b_e);
      if (c_b_e.f1027 == 0) {
         return Boolean.TRUE;
      } else {
         return c_b_e.f1027 == 1 ? Boolean.FALSE : null;
      }
   }

   static boolean m2114(String s, String s1, boolean flag) {
      boolean flag1 = !LogicProgram.noNetwork && (s != null || s1 != null);
      if (!flag && !flag1) {
         return false;
      } else {
         Message message = Message.get(flag1 ? (flag ? "not019" : "not065") : "not038");
         if (LogicProgram.noNetwork) {
            message = Message.get("not088");
         }

         C_b_E c_b_e = new C_b_E(message.buttons);
         MessageDialog.showMessage(message, null, null, c_b_e);
         if (flag && c_b_e.f1027 == 0) {
            ServerConnection.m903(false);
            return true;
         } else if (flag1 && c_b_e.f1027 == (flag ? 1 : 0)) {
            ServerConnection.m903(false);
            return ServerConnection.m899(s, s1, null);
         } else {
            return false;
         }
      }
   }

   static boolean m2115(BusyIndicator busyindicator) {
      boolean flag = !LogicProgram.noNetwork && LogicProgram.f581 && LogicProgram.f567 != null;
      boolean flag1 = LogicProgram.f582 && LogicProgram.f551 != null;
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

         C_b_E c_b_e = new C_b_E(message.buttons);
         MessageDialog.showMessage(message, null, null, c_b_e);
         int i = c_b_e.f1027;
         if (i == -1) {
            return false;
         } else if (flag && flag1 ? i != 3 : i != 1) {
            if (flag && flag1 ? i != 4 : i != 2) {
               boolean flag2 = true;
               boolean flag3 = true;
               if (flag && (i == 0 || flag1 && i == 2)) {
                  flag2 = ServerConnection.m896(LogicProgram.f567, busyindicator);
               }

               if (flag1 && (flag ? i == 1 || i == 2 : i == 0)) {
                  flag3 = ServerConnection.m900(LogicProgram.workDir, LogicProgram.f551);
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

   static boolean m2116(C_XA c_xa) {
      int i = c_xa.f872 == null ? 0 : c_xa.f872.length;
      Vector vector = new Vector();
      ProblemListView problemlistview = new ProblemListView(false);

      for (int j = 0; j < i; j++) {
         C_w_D c_w_d = c_xa.f872[j];
         if (c_w_d.m2159() && c_w_d.f1429.equalsIgnoreCase(c_xa.f868)) {
            vector.addElement(c_w_d);
            problemlistview.m1526(new C_ZE(c_w_d.f1430));
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
         problemlistview.m1528(messagedialog, 0);
         Dimension dimension = new Dimension(20 * LogicProgram.fontSize, 15 * LogicProgram.fontSize);
         messagedialog.setSize(dimension);
         messagedialog.m1317("bakChosen");
         problemlistview.setSelectedIndex(i - 1);
         problemlistview.requestFocus();
         messagedialog.m1323(MessageDialog.m1321(dimension), true);
         if (messagedialog.f790 != 0) {
            return false;
         } else {
            int k = problemlistview.getSelectedIndex();
            if (k == -1) {
               return false;
            } else {
               c_xa.f871 = ((C_w_D)vector.elementAt(k)).f1431;
               return true;
            }
         }
      }
   }

   static boolean m2117(String s) {
      Message message = Message.get(s);
      C_b_E c_b_e = new C_b_E(message.buttons);
      MessageDialog.showMessage(message, null, null, c_b_e);
      int i = c_b_e.f1027;
      return i == 0 && ServerConnection.m903(true) == 0;
   }

   static String m2118(String s) {
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

   static String m2119(String s, String s1) {
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
