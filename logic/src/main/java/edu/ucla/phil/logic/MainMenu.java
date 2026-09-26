package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;

class MainMenu extends LogicModule implements LogicConstants, ActionListener {
   int f1473;
   static final String[] f1474 = new String[]{"Using " + LPInfo.programName, "About " + LPInfo.programName, "Menu Help", "Logic Text", "Notices"};
   static final int f1475 = 0;
   static final int f1476 = 1;
   static final int f1477 = 2;
   static final int f1478 = 3;
   static final int f1479 = 4;
   static final String[] f1480 = new String[]{"Assignments", "Course Website"};
   static final int f1481 = 0;
   static final int f1482 = 1;
   static final String[] f1483 = new String[]{"User Information", "Backup", "Copy", "Delete Work/Quit", "Quit"};
   static final int f1484 = 0;
   static final int f1485 = 1;
   static final int f1486 = 2;
   static final int f1487 = 3;
   static final int f1488 = 4;
   static Point f1489 = null;

   MainMenu(boolean flag) {
      super(flag);
      this.titlePanel = null;
      this.f1473 = LogicProgram.fontSize;
      JButton[] ajbutton = new JButton[moduleNames.length + f1474.length + f1480.length + f1483.length];
      this.setLayout(new BorderLayout());
      CellPanel cellpanel = new CellPanel();
      cellpanel.setLayout(new C_m_A(1));
      cellpanel.add(new C_CC(this.f1473 * 3 / 5, 0, false, this.colors[7]));
      C_ZE c_ze = new C_ZE("  " + LPInfo.programName + ": A Workbook  ");
      c_ze.setFont(LogicProgram.getFont(this.f1473 * 2, 1));
      cellpanel.add(c_ze);
      CellPanel cellpanel1 = new CellPanel();
      cellpanel1.setLayout(new FlowLayout(1, 0, 0));
      C_ZE c_ze1 = new C_ZE("code version " + LogicProgram.codeVersion, 0);
      c_ze1.setFont(LogicProgram.getFont(this.f1473 * 6 / 7, 1));
      cellpanel1.add(c_ze1);
      cellpanel.add(cellpanel1);
      CellPanel cellpanel2 = new CellPanel();
      cellpanel2.setLayout(new FlowLayout(1, 0, 0));
      C_ZE c_ze2 = new C_ZE("text version " + ServerConnection.textVersion, 0);
      c_ze2.setFont(LogicProgram.getFont(this.f1473 * 6 / 7, 1));
      cellpanel2.add(c_ze2);
      cellpanel.add(cellpanel2);
      if (LogicProgram.getCredentials("exam") != null) {
         String s = C_GE.m644(".");
         CellPanel cellpanel3 = new CellPanel();
         cellpanel3.setLayout(new FlowLayout(1, 0, 0));
         cellpanel3.add(new C_ZE("IP address: " + s));
         cellpanel3.setFont(LogicProgram.getFont(this.f1473 * 6 / 7, 1));
         cellpanel.add(cellpanel3);
      }

      if (ServerConnection.f480) {
         CellPanel cellpanel4 = new CellPanel();
         cellpanel4.setLayout(new FlowLayout(1, 0, 0));
         C_ZE c_ze3;
         if (ServerConnection.f482) {
            c_ze3 = new C_ZE("projector version " + ServerConnection.f479);
         } else {
            c_ze3 = new C_ZE("instructor version " + ServerConnection.f479);
         }

         c_ze3.setFont(LogicProgram.getFont(this.f1473 * 6 / 7, 1));
         cellpanel4.add(c_ze3);
         cellpanel.add(cellpanel4);
      }

      cellpanel.add(new C_CC(this.f1473 * 2, 0, false, this.colors[7]));
      cellpanel.setFont(LogicProgram.getFont(this.f1473 * 2, 1));
      this.add(cellpanel, "North");
      this.add(new C_CC(this.f1473 * 10 / 7, 0, true, this.colors[7]), "West");
      cellpanel = new CellPanel();
      cellpanel1 = new CellPanel();
      cellpanel1.setLayout(new BoxLayout(cellpanel1, 1));
      c_ze1 = new C_ZE("Please Choose a Module");
      c_ze1.setFont(LogicProgram.getFont(this.f1473, 1));
      cellpanel1.add(c_ze1);
      int i = 0;

      for (int j = 0; j < moduleNames.length; j++) {
         if (LogicProgram.f584) {
            String s1 = LogicProgram.getLink(moduleWorks[j]);
            File file1 = ServerConnection.f467;
            s1 = s1 != null && file1 != null ? new File(file1, new File(s1).getName()).getPath() : null;
            if (s1 == null) {
               continue;
            }

            File file2 = new File(s1);
            if (file2.length() <= 2L) {
               continue;
            }
         }

         C_g_C c_g_c = new C_g_C(moduleNames[j]);
         c_g_c.addActionListener(this);
         cellpanel1.add(ajbutton[i++] = c_g_c);
      }

      cellpanel1.add(new C_CC(this.f1473, 2, false, this.colors[7]));

      for (int k = 0; k < f1474.length; k++) {
         if ((!f1474[k].equals(f1474[4]) || LogicProgram.getLink("headlines") != null) && (!f1474[k].equals(f1474[3]) || LogicProgram.f562 != null)) {
            C_g_C c_g_c1 = new C_g_C(f1474[k]);
            c_g_c1.addActionListener(this);
            cellpanel1.add(ajbutton[i++] = c_g_c1);
         }
      }

      cellpanel1.add(new C_CC(this.f1473, 2, false, this.colors[7]));

      for (int l = 0; l < f1480.length; l++) {
         if ((!f1480[l].equals(f1480[1]) || ServerConnection.websiteUrl != null)
            && (!f1480[l].equals(f1480[0]) || LogicProgram.getCredentials("exam") == null || ServerConnection.f480)) {
            C_g_C c_g_c2 = new C_g_C(f1480[l]);
            c_g_c2.addActionListener(this);
            cellpanel1.add(ajbutton[i++] = c_g_c2);
         }
      }

      for (int i1 = 0; i1 < f1483.length; i1++) {
         if ((LogicProgram.f567 != null && !LogicProgram.noNetwork || !f1483[i1].equals(f1483[1]))
            && (LogicProgram.f551 != null || !f1483[i1].equals(f1483[2]))) {
            C_g_C c_g_c3 = new C_g_C(f1483[i1]);
            c_g_c3.addActionListener(this);
            cellpanel1.add(ajbutton[i++] = c_g_c3);
         }
      }

      cellpanel.setLayout(new BorderLayout());
      cellpanel.add(Box.createRigidArea(new Dimension(20, 10)), "West");
      cellpanel.add(cellpanel1, "Center");
      this.add(cellpanel, "West");
      cellpanel = new CellPanel();
      cellpanel.setLayout(new C_m_A(1));
      cellpanel.add(new C_CC(this.f1473 * 2, 0, false, this.colors[7]));
      c_ze = new C_ZE(LPInfo.programName + " is a product of the UCLA Logic Software Project");
      c_ze.setFont(LogicProgram.getFont(this.f1473 * 6 / 7));
      cellpanel.add(c_ze);
      this.add(cellpanel, "South");
      this.setForeground(this.colors[7]);
      this.setBackground(this.colors[8]);
      this.frame = null;
   }

