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
   int baseFontSize;
   static final String[] HELP_BUTTONS = new String[]{"Using " + LPInfo.programName, "About " + LPInfo.programName, "Menu Help", "Logic Text", "Notices"};
   static final int HELP_USING = 0;
   static final int HELP_ABOUT = 1;
   static final int HELP_MENU = 2;
   static final int HELP_TEXT = 3;
   static final int HELP_NOTICES = 4;
   static final String[] WEB_BUTTONS = new String[]{"Assignments", "Course Website"};
   static final int WEB_ASSIGNMENTS = 0;
   static final int WEB_COURSE_SITE = 1;
   static final String[] USER_BUTTONS = new String[]{"User Information", "Backup", "Copy", "Delete Work/Quit", "Quit"};
   static final int USER_INFO = 0;
   static final int USER_BACKUP = 1;
   static final int USER_COPY = 2;
   static final int USER_DELETE_WORK_QUIT = 3;
   static final int USER_QUIT = 4;
   static Point lastModuleLocation = null;

   MainMenu(boolean flag) {
      super(flag);
      this.titlePanel = null;
      this.baseFontSize = LogicProgram.fontSize;
      JButton[] ajbutton = new JButton[moduleNames.length + HELP_BUTTONS.length + WEB_BUTTONS.length + USER_BUTTONS.length];
      this.setLayout(new BorderLayout());
      CellPanel cellpanel = new CellPanel();
      cellpanel.setLayout(new VerticalStackLayout(1));
      cellpanel.add(new SizedSeparator(this.baseFontSize * 3 / 5, 0, false, this.colors[7]));
      LogicLabel logiclabel = new LogicLabel("  " + LPInfo.programName + ": A Workbook  ");
      logiclabel.setFont(LogicProgram.getFont(this.baseFontSize * 2, 1));
      cellpanel.add(logiclabel);
      CellPanel cellpanel1 = new CellPanel();
      cellpanel1.setLayout(new FlowLayout(1, 0, 0));
      LogicLabel logiclabel1 = new LogicLabel("code version " + LogicProgram.codeVersion, 0);
      logiclabel1.setFont(LogicProgram.getFont(this.baseFontSize * 6 / 7, 1));
      cellpanel1.add(logiclabel1);
      cellpanel.add(cellpanel1);
      CellPanel cellpanel2 = new CellPanel();
      cellpanel2.setLayout(new FlowLayout(1, 0, 0));
      LogicLabel logiclabel2 = new LogicLabel("text version " + ServerConnection.textVersion, 0);
      logiclabel2.setFont(LogicProgram.getFont(this.baseFontSize * 6 / 7, 1));
      cellpanel2.add(logiclabel2);
      cellpanel.add(cellpanel2);
      if (LogicProgram.getCredentials("exam") != null) {
         String s = SocketLineClient.getLocalIpString(".");
         CellPanel cellpanel3 = new CellPanel();
         cellpanel3.setLayout(new FlowLayout(1, 0, 0));
         cellpanel3.add(new LogicLabel("IP address: " + s));
         cellpanel3.setFont(LogicProgram.getFont(this.baseFontSize * 6 / 7, 1));
         cellpanel.add(cellpanel3);
      }

      if (ServerConnection.adminInstall) {
         CellPanel cellpanel4 = new CellPanel();
         cellpanel4.setLayout(new FlowLayout(1, 0, 0));
         LogicLabel logiclabel3;
         if (ServerConnection.linkedFromNonetDir) {
            logiclabel3 = new LogicLabel("projector version " + ServerConnection.adminVersion);
         } else {
            logiclabel3 = new LogicLabel("instructor version " + ServerConnection.adminVersion);
         }

         logiclabel3.setFont(LogicProgram.getFont(this.baseFontSize * 6 / 7, 1));
         cellpanel4.add(logiclabel3);
         cellpanel.add(cellpanel4);
      }

      cellpanel.add(new SizedSeparator(this.baseFontSize * 2, 0, false, this.colors[7]));
      cellpanel.setFont(LogicProgram.getFont(this.baseFontSize * 2, 1));
      this.add(cellpanel, "North");
      this.add(new SizedSeparator(this.baseFontSize * 10 / 7, 0, true, this.colors[7]), "West");
      cellpanel = new CellPanel();
      cellpanel1 = new CellPanel();
      cellpanel1.setLayout(new BoxLayout(cellpanel1, 1));
      logiclabel1 = new LogicLabel("Please Choose a Module");
      logiclabel1.setFont(LogicProgram.getFont(this.baseFontSize, 1));
      cellpanel1.add(logiclabel1);
      int i = 0;

      for (int j = 0; j < moduleNames.length; j++) {
         if (LogicProgram.noCoreProblems) {
            String s1 = LogicProgram.getLink(moduleWorks[j]);
            File file1 = ServerConnection.localDir;
            String s2 = s1 != null && file1 != null ? new File(file1, new File(s1).getName()).getPath() : null;
            if (s2 == null) {
               continue;
            }

            File file2 = new File(s2);
            if (file2.length() <= 2L) {
               continue;
            }
         }

         WideMenuButton widemenubutton = new WideMenuButton(moduleNames[j]);
         widemenubutton.addActionListener(this);
         cellpanel1.add(ajbutton[i++] = widemenubutton);
      }

      cellpanel1.add(new SizedSeparator(this.baseFontSize, 2, false, this.colors[7]));

      for (int k = 0; k < HELP_BUTTONS.length; k++) {
         if ((!HELP_BUTTONS[k].equals(HELP_BUTTONS[4]) || LogicProgram.getLink("headlines") != null)
            && (!HELP_BUTTONS[k].equals(HELP_BUTTONS[3]) || LogicProgram.textDir != null)) {
            WideMenuButton widemenubutton1 = new WideMenuButton(HELP_BUTTONS[k]);
            widemenubutton1.addActionListener(this);
            cellpanel1.add(ajbutton[i++] = widemenubutton1);
         }
      }

      cellpanel1.add(new SizedSeparator(this.baseFontSize, 2, false, this.colors[7]));

      for (int l = 0; l < WEB_BUTTONS.length; l++) {
         if ((!WEB_BUTTONS[l].equals(WEB_BUTTONS[1]) || ServerConnection.websiteUrl != null)
            && (!WEB_BUTTONS[l].equals(WEB_BUTTONS[0]) || LogicProgram.getCredentials("exam") == null || ServerConnection.adminInstall)) {
            WideMenuButton widemenubutton2 = new WideMenuButton(WEB_BUTTONS[l]);
            widemenubutton2.addActionListener(this);
            cellpanel1.add(ajbutton[i++] = widemenubutton2);
         }
      }

      for (int i1 = 0; i1 < USER_BUTTONS.length; i1++) {
         if ((LogicProgram.backupName != null && !LogicProgram.noNetwork || !USER_BUTTONS[i1].equals(USER_BUTTONS[1]))
            && (LogicProgram.copyDir != null || !USER_BUTTONS[i1].equals(USER_BUTTONS[2]))) {
            WideMenuButton widemenubutton3 = new WideMenuButton(USER_BUTTONS[i1]);
            widemenubutton3.addActionListener(this);
            cellpanel1.add(ajbutton[i++] = widemenubutton3);
         }
      }

      cellpanel.setLayout(new BorderLayout());
      cellpanel.add(Box.createRigidArea(new Dimension(20, 10)), "West");
      cellpanel.add(cellpanel1, "Center");
      this.add(cellpanel, "West");
      cellpanel = new CellPanel();
      cellpanel.setLayout(new VerticalStackLayout(1));
      cellpanel.add(new SizedSeparator(this.baseFontSize * 2, 0, false, this.colors[7]));
      logiclabel = new LogicLabel(LPInfo.programName + " is a product of the UCLA Logic Software Project");
      logiclabel.setFont(LogicProgram.getFont(this.baseFontSize * 6 / 7));
      cellpanel.add(logiclabel);
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

   static MainMenu open(Rectangle rectangle) {
      MainMenu mainmenu = new MainMenu(false);
      mainmenu.createMenuFrame(LPInfo.programName + ": Menu");
      mainmenu.frame.pack();
      Dimension dimension = mainmenu.getSize();
      mainmenu.frame.setLocation(ProgressDialog.centeredLocation(dimension));
      mainmenu.frame.setResizable(false);
      mainmenu.frame.setVisible(true);
      mainmenu.frame.invalidate();
      mainmenu.frame.validate();
      return mainmenu;
   }

   void createMenuFrame(String s) {
      this.frame = new ModuleFrame(s);
      this.frame.add(this, "Center");
      this.frame.module = this;
   }

   boolean closeAllModules(boolean flag) {
      if (!shutdownInstances(LPDerivation.instances, flag)) {
         return false;
      } else if (!shutdownInstances(LPInvalidation.instances, flag)) {
         return false;
      } else if (!shutdownInstances(LPParsing.instances, flag)) {
         return false;
      } else if (!shutdownInstances(LPRecognition.instances, flag)) {
         return false;
      } else if (!shutdownInstances(LPSymbolizer.instances, flag)) {
         return false;
      } else if (!shutdownInstances(LPTruthAnalysis.instances, flag)) {
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
            if (!showSubmitSummary(hashtable, hashtable1)) {
               return false;
            } else if (!UserSetup.offerBackupBeforeQuit(new BusyIndicator(this))) {
               return false;
            } else {
               return true;
            }
         }
      }
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!this.closeAllModules(flag)) {
         return false;
      } else {
         LogicProgram.exit();
         return true;
      }
   }

   static boolean shutdownInstances(Vector vector, boolean flag) {
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
      return saveAll();
   }

   static boolean saveAll() {
      if (!saveInstances(LPDerivation.instances)) {
         return false;
      } else if (!saveInstances(LPInvalidation.instances)) {
         return false;
      } else if (!saveInstances(LPParsing.instances)) {
         return false;
      } else if (!saveInstances(LPRecognition.instances)) {
         return false;
      } else {
         if (!saveInstances(LPSymbolizer.instances)) {
            return false;
         } else if (!saveInstances(LPTruthAnalysis.instances)) {
            return false;
         } else {
            return true;
         }
      }
   }

   static boolean saveInstances(Vector vector) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         if (!((LogicModule)vector.elementAt(j)).save()) {
            return false;
         }
      }

      return true;
   }

   static boolean showSubmitSummary(Hashtable hashtable, Hashtable hashtable1) {
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
         sizedpanel1.setLayout(new VerticalStackLayout());
         if (vector4 != null) {
            sizedpanel1.add(new LogicLabel("The following problems were"));
            sizedpanel1.add(new LogicLabel("not submitted:"));
            int j = vector4.size();

            for (int i = 0; i < j; i++) {
               sizedpanel1.add(new LogicLabel("  " + LogicProgram.stripNamePrefix(TaggedRecord.nameOf((String)vector4.elementAt(i)))));
            }
         }

         if (vector5 != null) {
            sizedpanel1.add(new LogicLabel("The following problems were"));
            sizedpanel1.add(new LogicLabel("changed since submission:"));
            int i1 = vector5.size();

            for (int k = 0; k < i1; k++) {
               sizedpanel1.add(new LogicLabel("  " + LogicProgram.stripNamePrefix(TaggedRecord.nameOf((String)vector5.elementAt(k)))));
            }
         }

         if (vector3 != null) {
            sizedpanel1.add(new LogicLabel("The following problems were"));
            sizedpanel1.add(new LogicLabel("properly submitted:"));
            int j1 = vector3.size();

            for (int l = 0; l < j1; l++) {
               sizedpanel1.add(new LogicLabel("  " + LogicProgram.stripNamePrefix(TaggedRecord.nameOf((String)vector3.elementAt(l)))));
            }
         }

         SizedPanel sizedpanel2 = new SizedPanel();
         sizedpanel2.setLayout(new VerticalStackLayout());
         sizedpanel2.add(new LogicLabel("           STOP"));
         sizedpanel2.add(new LogicLabel("You should submit everything."));
         sizedpanel2.add(new LogicLabel("Do you wish to resume the"));
         sizedpanel2.add(new LogicLabel("program or quit?"));
         sizedpanel.add(sizedpanel2, "South");
         JScrollPane jscrollpane = new JScrollPane(sizedpanel1);
         sizedpanel.add(jscrollpane, "Center");
         String[] astring = new String[]{"Resume", "Quit"};
         MessageDialog messagedialog = new MessageDialog(moduleframe, "Submit Summary", sizedpanel, astring);
         messagedialog.setSize(dimension);
         messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
         moduleframe.dispose();
         return messagedialog.selectedButton != 0;
      }
   }

   static Vector appendAll(Vector vector, Vector vector1) {
      int i = vector == null ? 0 : vector.size();
      if (vector1 == null && i != 0) {
         vector1 = new Vector();
      }

      for (int j = 0; j < i; j++) {
         vector1.addElement(vector.elementAt(j));
      }

      return vector1;
   }

   static void mergeSubmitStatus(Hashtable hashtable, String s, String s1, ProblemSelector problemselector, ProblemRecordEnumeration problemrecordenumeration) {
      if (hashtable != null) {
         Vector vector = (Vector)hashtable.get("handled");
         Vector vector1 = (Vector)hashtable.get("missing");
         Vector vector2 = (Vector)hashtable.get("changed");
         Hashtable hashtable1 = LogicProgram.checkSubmitLog(s, s1, problemselector, problemrecordenumeration);
         if (hashtable1 != null) {
            vector = appendAll((Vector)hashtable1.get("handled"), vector);
            vector1 = appendAll((Vector)hashtable1.get("missing"), vector1);
            vector2 = appendAll((Vector)hashtable1.get("changed"), vector2);
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

   static Rectangle nextModuleBounds() {
      if (lastModuleLocation == null) {
         lastModuleLocation = MessageDialog.centeredLocation(moduleSize);
      } else {
         lastModuleLocation.x -= 24;
         lastModuleLocation.y += 24;
      }

      if (lastModuleLocation.x < 5) {
         lastModuleLocation.x += (LogicProgram.screenSize.width - moduleSize.width - lastModuleLocation.x - 5) / 24 * 24;
      }

      if (lastModuleLocation.y + moduleSize.height >= LogicProgram.screenSize.height - 30) {
         lastModuleLocation.y -= (lastModuleLocation.y - 5) / 24 * 24;
      }

      return new Rectangle(lastModuleLocation.x, lastModuleLocation.y, moduleSize.width, moduleSize.height);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      BusyIndicator busyindicator = new BusyIndicator(this);
      String s = actionevent.getActionCommand();
      if (s.equals(moduleNames[0])) {
         busyindicator.setBusy(true);
         LPDerivation.startup(nextModuleBounds(), busyindicator, null);
      } else if (s.equals(moduleNames[1])) {
         busyindicator.setBusy(true);
         LPInvalidation.startup(nextModuleBounds(), busyindicator, null);
      } else if (s.equals(moduleNames[2])) {
         busyindicator.setBusy(true);
         LPParsing.startup(nextModuleBounds(), busyindicator, null);
      } else if (s.equals(moduleNames[3])) {
         busyindicator.setBusy(true);
         LPRecognition.startup(nextModuleBounds(), busyindicator, null);
      } else if (s.equals(moduleNames[4])) {
         busyindicator.setBusy(true);
         LPSymbolizer.startup(nextModuleBounds(), busyindicator, null);
      } else if (s.equals(moduleNames[5])) {
         busyindicator.setBusy(true);
         LPTruthAnalysis.startup(nextModuleBounds(), busyindicator, null);
      } else if (s.equals(HELP_BUTTONS[2])) {
         String s1 = LogicProgram.getLink("menuHelp");
         DesktopLauncher.open(LogicProgram.configDir, s1);
      } else if (s.equals(HELP_BUTTONS[0])) {
         String s2 = LogicProgram.getLink("using");
         DesktopLauncher.open(LogicProgram.configDir, s2);
      } else if (s.equals(HELP_BUTTONS[1])) {
         String s3 = LogicProgram.getLink("about");
         DesktopLauncher.open(LogicProgram.configDir, s3);
      } else if (s.equals(HELP_BUTTONS[3])) {
         String s4 = LogicProgram.chooseOpenFile(null, "Logic Text", LogicProgram.textDir.getPath());
         if (s4 != null) {
            DesktopLauncher.open(s4);
         }
      } else if (s.equals(HELP_BUTTONS[4])) {
         LogicProgram.showHeadlines(false);
      } else if (s.equals(WEB_BUTTONS[1])) {
         String s5 = ServerConnection.websiteUrl + "";
         DesktopLauncher.browse(s5);
      } else if (s.equals(WEB_BUTTONS[0])) {
         String s6 = LogicProgram.getLink("assignments");
         int i = LogicProgram.user.userUid;
         DesktopLauncher.browse(s6 + "?user=" + i);
      } else if (s.equals(USER_BUTTONS[1])) {
         if (LogicProgram.backupName != null) {
            ServerConnection.backupWork(LogicProgram.backupName, busyindicator);
         }
      } else if (s.equals(USER_BUTTONS[2])) {
         if (LogicProgram.copyDir != null) {
            ServerConnection.copyWork(LogicProgram.workDir, LogicProgram.copyDir);
         }
      } else if (s.equals(USER_BUTTONS[3])) {
         if (this.closeAllModules(false) && ServerConnection.deleteWork() != 1) {
            LogicProgram.exit();
         }
      } else if (s.equals(USER_BUTTONS[0])) {
         LogicProgram.user.editInfo();
         if (LogicProgram.user.dirty) {
            LogicProgram.user.save();
            ServerSession serversession = ServerConnection.openSession(busyindicator);
            if (serversession != null) {
               LoginResult loginresult = LoginResult.login(serversession, LogicProgram.user, busyindicator, 2);
               ServerConnection.closeSession(serversession, busyindicator);
            }
         }
      } else if (s.equals(USER_BUTTONS[4])) {
         this.frame.closeModule(false);
      }
   }
}
