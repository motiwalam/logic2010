package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

class AccountManager implements LogicConstants {
   static CourseInfo m1863(UserInfo userinfo, String s, boolean flag, boolean flag1) {
      CourseInfo courseinfo;
      if (flag && ServerConnection.m859()) {
         courseinfo = ServerConnection.m861();
         if (courseinfo == null) {
            MessageDialog.showMessage(Message.get("not079"), null, null, null);
            return null;
         }
      } else {
         String s1 = Message.substitute(Message.getText(s), userinfo.m1165());
         s1 = Message.substitute(s1, ServerConnection.m850());
         C_n_E c_n_e = new C_n_E(userinfo, flag);
         if (c_n_e.f1308 == null) {
            return null;
         }

         if (c_n_e.f1308.length == 0) {
            Message message = Message.get(userinfo instanceof NewUserInfo ? "not042" : "not043");
            MessageDialog.showMessage(message, Message.params("site", userinfo.getInstitution()), null, null);
            return null;
         }

         SizedPanel sizedpanel = new SizedPanel();
         sizedpanel.setLayout(new BorderLayout());
         C_s_B c_s_b = new C_s_B(ProgressDialog.m1292(s1));
         c_s_b.setLineWrap(false);
         sizedpanel.add(c_s_b, "North");
         sizedpanel.add(c_n_e, "Center");
         sizedpanel.add(new C_s_B(ProgressDialog.m1292("\t")), "South");
         String[] astring;
         if (flag1) {
            astring = new String[]{"Continue", "Switch", flag ? "Quit" : "Cancel"};
         } else {
            astring = new String[]{"OK", flag ? "Quit" : "Cancel"};
         }

         MessageDialog messagedialog = new MessageDialog(null, "Please Choose A Course", sizedpanel, astring);
         c_n_e.f1312 = messagedialog;
         messagedialog.m1314(0);
         messagedialog.f789 = true;
         messagedialog.m1322(MessageDialog.m1321(messagedialog.getPreferredSize()));
         if (flag1 && messagedialog.f790 == 0) {
            courseinfo = ServerConnection.m861();
         } else {
            if (messagedialog.f790 != (flag1 ? 1 : 0)) {
               return null;
            }

            courseinfo = c_n_e.m1966();
         }
      }

      userinfo.put("institution", courseinfo.f620 == null ? "" : courseinfo.f620);
      userinfo.put("term", courseinfo.f621 == null ? "" : courseinfo.f621);
      userinfo.put("className", courseinfo.f622 == null ? "" : courseinfo.f622);
      userinfo.f665 = courseinfo.f623;
      userinfo.dirty = true;
      return courseinfo;
   }

   static String m1864(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator) {
      Boolean obool = ServerConnection.m906(serversession, userinfo, busyindicator, (NetworkTask)null);
      if (obool == null) {
         return null;
      } else if (!obool) {
         if (userinfo instanceof NewUserInfo) {
            Hashtable hashtable = Message.params("sid", (String)userinfo.get("studentID"), "site", (String)userinfo.get("institution"));
            MessageDialog.showMessage(Message.get("not028"), hashtable, null, null);
            return null;
         } else {
            return m1866(serversession, userinfo, busyindicator);
         }
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         C_QF c_qf = new C_QF(2, 1, 0, 0, true, true);
         c_qf.setVgap(1);
         sizedpanel.setLayout(c_qf);
         sizedpanel.add(new C_ZE("password: "));
         C_q_ c_q_;
         sizedpanel.add(c_q_ = new C_q_(20));
         String[] astring = new String[]{"OK", "Cancel", "Change"};
         MessageDialog messagedialog = new MessageDialog(null, "Please Enter Your Logic Password", sizedpanel, astring);
         messagedialog.f791[0].setEnabled(false);
         new C_UE(messagedialog, 0, c_q_);
         c_q_.requestFocus();
         messagedialog.m1322(MessageDialog.m1321(messagedialog.getPreferredSize()));
         String s = null;
         if (messagedialog.f790 == 0) {
            s = C__F.m1588(c_q_);
         } else if (messagedialog.f790 == 2) {
            s = m1867(serversession, userinfo, busyindicator);
         }

         return s;
      }
   }