   @Override
   int getModuleIndex() {
      return -1;
   }

   @Override
   int getProblemState(TaggedRecord taggedrecord) {
      System.out.println("LPChooser.getProblemState(LPTagReader reader) should never be called!");
      return 4;
   }

   static MainMenu m2207(Rectangle rectangle) {
      MainMenu mainmenu = new MainMenu(false);
      mainmenu.m2208(LPInfo.programName + ": Menu");
      mainmenu.frame.pack();
      Dimension dimension = mainmenu.getSize();
      mainmenu.frame.setLocation(ProgressDialog.m1291(dimension));
      mainmenu.frame.setResizable(false);
      mainmenu.frame.setVisible(true);
      mainmenu.frame.invalidate();
      mainmenu.frame.validate();
      return mainmenu;
   }

   void m2208(String s) {
      this.frame = new ModuleFrame(s);
      this.frame.add(this, "Center");
      this.frame.module = this;
   }

   boolean m2209(boolean flag) {
      if (!m2210(LPDerivation.instances, flag)) {
         return false;
      } else if (!m2210(LPInvalidation.instances, flag)) {
         return false;
      } else if (!m2210(LPParsing.instances, flag)) {
         return false;
      } else if (!m2210(LPRecognition.instances, flag)) {
         return false;
      } else if (!m2210(LPSymbolizer.instances, flag)) {
         return false;
      } else if (!m2210(LPTruthAnalysis.instances, flag)) {
         return false;
      } else {
         Hashtable hashtable = new Hashtable();
         Hashtable hashtable1 = new Hashtable();
         if (!LPDerivation.checkQuit(hashtable, hashtable1)) {
            return false;
         } else if (!LPInvalidation.checkQuit(hashtable, hashtable1)) {
            return false;
         } else if (!LPParsing.checkQuit(hashtable, hashtable1)) {
            return false;
         } else if (!LPRecognition.checkQuit(hashtable, hashtable1)) {
            return false;
         } else if (!LPSymbolizer.checkQuit(hashtable, hashtable1)) {
            return false;
         } else if (!LPTruthAnalysis.checkQuit(hashtable, hashtable1)) {
            return false;
         } else {
            return !m2213(hashtable, hashtable1) ? false : UserSetup.m2115(new BusyIndicator(this));
         }
      }
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!this.m2209(flag)) {
         return false;
      } else {
         LogicProgram.exit();
         return true;
      }
   }

   static boolean m2210(Vector vector, boolean flag) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         LogicModule logicmodule = (LogicModule)vector.elementAt(0);
         if (!logicmodule.shutdown(flag)) {
            return false;
         }

         logicmodule.getFrame().dispose();
      }

      return true;
   }

   @Override
   public boolean save() {
      return m2211();
   }

   static boolean m2211() {
      if (!m2212(LPDerivation.instances)) {
         return false;
      } else if (!m2212(LPInvalidation.instances)) {
         return false;
      } else if (!m2212(LPParsing.instances)) {
         return false;
      } else if (!m2212(LPRecognition.instances)) {
         return false;
      } else {
         return !m2212(LPSymbolizer.instances) ? false : m2212(LPTruthAnalysis.instances);
      }
   }

   static boolean m2212(Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         if (!((LogicModule)vector.elementAt(j)).save()) {
            return false;
         }
      }

      return true;
   }

   static boolean m2213(Hashtable hashtable, Hashtable hashtable1) {
      Vector vector = (Vector)hashtable.get("handled");
      Vector vector1 = (Vector)hashtable.get("missing");
      Vector vector2 = (Vector)hashtable.get("changed");
      Vector vector3 = (Vector)hashtable1.get("handled");
      Vector vector4 = (Vector)hashtable1.get("missing");
      Vector vector5 = (Vector)hashtable1.get("changed");
      boolean flag = vector4 == null && vector5 == null;
      if (flag) {
         if (vector3 != null) {
            MessageDialog.showMessage("Submit Summary", "Everything has been properly submitted.", null, null);
         }

         return true;
      } else {
         Dimension dimension = new Dimension(24 * LogicProgram.fontSize, 30 * LogicProgram.fontSize);
         ModuleFrame moduleframe = new ModuleFrame();
         SizedPanel sizedpanel = new SizedPanel();
         sizedpanel.setLayout(new BorderLayout());
         SizedPanel sizedpanel1 = new SizedPanel();
         sizedpanel1.setLayout(new C_m_A());
         if (vector4 != null) {
            sizedpanel1.add(new C_ZE("The following problems were"));
            sizedpanel1.add(new C_ZE("not submitted:"));
            int j = vector4.size();

            for (int i = 0; i < j; i++) {
               sizedpanel1.add(new C_ZE("  " + LogicProgram.m1000(TaggedRecord.m1493((String)vector4.elementAt(i)))));
            }
         }

         if (vector5 != null) {
            sizedpanel1.add(new C_ZE("The following problems were"));
            sizedpanel1.add(new C_ZE("changed since submission:"));
            int i1 = vector5.size();

            for (int k = 0; k < i1; k++) {
               sizedpanel1.add(new C_ZE("  " + LogicProgram.m1000(TaggedRecord.m1493((String)vector5.elementAt(k)))));
            }
         }

         if (vector3 != null) {
            sizedpanel1.add(new C_ZE("The following problems were"));
            sizedpanel1.add(new C_ZE("properly submitted:"));
            int j1 = vector3.size();

            for (int l = 0; l < j1; l++) {
               sizedpanel1.add(new C_ZE("  " + LogicProgram.m1000(TaggedRecord.m1493((String)vector3.elementAt(l)))));
            }
         }

         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setLayout(new C_m_A());
         sizedpanel2.add(new C_ZE("           STOP"));
         sizedpanel2.add(new C_ZE("You should submit everything."));
         sizedpanel2.add(new C_ZE("Do you wish to resume the"));
         sizedpanel2.add(new C_ZE("program or quit?"));
         sizedpanel.add(sizedpanel2, "South");
         JScrollPane jscrollpane = new JScrollPane(sizedpanel1);
         sizedpanel.add(jscrollpane, "Center");
         String[] astring = new String[]{"Resume", "Quit"};
         MessageDialog messagedialog = new MessageDialog(moduleframe, "Submit Summary", sizedpanel, astring);
         messagedialog.setSize(dimension);
         messagedialog.m1323(MessageDialog.m1321(dimension), true);
         moduleframe.dispose();
         return messagedialog.f790 != 0;
      }
   }

   static Vector m2214(Vector vector, Vector vector1) {
      int i = vector == null ? 0 : vector.size();
      if (vector1 == null && i != 0) {
         vector1 = new Vector();
      }

      for (int j = 0; j < i; j++) {
         vector1.addElement(vector.elementAt(j));
      }

      return vector1;
   }

   static void m2215(Hashtable hashtable, String s, String s1, ProblemSelector problemselector, C_a_A c_a_a) {
      if (hashtable != null) {
         Vector vector = (Vector)hashtable.get("handled");
         Vector vector1 = (Vector)hashtable.get("missing");
         Vector vector2 = (Vector)hashtable.get("changed");
         Hashtable hashtable1 = LogicProgram.m1084(s, s1, problemselector, c_a_a);
         if (hashtable1 != null) {
            vector = m2214((Vector)hashtable1.get("handled"), vector);
            vector1 = m2214((Vector)hashtable1.get("missing"), vector1);
            vector2 = m2214((Vector)hashtable1.get("changed"), vector2);
         }

         if (vector != null) {
            hashtable.put("handled", vector);
         }

         if (vector1 != null) {
            hashtable.put("missing", vector1);
         }

         if (vector2 != null) {
            hashtable.put("changed", vector2);
         }
      }
   }

   @Override
   public void resize() {
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   static Rectangle m2216() {
      if (f1489 == null) {
         f1489 = MessageDialog.m1321(moduleSize);
      } else {
         f1489.x -= 24;
         f1489.y += 24;
      }

      if (f1489.x < 5) {
         f1489.x = f1489.x + (LogicProgram.f541.width - moduleSize.width - f1489.x - 5) / 24 * 24;
      }

      if (f1489.y + moduleSize.height >= LogicProgram.f541.height - 30) {
         f1489.y = f1489.y - (f1489.y - 5) / 24 * 24;
      }

      return new Rectangle(f1489.x, f1489.y, moduleSize.width, moduleSize.height);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      BusyIndicator busyindicator = new BusyIndicator(this);
      String s = actionevent.getActionCommand();
      if (s.equals(moduleNames[0])) {
         busyindicator.m2162(true);
         LPDerivation.startup(m2216(), busyindicator, null);
      } else if (s.equals(moduleNames[1])) {
         busyindicator.m2162(true);
         LPInvalidation.startup(m2216(), busyindicator, null);
      } else if (s.equals(moduleNames[2])) {
         busyindicator.m2162(true);
         LPParsing.startup(m2216(), busyindicator, null);
      } else if (s.equals(moduleNames[3])) {
         busyindicator.m2162(true);
         LPRecognition.startup(m2216(), busyindicator, null);
      } else if (s.equals(moduleNames[4])) {
         busyindicator.m2162(true);
         LPSymbolizer.startup(m2216(), busyindicator, null);
      } else if (s.equals(moduleNames[5])) {
         busyindicator.m2162(true);
         LPTruthAnalysis.startup(m2216(), busyindicator, null);
      } else if (s.equals(f1474[2])) {
         String s1 = LogicProgram.getLink("menuHelp");
         C_Q.m1186(LogicProgram.configDir, s1);
      } else if (s.equals(f1474[0])) {
         String s2 = LogicProgram.getLink("using");
         C_Q.m1186(LogicProgram.configDir, s2);
      } else if (s.equals(f1474[1])) {
         String s3 = LogicProgram.getLink("about");
         C_Q.m1186(LogicProgram.configDir, s3);
      } else if (s.equals(f1474[3])) {
         String s4 = LogicProgram.m1031(null, "Logic Text", LogicProgram.f562.getPath());
         if (s4 != null) {
            C_Q.m1185(s4);
         }
      } else if (s.equals(f1474[4])) {
         LogicProgram.m1053(false);
      } else if (s.equals(f1480[1])) {
         String s5 = ServerConnection.websiteUrl + "";
         C_Q.m1184(s5);
      } else if (s.equals(f1480[0])) {
         String s6 = LogicProgram.getLink("assignments");
         int i = LogicProgram.user.f664;
         C_Q.m1184(s6 + "?user=" + i);
      } else if (s.equals(f1483[1])) {
         if (LogicProgram.f567 != null) {
            ServerConnection.m896(LogicProgram.f567, busyindicator);
         }
      } else if (s.equals(f1483[2])) {
         if (LogicProgram.f551 != null) {
            ServerConnection.m900(LogicProgram.workDir, LogicProgram.f551);
         }
      } else if (s.equals(f1483[3])) {
         if (this.m2209(false) && ServerConnection.m902() != 1) {
            LogicProgram.exit();
         }
      } else if (s.equals(f1483[0])) {
         LogicProgram.user.m1170();
         if (LogicProgram.user.dirty) {
            LogicProgram.user.save();
            ServerSession serversession = ServerConnection.openSession(busyindicator);
            if (serversession != null) {
               C_a_F c_a_f = C_a_F.m1642(serversession, LogicProgram.user, busyindicator, 2);
               ServerConnection.m832(serversession, busyindicator);
            }
         }
      } else if (s.equals(f1483[4])) {
         this.frame.m62(false);
      }
   }
}