   static String m1865(ServerSession serversession, UserInfo userinfo, C__F c__f, String s) {
      if (c__f.f933 != null) {
         return m1868(serversession, userinfo, c__f, s);
      } else {
         SizedPanel sizedpanel = new SizedPanel();
         C_QF c_qf = new C_QF(2, 1, 0, 0, true, true);
         c_qf.setVgap(1);
         sizedpanel.setLayout(c_qf);
         sizedpanel.add(new C_ZE("password: "));
         C_q_ c_q_;
         sizedpanel.add(c_q_ = new C_q_(20));
         SizedPanel sizedpanel1 = new SizedPanel();
         sizedpanel1.setLayout(new BoxLayout(sizedpanel1, 3));
         sizedpanel1.add(new C_s_B(LogicProgram.m1004(s + "\\n" + Message.getText("not056"))));
         sizedpanel1.add(sizedpanel);
         String[] astring = new String[]{"OK", "Cancel"};
         MessageDialog messagedialog = new MessageDialog(null, "Please Enter Your Logic Password", sizedpanel1, astring);
         messagedialog.f791[0].setEnabled(false);
         new C_UE(messagedialog, 0, c_q_);
         c_q_.requestFocus();
         messagedialog.m1322(MessageDialog.m1321(messagedialog.getPreferredSize()));
         return messagedialog.f790 != 0 ? null : (c__f.f932 = C__F.m1588(c_q_));
      }
   }

   static String m1866(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator) {
      SizedPanel sizedpanel = new SizedPanel();
      C_QF c_qf = new C_QF(2, 2, 0, 0, true, true);
      c_qf.setVgap(1);
      sizedpanel.setLayout(c_qf);
      sizedpanel.add(new C_ZE("password: "));
      C_q_ c_q_;
      sizedpanel.add(c_q_ = new C_q_(20));
      sizedpanel.add(new C_ZE("confirm: "));
      C_q_ c_q_1;
      sizedpanel.add(c_q_1 = new C_q_(20));
      sizedpanel.setBorder(new EmptyBorder(10, 5, 10, 5));
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new BorderLayout());
      C_s_B c_s_b = new C_s_B(LogicProgram.m1004(Message.getText("not057")));
      sizedpanel1.add(c_s_b, "North");
      sizedpanel1.add(sizedpanel, "Center");
      sizedpanel1.setBorder(new EmptyBorder(10, 5, 10, 5));
      String[] astring = new String[]{"Create", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(null, "Please Create A Logic Password", sizedpanel1, astring);
      messagedialog.f791[0].setEnabled(false);
      messagedialog.f789 = false;
      new C_UE(messagedialog, 0, c_q_, c_q_1);
      c_q_.requestFocus();
      messagedialog.f789 = true;
      messagedialog.m1322(MessageDialog.m1321(messagedialog.getPreferredSize()));
      if (messagedialog.f790 != 0) {
         return null;
      } else {
         C__F c__f = new C__F(c_q_);
         if (busyindicator != null) {
            busyindicator.m2162(true);
         }

         String s = Institution.m1293(userinfo.getInstitution()).m1294();
         Integer integer = ServerConnection.m908(
            serversession, userinfo, c__f, "Account " + userinfo.getStudentId() + " at " + s + "\nexists and has a different logic password.", 0
         );
         if (busyindicator != null) {
            busyindicator.m2162(false);
         }

         if (integer == null) {
            return null;
         } else {
            MessageDialog.showMessage(Message.get("not013"), null, null, null);
            return c__f.f932;
         }
      }
   }

   static String m1867(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator) {
      SizedPanel sizedpanel = new SizedPanel();
      C_QF c_qf = new C_QF(2, 3, 0, 0, true, true);
      c_qf.setVgap(1);
      sizedpanel.setLayout(c_qf);
      sizedpanel.add(new C_ZE("old password: "));
      C_q_ c_q_;
      sizedpanel.add(c_q_ = new C_q_(20));
      sizedpanel.add(new C_ZE("new password: "));
      C_q_ c_q_1;
      sizedpanel.add(c_q_1 = new C_q_(20));
      sizedpanel.add(new C_ZE("confirm: "));
      C_q_ c_q_2;
      sizedpanel.add(c_q_2 = new C_q_(20));
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(null, "Please Supply A New Logic Password", sizedpanel, astring);
      messagedialog.f791[0].setEnabled(false);
      new C_UE(messagedialog, 0, c_q_1, c_q_2, c_q_);
      c_q_.requestFocus();
      messagedialog.m1322(MessageDialog.m1321(messagedialog.getPreferredSize()));
      if (messagedialog.f790 != 0) {
         return null;
      } else {
         C__F c__f = new C__F(c_q_, c_q_1);
         if (busyindicator != null) {
            busyindicator.m2162(true);
         }

         Integer integer = ServerConnection.m908(serversession, userinfo, c__f, "The old logic password is incorrect.", 2);
         if (busyindicator != null) {
            busyindicator.m2162(false);
         }

         if (integer == null) {
            return null;
         } else {
            if (busyindicator != null) {
               busyindicator.m2162(true);
            }

            boolean flag = ServerConnection.m915(serversession, integer, c__f.f932, c__f.f933);
            if (busyindicator != null) {
               busyindicator.m2162(false);
            }

            return flag ? c__f.f933 : null;
         }
      }
   }

   static String m1868(ServerSession serversession, UserInfo userinfo, C__F c__f, String s) {
      SizedPanel sizedpanel = new SizedPanel();
      C_QF c_qf = new C_QF(2, 3, 0, 0, true, true);
      c_qf.setVgap(1);
      sizedpanel.setLayout(c_qf);
      sizedpanel.add(new C_ZE("old password: "));
      C_q_ c_q_;
      sizedpanel.add(c_q_ = new C_q_(20));
      sizedpanel.add(new C_ZE("new password: "));
      C_q_ c_q_1;
      sizedpanel.add(c_q_1 = new C_q_(20));
      sizedpanel.add(new C_ZE("confirm: "));
      C_q_ c_q_2;
      sizedpanel.add(c_q_2 = new C_q_(20));
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new C_m_A());
      sizedpanel1.add(new C_s_B(LogicProgram.m1004(s + "\\n" + Message.getText("not056"))));
      sizedpanel1.add(sizedpanel);
      String[] astring = new String[]{"OK", "Cancel"};
      MessageDialog messagedialog = new MessageDialog(null, "Please Supply A New Logic Password", sizedpanel1, astring);
      messagedialog.f791[0].setEnabled(false);
      new C_UE(messagedialog, 0, c_q_1, c_q_2, c_q_);
      c_q_.requestFocus();
      messagedialog.m1322(MessageDialog.m1321(messagedialog.getPreferredSize()));
      if (messagedialog.f790 != 0) {
         return null;
      } else {
         c__f.f932 = C__F.m1588(c_q_);
         return c__f.f933 = C__F.m1588(c_q_1);
      }
   }

   static NewUserInfo m1869() {
      return m1870(null);
   }

   static NewUserInfo m1870(String s) {
      NewUserInfo newuserinfo = new NewUserInfo();

      while (!newuserinfo.m683()) {
         if (!newuserinfo.m682(s)) {
            return null;
         }
      }

      return newuserinfo;
   }

   static NewUserInfo m1871(String s, Hashtable hashtable) {
      ServerSession serversession = ServerConnection.openSession(null);
      if (serversession == null) {
         return null;
      } else {
         NewUserInfo newuserinfo = m1870(Message.substitute(Message.getText(s), hashtable));
         C__F c__f = newuserinfo == null ? null : new C__F(m1864(serversession, newuserinfo, null));
         if (c__f != null && c__f.f932 != null) {
            hashtable = Message.params("site", newuserinfo.getInstitution(), "sid", newuserinfo.getStudentId());
            String s1 = Message.substitute(Message.getText("not051"), hashtable);
            Integer integer = ServerConnection.m908(serversession, newuserinfo, c__f, s1, 1);
            if (integer == null) {
               newuserinfo = null;
            }

            ServerConnection.m832(serversession, null);
            return newuserinfo;
         } else {
            ServerConnection.m832(serversession, null);
            return null;
         }
      }
   }

   static Boolean m1872(UserInfo userinfo) {
      if (userinfo.demo) {
         return Boolean.TRUE;
      } else {
         ServerSession serversession = ServerConnection.openSession(null);
         Boolean obool = serversession == null ? Boolean.FALSE : ServerConnection.m911(serversession, userinfo);

         try {
            if (obool == null) {
               if (m1866(serversession, userinfo, null) == null) {
                  return Boolean.FALSE;
               }
            } else if (!obool) {
               Message message = Message.get("not029");
               C_b_E c_b_e = new C_b_E(message.buttons);
               MessageDialog.showMessage(message, null, null, c_b_e);
               if (c_b_e.f1027 != 1 || ServerConnection.m902() != 0) {
                  return Boolean.FALSE;
               }

               return null;
            }

            return Boolean.TRUE;
         } finally {
            if (serversession != null) {
               ServerConnection.m832(serversession, null);
            }
         }
      }
   }

   static void m1873(Submission submission) {
      m1874(submission.f15, submission.f16);
   }

   static void m1874(String[] astring, String[] astring1) {
      Dimension dimension = new Dimension(24 * LogicProgram.fontSize, 30 * LogicProgram.fontSize);
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new BorderLayout());
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new C_m_A());
      Hashtable hashtable = Message.params("user", LogicProgram.user.getFullName());
      if (astring1.length == 0 && astring.length == 0) {
         sizedpanel1.add(new C_ZE("There were no problems to submit."));
         sizedpanel.add(sizedpanel1, "North");
      } else {
         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setLayout(new C_m_A());
         if (astring1.length == 0) {
            String s = Message.substitute("All problems were successfully\nsubmitted for\n<user>:", hashtable);
            sizedpanel1.add(new C_NC(LogicProgram.m1004(s)));
            int k1 = astring.length;

            for (int i1 = 0; i1 < k1; i1++) {
               sizedpanel2.add(new C_ZE(astring[i1]));
            }
         } else if (astring.length == 0) {
            sizedpanel1.add(new C_ZE("No problems could be submitted for"));
            sizedpanel1.add(new C_ZE(LogicProgram.user.getFullName() + ":"));
            int j1 = astring1.length;

            for (int l = 0; l < j1; l++) {
               sizedpanel2.add(new C_ZE(astring1[l]));
            }
         } else {
            sizedpanel1.add(new C_ZE("Submission results for"));
            sizedpanel1.add(new C_ZE(LogicProgram.user.getFullName() + ":"));
            sizedpanel2.add(new C_ZE("Succeeded:"));
            int j = astring.length;

            for (int i = 0; i < j; i++) {
               sizedpanel2.add(new C_ZE("  " + astring[i]));
            }

            sizedpanel2.add(new C_ZE("Failed:"));
            j = astring1.length;

            for (int k = 0; k < j; k++) {
               sizedpanel2.add(new C_ZE("  " + astring1[k]));
            }
         }

         sizedpanel.add(sizedpanel1, "North");
         JScrollPane jscrollpane = new JScrollPane(sizedpanel2);
         sizedpanel.add(jscrollpane, "Center");
      }

      String[] astring2 = new String[]{"OK"};
      MessageDialog messagedialog = new MessageDialog(null, "Submission Results", sizedpanel, astring2);
      messagedialog.setSize(dimension);
      messagedialog.m1323(MessageDialog.m1321(dimension), true);
   }

   static void m1875(C_LD c_ld) {
      m1876(c_ld.f518, c_ld.f519, c_ld.f520);
   }

   static void m1876(String[] astring, String[] astring1, String s) {
      Dimension dimension = new Dimension(16 * LogicProgram.fontSize, 20 * LogicProgram.fontSize);
      SizedPanel sizedpanel = new SizedPanel();
      boolean flag = false;
      sizedpanel.setLayout(new BorderLayout());
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.setLayout(new C_m_A());
      if (astring1.length == 0 && astring.length == 0) {
         sizedpanel1.add(new C_ZE("There were no problems to upload."));
         sizedpanel.add(sizedpanel1, "North");
      } else {
         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setLayout(new C_m_A());
         if (astring1.length == 0) {
            sizedpanel1.add(new C_ZE("All problems were successfully uploaded."));
            int k1 = astring.length;

            for (int i1 = 0; i1 < k1; i1++) {
               sizedpanel2.add(new C_ZE(astring[i1]));
            }

            sizedpanel1.add(sizedpanel2);
         } else if (astring.length == 0) {
            sizedpanel1.add(new C_ZE("No problems could be uploaded."));
            int j1 = astring1.length;

            for (int l = 0; l < j1; l++) {
               sizedpanel2.add(new C_ZE(astring1[l]));
            }

            sizedpanel1.add(sizedpanel2);
            flag = true;
         } else {
            sizedpanel1.add(new C_ZE("Upload results:"));
            sizedpanel2.add(new C_ZE("Succeeded:"));
            int j = astring.length;

            for (int i = 0; i < j; i++) {
               sizedpanel2.add(new C_ZE("  " + astring[i]));
            }

            sizedpanel2.add(new C_ZE("Failed:"));
            j = astring1.length;

            for (int k = 0; k < j; k++) {
               sizedpanel2.add(new C_ZE("  " + astring1[k]));
            }

            sizedpanel1.add(sizedpanel2);
            flag = true;
         }

         if (flag) {
            Message message = Message.get("not092");
            sizedpanel1.add(new C_NC(LogicProgram.m1004(message.text)));
         }

         sizedpanel.add(sizedpanel1, "North");
         JScrollPane jscrollpane = new JScrollPane(sizedpanel2);
         sizedpanel.add(jscrollpane, "Center");
         if (astring1.length != 0 && s != null) {
            EditableTextPane editabletextpane = new EditableTextPane(LogicProgram.m1004(s));
            editabletextpane.m1787(true);
            editabletextpane.m1789(true);
            editabletextpane.setEnabled(false);
            sizedpanel.add(editabletextpane, "South");
         }
      }

      String[] astring2 = new String[]{"OK"};
      MessageDialog messagedialog = new MessageDialog(null, "Upload Results", sizedpanel, astring2);
      messagedialog.setSize(dimension);
      messagedialog.m1323(MessageDialog.m1321(dimension), true);
   }
}
