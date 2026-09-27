package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.StyledDocument;

public class LogicProgram implements LogicConstants {
   static ErrorLogStream errorLog = null;
   static OverrideSettings overrides = null;
   static PreferencesFile prefs = null;
   static PreferencesFile workPrefs = null;
   static UserInfo user;
   static RuleTable ruleTable = null;
   static Hashtable links = null;
   static Hashtable coreInfo = null;
   static String scrambleKey = null;
   static final int UNUSED_CONST_16 = 16;
   static int fontSize = ProgressDialog.getDefaultFontSize();
   static Font[] bundledFonts;
   static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   static final int DER_MODULE_INDEX = 0;
   static final int INV_MODULE_INDEX = 1;
   static final int PAR_MODULE_INDEX = 2;
   static final int REC_MODULE_INDEX = 3;
   static final int SYM_MODULE_INDEX = 4;
   static final int TRU_MODULE_INDEX = 5;
   static final int UNUSED_CONST_0 = 0;
   static final int UNUSED_CONST_2 = 2;
   static File configDir;
   static File copyDir;
   static File linkDir;
   static File progDir;
   static File rootDir;
   static File workDir;
   static File overrideFile;
   static File prefsFile;
   static File workPrefsFile;
   static File userFile;
   static File loadInfoFile;
   static File ruleDir;
   static File textDir;
   static File trashDir;
   static String windowsDir;
   static String failDir;
   static int maxBackups = 1;
   static String backupName = null;
   static String restoreName = null;
   static String codeVersion = null;
   static String arch = null;
   static boolean deleteLoadInfo = true;
   static boolean debug = false;
   static boolean printingEnabled = true;
   static boolean overheadColors = false;
   static boolean remote = false;
   static boolean noNetwork = false;
   static boolean hiddenMode = false;
   static boolean unusedFlagA = false;
   static boolean unusedFlagB = true;
   static boolean fromIde = false;
   static boolean backupNeeded = false;
   static boolean copyNeeded = false;
   static boolean workPresent = true;
   static boolean noCoreProblems = false;
   static boolean altSymbols = false;
   static MainMenu mainMenu = null;
   static SingleInstanceGuard instanceGuard = null;
   static Integer soloPort = null;
   static boolean repeatAuth = false;
   static boolean hideSensitive = false;
   static boolean reinitializing = false;
   static UserInfo reinitUser;
   static String optionO = null;
   static ArrayList<Image> iconImages = null;
   static Hashtable credentials = null;
   static String[] symbols = kaplan1;
   static String[] encodedSymbols = kaplan5;
   static String[] htmlSymbols = html1;
   static String sentenceLetters = "PQRSTUVWXYZ";
   static String predicateLetters = "FGHIJKLMNO";
   static String operationLetters = "ABCDE";
   static String variableLetters = "abcdefghijklmnopqrstuvwxyz";
   static boolean monochrome = false;
   static Color[] bruinColors = new Color[]{
      bruinGold, bruinBlue, bruinBluf, bruinRed, bruinWhite, bruinNavy, bruinWhite, bruinBlue, bruinGray, bruinBlack, bruinMaize
   };
   static Color[] printColors = new Color[]{
      bruinBlack, bruinWhite, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinWhite
   };
   static Color[] monochromeColors = new Color[]{
      bruinBlack, bruinWhite, bruinAsh, bruinRed, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinGray, bruinBlack, bruinWhite
   };
   static Color[] moduleColors = monochrome ? monochromeColors : bruinColors;

   public static void main(String[] astring) {
      SwingUtilities.invokeLater(new LogicProgram.ProgramStartup());
   }

   static void loadIconsAndFonts() {
      int[] aint = new int[]{256, 128, 48, 32, 24, 16};
      iconImages = new ArrayList<>(aint.length);
      Toolkit toolkit = Toolkit.getDefaultToolkit();

      for (int i = 0; i < aint.length; i++) {
         URL url = ClassLoader.getSystemResource("images/Logic2010_" + aint[i] + ".png");
         ImageIcon imageicon = new ImageIcon(url);
         iconImages.add(imageicon.getImage());
      }

      bundledFonts = new Font[2];
      GraphicsEnvironment graphicsenvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

      try {
         InputStream inputstream;
         if ((inputstream = ClassLoader.getSystemResourceAsStream("fonts/mplus-1p-lsp-medium.ttf")) != null) {
            graphicsenvironment.registerFont(bundledFonts[0] = Font.createFont(0, inputstream));
            inputstream.close();
         }

         if ((inputstream = ClassLoader.getSystemResourceAsStream("fonts/mplus-1p-lsp-bold.ttf")) != null) {
            graphicsenvironment.registerFont(bundledFonts[1] = Font.createFont(0, inputstream));
            inputstream.close();
         }
      } catch (Exception exception) {
         System.out.println("Font load failure");
      }
   }

   static int initialize() {
      if (!reinitializing) {
         String s = System.getProperty("config.dir");
         if (s == null) {
            s = System.getProperty("user.dir");
         }

         configDir = canonicalFile(s);
         overrideFile = new File(configDir, "override.txt");
         prefsFile = new File(configDir, "prefs.txt");
         workDir = new File(configDir, "work");
         workPrefsFile = new File(workDir, "prefs.txt");
         if (!isWritableDirectory(configDir)) {
            MessageDialog.showMessage(
               "Permission Error",
               "Cannot write to the installation location.\nThe program must be installed in a folder\nto which you can write.\n\nTry installing again either in the default\ndirectory or on the Desktop or in your\nDownloads directory.",
               null,
               null
            );
            return 1;
         }

         userFile = new File(workDir, "user.txt");
         linkDir = canonicalFile(System.getProperty("link.dir"));
         progDir = canonicalFile(System.getProperty("prog.dir"));
         rootDir = canonicalFile(System.getProperty("root.dir"));
         String s1 = System.getProperty("from.ide");
         if ("1".equals(s1)) {
            fromIde = true;
         }

         trashDir = new File(progDir, "trash");
         loadInfoFile = progDir == null ? null : new File(progDir, "loadinfo.txt");
         copyDir = canonicalFile(System.getProperty("copy.dir"));
         windowsDir = System.getProperty("com.ms.windir");
         failDir = System.getProperty("fail.dir");
         if (loadInfoFile.exists()) {
            Hashtable hashtable = readLoadInfo();
            String s2 = (String)hashtable.get("linkDir");
            if (s2 != null) {
               linkDir = canonicalFile((String)hashtable.get("linkDir"));
            }
         }

         overrides = new OverrideSettings();
         if (overrideFile.exists()) {
            try {
               overrides.load(new FileReader(overrideFile));
            } catch (Exception exception) {
               System.out.println(exception.getMessage());
            }
         }

         prefs = new PreferencesFile();
         prefs.load(prefsFile);
         workPrefs = new PreferencesFile();
         workPrefs.load(workPrefsFile);
      }

      ProgressDialog progressdialog = new ProgressDialog(LPInfo.programName, "Starting the program...", false);
      progressdialog.showWithMargins(30, 20);
      FormulaParser.disableTracing();
      if ((coreInfo = readCoreInfo()) == null) {
         progressdialog.dispose();
         MessageDialog.showMessage("No Core Information", "Could not read core information file.", null, null);
         return 1;
      } else {
         codeVersion = getValue(coreInfo, "version", "").trim();
         arch = getValue(coreInfo, "arch", "noarch").trim();
         if (arch.equalsIgnoreCase("noarch")) {
            progressdialog.dispose();
            MessageDialog.showMessage("No Core Architecture", "Could not determine core architecture.", null, null);
            return 1;
         } else {
            File file3 = null;

            while ((links = readLinks(linkDir, true)) != null) {
               textDir = new File(ruleDir, "text");
               if (!textDir.exists()) {
                  textDir = null;
               } else {
                  File[] afile = textDir.listFiles();
                  if (afile == null || afile.length == 0) {
                     textDir = null;
                  }
               }

               if (!Message.loadMessages()) {
                  progressdialog.dispose();
                  JOptionPane.showMessageDialog(null, "Could not open the program messages file.", "No Messages", 0);
                  return 1;
               }

               if (file3 == null && (!reinitializing || ServerConnection.installAdminNow) && !loadInfoFile.exists()) {
                  String s3 = getLink("adminDir");
                  File file1 = s3 == null ? null : ServerConnection.resolvePath(configDir, s3);
                  s3 = getLink("nonetDir");
                  File file2 = s3 == null ? null : ServerConnection.resolvePath(configDir, s3);
                  if ((file3 = UserSetup.chooseInstalledVersion(file1, file2)) != null) {
                     linkDir = file3;
                  }

                  ServerConnection.installAdminNow = false;
               } else {
                  file3 = null;
               }

               if (file3 == null) {
                  if (failDir != null) {
                     progressdialog.dispose();
                     JOptionPane.showMessageDialog(null, "Could not set directory to " + DelimitedTokenizer.escape(failDir, "\\") + ".", "No Work Directory", 0);
                     return 1;
                  }

                  migrateOldWorkDir();
                  if (!workDir.exists()) {
                     workDir.mkdir();
                  } else if (workDir.isFile()) {
                     progressdialog.dispose();
                     JOptionPane.showMessageDialog(null, DelimitedTokenizer.escape(workDir + "", "\\") + " is not a directory.", "No User Directory", 0);
                     return 1;
                  }

                  resetOptions();
                  ScrambledReader scrambledreader = openDataFile("options", false);
                  if (scrambledreader == null) {
                     progressdialog.dispose();
                     MessageDialog.showMessage("No Options", "Could not read option file.", null, null);
                     return 1;
                  }

                  readOptions(scrambledreader);
                  readOptions(openLocalFile("options", false));
                  if (overheadColors) {
                     moduleColors = monochromeColors;
                  }

                  if (debug) {
                     openDebugLogs();
                  }

                  applyPrefsFontSize();
                  applyColorPrefs();
                  applyOverrideFontSize();
                  if (!arch.equalsIgnoreCase("macos") && !overrides.lookup("SoloCheck", "yes").equalsIgnoreCase("no")) {
                     if (instanceGuard != null && instanceGuard.port != soloPort) {
                        instanceGuard.stopServer();
                        instanceGuard = null;
                     }

                     if (instanceGuard == null && soloPort != null) {
                        instanceGuard = new SingleInstanceGuard(soloPort, "cogito");
                     }

                     if (instanceGuard != null && !instanceGuard.isAlive() && !instanceGuard.startServer()) {
                        progressdialog.dispose();
                        JOptionPane.showMessageDialog(null, "There is already a copy of Logic 2010\nrunning on this computer.", "Logic 2010 Running", 1);
                        return 1;
                     }
                  }

                  if (!ServerConnection.readDatabaseLinks()) {
                     progressdialog.dispose();
                     JOptionPane.showMessageDialog(null, "Could not read database link file.", "No Database Links", 0);
                     return 1;
                  }

                  if (ServerConnection.demoMode) {
                     backupName = null;
                     restoreName = null;
                  }

                  progressdialog.dispose();
                  ServerConnection.emptyTrash();
                  return 0;
               }
            }

            progressdialog.dispose();
            JOptionPane.showMessageDialog(null, "Could not read link file.", "No Links", 0);
            return 1;
         }
      }
   }

   static int initializeUser() {
      try {
         while (true) {
            user = null;
            if (userFile.exists()) {
               if ((user = UserInfo.load(false)) == null) {
                  if (UserSetup.offerDeleteWork()) {
                     continue;
                  }

                  return 1;
               }

               if (remote) {
                  if (!reinitializing) {
                     if (!loadInfoFile.exists()) {
                        Boolean obool = UserSetup.askContinueOrDelete(user);
                        if (obool == null) {
                           continue;
                        }

                        if (!obool) {
                           return 1;
                        }

                        obool = UserSetup.confirmIdentityAndPreferences(user, false, false);
                        if (obool == null) {
                           continue;
                        }

                        if (!obool) {
                           return 1;
                        }

                        if (user.dirty) {
                           user.save();
                        }

                        obool = AccountManager.verifyAccount(user);
                        if (obool == null) {
                           continue;
                        }

                        if (!obool) {
                           return 1;
                        }
                     } else if (readLoadInfo() == null) {
                        return 1;
                     }
                  }

                  int i;
                  if ((i = ServerConnection.checkForUpdates(user)) != 0) {
                     return i;
                  }
               } else if (getCredentials("exam") != null) {
                  Boolean obool2 = UserSetup.confirmIdentityAndPreferences(user, true, false);
                  if (obool2 == null) {
                     continue;
                  }

                  if (!obool2) {
                     return 1;
                  }

                  if (user.dirty) {
                     user.save();
                  }
               } else if (ServerConnection.adminInstall) {
                  Boolean obool3 = UserSetup.confirmIdentityAndPreferences(user, false, true);
                  if (obool3 == null) {
                     continue;
                  }

                  if (!obool3) {
                     return 1;
                  }
               }
            } else {
               workPresent = false;
               if (remote) {
                  Object object;
                  if (reinitializing) {
                     object = reinitUser;
                  } else {
                     object = new NewUserInfo();
                     if ((!loadInfoFile.exists() || !readCourseFromLoadInfo((UserInfo)object))
                        && AccountManager.chooseCourse((UserInfo)object, "not027", true, false) == null) {
                        return 1;
                     }
                  }

                  int j;
                  if ((j = ServerConnection.checkForUpdates((UserInfo)object)) != 0) {
                     return j;
                  }
               }

               if (ServerConnection.demoMode) {
                  user = UserInfo.createDemoUser();
                  if (user.dirty) {
                     user.save();
                  }

                  Boolean obool4 = UserSetup.confirmIdentityAndPreferences(user, false, true);
                  if (obool4 == null) {
                     continue;
                  }

                  if (!obool4) {
                     return 1;
                  }
               } else {
                  if (!UserSetup.offerRegisterOrRestore(restoreName, backupName, getCredentials("exam") == null)) {
                     return 1;
                  }

                  if (userFile.exists()) {
                     if ((user = UserInfo.load(false)) == null) {
                        if (UserSetup.offerDeleteWork()) {
                           continue;
                        }

                        return 1;
                     }

                     if (remote) {
                        if (!checkUserCourse(user)) {
                           return 1;
                        }

                        int k;
                        if ((k = ServerConnection.checkForUpdates(user)) != 0) {
                           return k;
                        }
                     }
                  } else {
                     boolean flag = false;
                     if (remote) {
                        NewUserInfo newuserinfo = AccountManager.createNewUser();
                        if (newuserinfo == null) {
                           return 1;
                        }

                        Boolean obool1 = ServerConnection.hasBackup(restoreName, backupName, newuserinfo);
                        if (obool1 == null) {
                           return 1;
                        }

                        if (obool1) {
                           if (UserSetup.askRestoreOrQuit(newuserinfo)) {
                              return 1;
                           }

                           ServerConnection.deleteWork(false);
                           if (!ServerConnection.restoreWork(restoreName, backupName, newuserinfo)) {
                              return 1;
                           }

                           if ((user = UserInfo.load(false)) == null) {
                              if (UserSetup.offerDeleteWork()) {
                                 continue;
                              }

                              return 1;
                           }

                           flag = true;
                        } else {
                           Boolean obool8 = UserSetup.askRegister(newuserinfo);
                           if (obool8 == null) {
                              return 1;
                           }

                           if (!obool8) {
                              continue;
                           }

                           user = new UserInfo(newuserinfo.getStudentId());
                        }
                     } else {
                        user = new UserInfo();
                     }

                     while (!user.isComplete()) {
                        if (!user.editInfo()) {
                           return 1;
                        }
                     }

                     if (user.dirty) {
                        user.save();
                     }

                     if (remote) {
                        if (flag) {
                           if (!checkUserCourse(user)) {
                              return 1;
                           }

                           int l;
                           if ((l = ServerConnection.checkForUpdates(user)) != 0) {
                              return l;
                           }
                        } else {
                           Boolean obool6 = AccountManager.verifyAccount(user);
                           if (obool6 == null) {
                              continue;
                           }

                           if (!obool6) {
                              return 1;
                           }

                           int i1;
                           if ((i1 = ServerConnection.checkForUpdates(user)) != 0) {
                              return i1;
                           }
                        }
                     }
                  }

                  Boolean obool5 = UserSetup.confirmIdentityAndPreferences(user, false, true);
                  if (obool5 == null) {
                     continue;
                  }

                  if (!obool5) {
                     return 1;
                  }
               }
            }

            if (remote) {
               if (getCredentials("exam") != null) {
                  if (user.usesWorkBackupKey()) {
                     if (ServerConnection.hasModuleWorkFiles()) {
                        if (UserSetup.confirmDeleteWork("not097")) {
                           continue;
                        }

                        return 1;
                     }

                     user.setBackupKey(backupName);
                  } else if (!user.getBackupKey().equalsIgnoreCase(backupName)) {
                     if (UserSetup.confirmDeleteWork("not098")) {
                        continue;
                     }

                     return 1;
                  }
               } else if (!user.usesWorkBackupKey()) {
                  if (UserSetup.confirmDeleteWork("not096")) {
                     continue;
                  }

                  return 1;
               }

               if ("work".equalsIgnoreCase(backupName)) {
                  NetworkTask networktask = new NetworkTask(LPInfo.programName, "Checking database for user...");
                  Boolean obool7 = ServerConnection.ensureInitialBackup(user, backupName, networktask);
                  if (obool7 != null) {
                     if (!obool7) {
                        MessageDialog.showMessage(Message.get("not059"), null, null, null);
                        return 1;
                     }

                     MessageDialog.showMessage(Message.get("not058"), null, null, null);
                  }
               } else if (!ServerConnection.demoMode) {
                  NetworkTask networktask1 = new NetworkTask(LPInfo.programName, "Checking database for user...");
                  if (!ServerConnection.addUserRelation(user, "student", networktask1)) {
                     MessageDialog.showMessage(Message.get("not059"), null, null, null);
                     return 1;
                  }
               }
            }

            return 0;
         }
      } finally {
         if (deleteLoadInfo && loadInfoFile.exists()) {
            loadInfoFile.delete();
         }
      }
   }

   static boolean loadRulesAndTheorems() {
      ProgressDialog progressdialog = new ProgressDialog(LPInfo.programName, "Reading rules and theorems...", false);
      progressdialog.showWithMargins(20, 10);
      ScrambledReader scrambledreader = openDataFile("theorems", false);
      if (scrambledreader == null) {
         progressdialog.dispose();
         showFileError("not001", "the theorems file");
         return false;
      } else {
         TheoremTable theoremtable = TheoremTable.read(scrambledreader);
         scrambledreader = openDataFile("rules", false);
         if (scrambledreader == null) {
            progressdialog.dispose();
            showFileError("not001", "the rules file");
            return false;
         } else {
            ruleTable = RuleTable.read(scrambledreader, theoremtable);
            if (theoremtable != null && ruleTable != null) {
               progressdialog.dispose();
               if (getCredentials("instructor") != null) {
                  ErrorRef errorref = getCredentials("exam") == null ? UserSetup.checkAccess("instructor", null) : UserSetup.checkAccess(null, "Instructor");
                  if (errorref != null) {
                     String s = errorref.getId();
                     if (s != null) {
                        MessageDialog.showMessage(Message.get(s), errorref.params, null, null);
                     }

                     return false;
                  }
               } else if (getCredentials("exam") != null) {
                  ErrorRef errorref1 = UserSetup.checkAccess("exam", null);
                  if (errorref1 != null) {
                     String s1 = errorref1.getId();
                     if (s1 != null) {
                        MessageDialog.showMessage(Message.get(s1), errorref1.params, null, null);
                     }

                     return false;
                  }

                  if (!noNetwork && !submitExamStart()) {
                     return false;
                  }
               }

               return true;
            } else {
               progressdialog.dispose();
               showFileError("not002", "theorems and/or rules file");
               return false;
            }
         }
      }
   }

   static boolean submitExamStart() {
      ServerSession serversession = ServerConnection.openSession(null);
      if (user.userUid == null) {
         Boolean obool = ServerConnection.verifyUser(serversession, user, null, null);
         if (obool == null || !obool) {
            ServerConnection.closeSession(serversession, null);
            return false;
         }
      }

      int i = user.courseUid == null ? 40 : user.courseUid;
      Submission submission = new Submission(i, user.userUid, serversession, "password");
      submission.problemMd5 = Scrambler.md5Base64("");
      submission.evaluation = "X";
      submission.work = "none";
      submission.problemName = "exam start";
      submission.module = "nul";
      submission.helpCount = 0;
      submission.duration = 0L;
      boolean flag = ServerConnection.submit(submission, null);
      ServerConnection.finishSubmission(submission, null);
      return flag;
   }

   static boolean isWritableDirectory(File file1) {
      char[] achar = new char[256];
      int i = 0;
      String s = "This file can be written.";
      if (!file1.isDirectory()) {
         return false;
      } else {
         File file2 = new File(file1, "tempest.txt");
         if (file2.exists()) {
            file2.delete();
         }

         if (file2.exists()) {
            return false;
         } else {
            FileWriter filewriter;
            try {
               filewriter = new FileWriter(file2);
            } catch (IOException ioexception6) {
               file2.delete();
               return false;
            }

            try {
               filewriter.write(s);
               filewriter.flush();
            } catch (IOException ioexception5) {
               try {
                  filewriter.close();
               } catch (IOException ioexception1) {
               }

               file2.delete();
               return false;
            }

            try {
               filewriter.close();
            } catch (IOException ioexception4) {
               file2.delete();
               return false;
            }

            if (!file2.exists()) {
               return false;
            } else {
               FileReader filereader;
               try {
                  filereader = new FileReader(file2);
               } catch (IOException ioexception3) {
                  file2.delete();
                  return false;
               }

               try {
                  if (filereader.ready()) {
                     i = filereader.read(achar);
                  }
               } catch (IOException ioexception7) {
                  try {
                     filereader.close();
                  } catch (IOException ioexception) {
                  }

                  file2.delete();
                  return false;
               }

               try {
                  filereader.close();
               } catch (IOException ioexception2) {
                  file2.delete();
                  return false;
               }

               file2.delete();
               if (file2.exists()) {
                  return false;
               } else {
                  String s1 = new String(achar, 0, i);
                  return s1.equals(s);
               }
            }
         }
      }
   }

   static Hashtable readLoadInfo() {
      Hashtable hashtable = new Hashtable();
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = new BufferedReader(new FileReader(loadInfoFile));

         String s;
         while ((s = bufferedreader.readLine()) != null) {
            int i = s.indexOf(58);
            if (i != -1) {
               hashtable.put(s.substring(0, i), s.substring(i + 1));
            }
         }

         if (hashtable.get("workDeleted") != null) {
            workPresent = false;
         }
      } catch (IOException ioexception1) {
         hashtable = null;
      }

      if (bufferedreader != null) {
         try {
            bufferedreader.close();
         } catch (IOException ioexception) {
         }
      }

      return hashtable;
   }

   static boolean readCourseFromLoadInfo(UserInfo userinfo) {
      Hashtable hashtable = readLoadInfo();
      if (hashtable == null) {
         return false;
      } else {
         String s;
         if ((s = (String)hashtable.get("institution")) == null) {
            return false;
         } else {
            userinfo.put("institution", s);
            if ((s = (String)hashtable.get("term")) == null) {
               return false;
            } else {
               userinfo.put("term", s);
               if ((s = (String)hashtable.get("course")) == null) {
                  return false;
               } else {
                  userinfo.put("className", s);
                  return true;
               }
            }
         }
      }
   }

   static boolean checkUserCourse(UserInfo userinfo) {
      CourseInfo courseinfo = userinfo.getCourse();
      CourseInfo courseinfo1 = ServerConnection.getConfiguredCourse();
      if (courseinfo == null) {
         if (AccountManager.chooseCourse(userinfo, "not045", true, false) == null) {
            return false;
         }

         if (userinfo.dirty) {
            userinfo.save();
         }
      } else if (courseinfo1 != null && !courseinfo.courseUid.equals(courseinfo1.courseUid)) {
         if (AccountManager.chooseCourse(userinfo, "not080", true, true) == null) {
            return false;
         }

         if (userinfo.dirty) {
            userinfo.save();
         }
      }

      return true;
   }

   static void migrateOldWorkDir() {
      File file1 = new File(configDir, "logic");
      if (file1.isDirectory()) {
         file1.renameTo(workDir);
      }
   }

   static boolean isDemoName(String s) {
      return s.equalsIgnoreCase("Demo") || s.equalsIgnoreCase("Test");
   }

   static void showFileError(String s, String s1) {
      Hashtable hashtable = Message.params("file name", s1, "user name", user.getFullName());
      MessageDialog.showMessage(Message.get(s), hashtable, null, null);
   }

   static void showProblemError(String s, String s1) {
      Hashtable hashtable = Message.params("problem name", s1);
      MessageDialog.showMessage(Message.get(s), hashtable, null, null);
   }

   static String currentStackTrace() {
      StringWriter stringwriter = new StringWriter();
      new Throwable().fillInStackTrace().printStackTrace(new PrintWriter(stringwriter));
      return stringwriter.toString();
   }

   static void logStackTrace(String s) {
      try {
         throw new Throwable(s);
      } catch (Throwable throwable) {
         if (DiagnosticsLog.out == null) {
            throwable.printStackTrace();
         } else {
            throwable.printStackTrace(DiagnosticsLog.out);
            System.out.println("see diagnostics: " + s);
         }
      }
   }

   static boolean checkWorkDir() {
      if (workDir.exists() && !workDir.isFile()) {
         return true;
      } else {
         Hashtable hashtable = Message.params("user dir", DelimitedTokenizer.escape(workDir + "", "\\"));
         MessageDialog.showMessage(Message.get("not009"), hashtable, null, null);
         return false;
      }
   }

   static boolean checkSameUser() {
      if (!checkWorkDir()) {
         return false;
      } else {
         UserInfo userinfo = UserInfo.load(false);
         String s = userinfo == null ? null : userinfo.computeDigest();
         String s1 = user == null ? null : user.computeDigest();
         if (s != null && s1 != null && s.equals(s1)) {
            return true;
         } else {
            Hashtable hashtable = Message.params("user name", user.getFullName());
            MessageDialog.showMessage(Message.get("not010"), hashtable, null, null);
            return false;
         }
      }
   }

   static void exit() {
      if (instanceGuard != null) {
         instanceGuard.stopServer();
      }

      if (deleteLoadInfo && loadInfoFile != null && loadInfoFile.exists()) {
         loadInfoFile.delete();
      }

      closeDebugLogs();
      System.exit(0);
   }

   static void openDebugLogs() {
      if (debug) {
         try {
            errorLog = new ErrorLogStream(new FileOutputStream(new File(workDir, "errors.txt")), true);
         } catch (IOException ioexception1) {
            errorLog = null;
            ioexception1.printStackTrace(System.out);
         }

         try {
            DiagnosticsLog.out = new PrintWriter(new FileWriter(new File(workDir, "diagnostics.txt")), true);
         } catch (IOException ioexception) {
            DiagnosticsLog.out = null;
            ioexception.printStackTrace(System.out);
         }
      }
   }

   static void closeDebugLogs() {
      if (debug) {
         if (errorLog != null) {
            try {
               errorLog.close();
               errorLog = null;
            } catch (Exception exception1) {
            }
         }

         if (DiagnosticsLog.out != null) {
            try {
               DiagnosticsLog.out.close();
               DiagnosticsLog.out = null;
            } catch (Exception exception) {
            }
         }
      }
   }

   static String[] excludeLogFiles(String[] astring) {
      if (astring == null) {
         return null;
      } else {
         int i = astring.length;
         Vector vector = new Vector();
         boolean flag = false;

         for (int j = 0; j < i; j++) {
            String s = astring[j];
            if (!"errors.txt".equalsIgnoreCase(s) && !"diagnostics.txt".equalsIgnoreCase(s)) {
               vector.addElement(s);
            } else {
               flag = true;
            }
         }

         if (!flag) {
            return astring;
         } else {
            i = vector.size();
            return i == 0 ? null : (String[])vector.toArray(new String[i]);
         }
      }
   }

   static EditableTextPane createFormulaPane(String s) {
      return createFormulaPane(s, 0, 2147483647, false);
   }

   static EditableTextPane createFormulaPane(String s, boolean flag) {
      return createFormulaPane(s, 0, 2147483647, flag);
   }

   static EditableTextPane createFormulaPane(String s, int i, boolean flag) {
      return createFormulaPane(s, i, i, flag);
   }

   static EditableTextPane createFormulaPane(String s, int i, int j, boolean flag) {
      EditableTextPane editabletextpane = new EditableTextPane(translateSymbols(s, maggie, symbols), i, j, flag);
      editabletextpane.setEditable(false);
      return editabletextpane;
   }

   static LinePanel createFormulaRow(String s, int i) {
      return createFormulaRow(s, i, 0, 2147483647, null);
   }

   static LinePanel createFormulaRow(HighlightedText highlightedtext, int i) {
      return createFormulaRow(highlightedtext, i, 0, 2147483647);
   }

   static LinePanel createFormulaRow(String s, int i, int j) {
      return createFormulaRow(s, i, 0, j, null);
   }

   static LinePanel createFormulaRow(HighlightedText highlightedtext, int i, int j) {
      return createFormulaRow(highlightedtext, i, 0, j);
   }

   static LinePanel createFormulaRow(String s, int i, int j, int k) {
      return createFormulaRow(s, i, j, k, null);
   }

   static LinePanel createFormulaRow(HighlightedText highlightedtext, int i, int j, int k) {
      return createFormulaRow(highlightedtext.text, i, j, k, TextHighlighter.fromRanges(highlightedtext.layers));
   }

   static LinePanel createFormulaRow(String s, int i, int j, int k, TextHighlighter texthighlighter) {
      LinePanel linepanel = new LinePanel();
      linepanel.setLayout(new FlowLayout(0, 0, 0));
      StyledDocument styleddocument = translateToDocument(s, maggie, symbols, texthighlighter == null ? null : texthighlighter.copy());
      EditableTextPane editabletextpane = new EditableTextPane(styleddocument, j, k, false);
      editabletextpane.setEditable(false);
      linepanel.add(new LinePanel(i));
      linepanel.add(editabletextpane);
      linepanel.add(new LinePanel(i));
      return linepanel;
   }

   static LogicLabel createFormulaLabel(String s) {
      s = translateSymbols(s, maggie, symbols);
      return new LogicLabel(s);
   }

   static String translateSymbols(String s) {
      return translateSymbols(s, maggie, symbols);
   }

   static StyledDocument translateToDocument(String s, TextHighlighter texthighlighter) {
      return translateToDocument(s, maggie, symbols, texthighlighter);
   }

   static String translateSymbols(String s, String[] astring, String[] astring1) {
      return translateSymbols(s, astring, astring1, (int[])null);
   }

   static String translateSymbols(String s, String[] astring, String[] astring1, int[] aint) {
      if (s == null) {
         return null;
      } else {
         String s1 = "";

         while (true) {
            int i = -1;
            int j = -1;

            for (int k = 0; k < astring.length; k++) {
               int l = s.indexOf(astring[k]);
               if (l != -1 && (i == -1 || l < i)) {
                  i = l;
                  j = k;
               }
            }

            String s2 = findNumberedPlaceholder(s);
            int i1 = s2 == null ? -1 : s.indexOf(s2);
            if (i1 != -1 && (i == -1 || i1 < i)) {
               i = i1 + s2.length();
               j = -1;
            }

            if (i == -1) {
               return s1 + s;
            }

            if (j == -1) {
               s1 = s1 + s.substring(0, i);
               s = s.substring(i);
            } else {
               shiftPositions(aint, s1.length() + i, astring1[j].length() - astring[j].length());
               s1 = s1 + s.substring(0, i) + astring1[j];
               s = s.substring(i + astring[j].length());
            }
         }
      }
   }

   static String translateSymbols(String s, String[] astring, String[] astring1, IntervalSet intervalset) {
      return translateSymbols(s, astring, astring1, intervalset == null ? null : intervalset.boundaries);
   }

   static StyledDocument translateToDocument(String s, String[] astring, String[] astring1, TextHighlighter texthighlighter) {
      if (texthighlighter != null) {
         int i = texthighlighter.size();

         for (int j = 0; j < i; j++) {
            translateSymbols(s, astring, astring1, (IntervalSet)texthighlighter.ranges.elementAt(j));
         }
      }

      s = translateSymbols(s, astring, astring1);
      return TextHighlighter.createDocument(new HighlightedText(s, texthighlighter != null ? texthighlighter.ranges : null));
   }

   static HighlightedText translateStyledText(HighlightedText highlightedtext, String[] astring, String[] astring1) {
      if (highlightedtext.layers != null) {
         int i = highlightedtext.layers.size();

         for (int j = 0; j < i; j++) {
            translateSymbols(highlightedtext.text, astring, astring1, (IntervalSet)highlightedtext.layers.elementAt(j));
         }
      }

      highlightedtext.text = translateSymbols(highlightedtext.text, astring, astring1);
      return highlightedtext;
   }

   static String stripNamePrefix(String s) {
      if (s == null) {
         return null;
      } else {
         String s1 = s.trim();
         int i = s1.indexOf(32);
         if (i == -1) {
            return s1;
         } else {
            int j = s1.substring(0, i).lastIndexOf(46);
            return j == -1 ? s1 : s1.substring(j + 1).trim();
         }
      }
   }

   static int[] symbolBoundsAt(String s, int i, String[] astring) {
      int j = astring.length;
      int k = s.length();
      if (k == 0) {
         return new int[]{0, 0};
      } else {
         if (i < 0) {
            i = 0;
         }

         if (i >= k) {
            i = k - 1;
         }

         for (int l = 0; l < j; l++) {
            String s1 = astring[l];
            int i1 = s1.length();
            int j1 = i - i1 + 1;
            if (j1 < 0) {
               j1 = 0;
            }

            int k1 = k - i1;
            if (k1 > i) {
               k1 = i;
            }

            for (int l1 = j1; l1 <= k1; l1++) {
               if (s.substring(l1, l1 + i1).equals(s1)) {
                  return new int[]{l1, l1 + i1};
               }
            }
         }

         return new int[]{i, i + 1};
      }
   }

   static void shiftPositions(int[] aint, int i, int j) {
      if (aint != null && j != 0) {
         int k = aint.length;

         for (int l = 0; l < k; l++) {
            if (aint[l] > i && (aint[l] += j) < i) {
               aint[l] = i;
            }
         }
      }
   }

   static String[] splitLines(String s) {
      Vector vector = new Vector();

      int i;
      while ((i = s.indexOf(10)) != -1) {
         vector.addElement(s.substring(0, i));
         s = s.substring(i + 1);
      }

      vector.addElement(s);
      String[] astring = new String[vector.size()];
      vector.copyInto(astring);
      return astring;
   }

   static String expandEscapes(String s) {
      boolean flag = false;
      String s1 = "";

      int i;
      while ((i = s.indexOf("\\")) != -1) {
         if (flag) {
            s1 = s1 + translateSymbols(s.substring(0, i), maggie, symbols);
         } else {
            s1 = s1 + s.substring(0, i);
         }

         if (s.length() < i + 2) {
            break;
         }

         char c0 = s.charAt(i + 1);
         s = s.substring(i + 2);
         if (c0 == 'l') {
            flag = !flag;
         } else if (c0 == 'n') {
            s1 = s1 + '\n';
         } else {
            s1 = s1 + c0;
         }
      }

      if (flag) {
         s1 = s1 + translateSymbols(s, maggie, symbols);
      } else {
         s1 = s1 + s;
      }

      return s1;
   }

   static String escapeBackslashes(String s) {
      String s1 = "";

      int i;
      while ((i = s.indexOf("\\")) != -1) {
         s1 = s1 + s.substring(0, i) + "\\\\";
         s = s.substring(i + 1);
      }

      return s1 + s;
   }

   static Expression parseFormula(String s) throws FormulaParseException {
      return parseFormula(s, false, false, false);
   }

   static Expression parseFormula(String s, boolean flag) throws FormulaParseException {
      return parseFormula(s, flag, false, false);
   }

   static Expression parseFormula(String s, boolean flag, boolean flag1) throws FormulaParseException {
      return parseFormula(s, flag, flag1, false);
   }

   static Expression parseFormula(String s, boolean flag, boolean flag1, boolean flag2) throws FormulaParseException {
      try {
         if (s != null && !s.trim().equals("")) {
            FormulaParser.reinit(new StringReader(s + "\n"));
            Expression expression = FormulaParser.parse();
            if (expression != null) {
               expression.linkVariables();
            }

            String s1;
            if (!flag1 && (s1 = findNumberedPlaceholder(s)) != null) {
               throw new FormulaParseException("Parse error, column " + (s.indexOf(s1) + 1) + ".");
            } else {
               String s4;
               if (!flag2 && (s4 = findQuestionVariable(s)) != null) {
                  throw new FormulaParseException("Parse error, column " + (s.indexOf(s4) + 1) + ".");
               } else {
                  FormulaParseNode formulaparsenode = new FormulaParseNode(expression);
                  formulaparsenode.text = s;
                  if (!formulaparsenode.isParenthesizationValid()) {
                     throw new FormulaParseException(s + " is not well formed");
                  } else if (!flag && expression instanceof Term) {
                     throw new FormulaParseException("Syntax error: expected Formula but found Term");
                  } else {
                     return expression;
                  }
               }
            }
         } else {
            return null;
         }
      } catch (FormulaParseException formulaparseexception) {
         String s3 = formulaparseexception.getMessage();
         int j = parseErrorColumn(s3);
         if (j != -1) {
            int[] aint1 = new int[]{j};
            translateSymbols(s, maggie, symbols, aint1);
            throw new FormulaParseException("Parse error at position " + aint1[0] + ".");
         } else {
            throw formulaparseexception;
         }
      } catch (FormulaLexerError formulalexererror) {
         String s2 = formulalexererror.getMessage();
         int i = parseErrorColumn(s2);
         if (i != -1) {
            int[] aint = new int[]{i};
            translateSymbols(s, maggie, symbols, aint);
            throw new FormulaParseException("Lexical error at position " + aint[0] + ".");
         } else {
            throw new FormulaParseException(s2);
         }
      }
   }

   static Integer parseInteger(String s) {
      return parseInteger(s, 10);
   }

   static Integer parseInteger(String s, int i) {
      try {
         return Integer.valueOf(s, i);
      } catch (NumberFormatException numberformatexception) {
         return null;
      }
   }

   static Long parseLong(String s) {
      return parseLong(s, 10);
   }

   static Long parseLong(String s, int i) {
      try {
         return Long.valueOf(s, i);
      } catch (NumberFormatException numberformatexception) {
         return null;
      }
   }

   static String toHex8(int i) {
      String s = "00000000" + Integer.toString(i, 16);
      return s.substring(s.length() - 8);
   }

   static String findNumberedPlaceholder(String s) {
      int i = s == null ? 0 : s.length();
      int j = 0;

      while (j < i - 2) {
         char c0;
         if (s.charAt(j) == '{' && (c0 = s.charAt(j + 1)) >= '1' && c0 <= '9') {
            int k = j + 2;

            while (k < i && (c0 = s.charAt(k)) >= '0' && c0 <= '9') {
               k++;
            }

            if (c0 == '}') {
               return s.substring(j, k + 1);
            }

            if (k == i) {
               break;
            }

            j = k;
         } else {
            j++;
         }
      }

      return null;
   }

   static String sentenceLetter(int i) {
      int j = sentenceLetters.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return sentenceLetters.substring(i, i + 1);
   }

   static String predicateLetter(int i) {
      int j = predicateLetters.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return predicateLetters.substring(i, i + 1);
   }

   static String operationLetter(int i) {
      int j = operationLetters.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return operationLetters.substring(i, i + 1);
   }

   static String variableLetter(int i) {
      int j = variableLetters.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return variableLetters.substring(i, i + 1);
   }

   static String defaultVariable(int i) {
      int j = "xyzuvw".length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return "xyzuvw".substring(i, i + 1);
   }

   static String reverse(String s) {
      if (s == null) {
         return null;
      } else {
         String s1 = "";
         int i = s.length();

         while (--i >= 0) {
            s1 = s1 + s.charAt(i);
         }

         return s1;
      }
   }

   static String[] splitChars(String s) {
      int i = s == null ? 0 : s.length();
      String[] astring = new String[i];

      for (int j = 0; j < i; j++) {
         astring[j] = s.substring(j, j + 1);
      }

      return astring;
   }

   static String findQuestionVariable(String s) {
      int i = s == null ? 0 : s.length();
      int j = 0;

      while (j < i && s.charAt(j) != '?') {
         j++;
      }

      if (j == i) {
         return null;
      } else {
         int k = j + 1;

         char c0;
         while (k < i && (c0 = s.charAt(k)) >= 'A' && c0 <= 'Z') {
            k++;
         }

         return s.substring(j, k);
      }
   }

   static int parseErrorColumn(String s) {
      String s1 = ", column ";
      String s2 = ".";
      int i;
      if (s != null && (i = s.indexOf(s1)) != -1) {
         int k = i + s1.length();
         int j;
         if ((j = s.indexOf(s2, k)) != -1) {
            try {
               return Integer.parseInt(s.substring(k, j));
            } catch (NumberFormatException numberformatexception) {
            }
         }
      }

      return -1;
   }

   static Theorem getTheorem(Integer integer) {
      return ruleTable == null ? null : ruleTable.getTheorem(integer);
   }

   static Rule getRule(String s) {
      return ruleTable == null ? null : ruleTable.findRule(s);
   }

   static void listRules(String s, Vector vector, IntervalSet intervalset, boolean flag) {
      String s2 = s.trim();

      while (!s2.equals("")) {
         int i = s2.indexOf(".");
         String s1;
         if (i == -1) {
            s1 = s2;
            s2 = "";
         } else {
            s1 = s2.substring(0, i).trim();
            s2 = s2.substring(i + 1).trim();
         }

         if ("~{".indexOf(s1.charAt(0)) != -1) {
            intervalset.union(new IntervalSet(s1));
         } else {
            Integer integer;
            if ((integer = SchematicRule.parseTheoremNumber(s1)) != null) {
               intervalset.union(IntervalSet.singleton(integer));
            } else {
               Rule rule;
               if ((rule = getRule(s1)) != null) {
                  if (flag) {
                     SchematicRule[] aschematicrule = rule.getAllForms();
                     int j = aschematicrule.length;

                     for (int k = 0; k < j; k++) {
                        if (!vector.contains(aschematicrule[k].name)) {
                           vector.addElement(aschematicrule[k].name);
                        }
                     }
                  } else if (!vector.contains(rule.name)) {
                     vector.addElement(rule.name);
                  }
               } else {
                  System.out.println("unknown rule: " + s1);
               }
            }
         }
      }
   }

   static int parseFontSize(String s) {
      int i = s.indexOf("/");
      if (i >= 0) {
         Integer integer = parseInteger(s.substring(0, i));
         Integer integer1 = parseInteger(s.substring(i + 1));
         if (integer != null && integer1 != null) {
            int j = integer;
            int k = integer1;
            if (j != 0 && k != 0) {
               return ProgressDialog.getDefaultFontSize() * j / k;
            }
         }
      } else {
         Integer integer2 = parseInteger(s);
         if (integer2 != null) {
            int l = integer2;
            if (l != 0) {
               return l;
            }
         }
      }

      return fontSize;
   }

   static Font getFont(int i) {
      return getFont(i, 1);
   }

   static Font getFont(int i, int j) {
      Font font;
      return bundledFonts != null && j <= 1 && (font = bundledFonts[j]) != null ? font.deriveFont((float)i) : new Font("SanSerif", j, i);
   }

   static String chooseOpenFile(ModuleFrame moduleframe, String s, String s1) {
      FileDialog filedialog = new FileDialog(moduleframe, s);
      Dimension dimension = new Dimension(400, 250);
      filedialog.setLocation(MessageDialog.centeredLocation(dimension));
      if (s1 != null) {
         filedialog.setDirectory(s1);
      }

      filedialog.setVisible(true);
      String s2 = filedialog.getFile();
      String s3 = filedialog.getDirectory();
      filedialog.dispose();
      return s2 == null ? null : s3 + s2;
   }

   static String chooseSaveFile(ModuleFrame moduleframe, String s, String s1) {
      FileDialog filedialog = new FileDialog(moduleframe, s, 1);
      Dimension dimension = new Dimension(400, 250);
      filedialog.setLocation(MessageDialog.centeredLocation(dimension));
      if (s1 != null) {
         filedialog.setDirectory(s1);
      }

      filedialog.setVisible(true);
      String s2 = filedialog.getFile();
      String s3 = filedialog.getDirectory();
      filedialog.dispose();
      return s2 == null ? null : s3 + s2;
   }

   static boolean isDescendant(Container container, Component component) {
      if (container == null) {
         return false;
      } else {
         Component object = component;

         while (object != null && object != container) {
            object = object.getParent();
         }

         return object != null;
      }
   }

   static Container commonAncestor(Component component, Component component1) {
      if (component1 == null) {
         return null;
      } else {
         Container container = component != null && !(component instanceof Container) ? component.getParent() : (Container)component;

         while (container != null && !isDescendant(container, component1)) {
            container = container.getParent();
         }

         return container;
      }
   }

   static Rectangle boundsRelativeTo(Component component, Component component1) {
      Container container = commonAncestor(component, component1);
      Rectangle rectangle = boundsIn(component, container);
      if (component1 != null) {
         Rectangle rectangle1 = boundsIn(component1, container);
         rectangle.x = rectangle.x - rectangle1.x;
         rectangle.y = rectangle.y - rectangle1.y;
      }

      return rectangle;
   }

   static Rectangle boundsIn(Component component, Container container) {
      if (component == null) {
         return null;
      } else {
         Rectangle rectangle = component.getBounds();
         if (component == container) {
            rectangle.x = 0;
            rectangle.y = 0;
            return rectangle;
         } else {
            Container container1 = component.getParent();

            while (container1 != null && container1 != container) {
               Container container2 = getParentOf(container1);
               Rectangle rectangle1 = container1.getBounds();
               rectangle.x = rectangle.x + rectangle1.x;
               rectangle.y = rectangle.y + rectangle1.y;
               container1 = container2;
            }

            return container1 == container ? rectangle : null;
         }
      }
   }

   static Container getParentOf(Component component) {
      Container container = component.getParent();
      if (container != null && component instanceof Window) {
         Component[] acomponent = container.getComponents();
         int i = acomponent.length;

         while (--i >= 0) {
            if (component == acomponent[i]) {
               return container;
            }
         }

         return null;
      } else {
         return container;
      }
   }

   static Rectangle getInteriorBounds(Container container) {
      Insets insets = container.getInsets();
      Dimension dimension = container.getSize();
      return new Rectangle(insets.left, insets.top, dimension.width - insets.left - insets.right, dimension.height - insets.top - insets.bottom);
   }

   static void addCredentials(Credentials credentialsx) {
      if (credentials == null) {
         credentials = new Hashtable();
      }

      credentials.put(credentialsx.user.toLowerCase(), credentialsx);
   }

   static Credentials getCredentials(String s) {
      return credentials != null && s != null ? (Credentials)credentials.get(s.toLowerCase()) : null;
   }

   static Dialog findDialog(Component object) {
      while (object != null) {
         if (object instanceof Dialog) {
            return (Dialog)object;
         }

         object = object.getParent();
      }

      return null;
   }

   static Dialog getDialog(JComponent jcomponent) {
      JRootPane jrootpane = jcomponent.getRootPane();
      if (jrootpane == null) {
         return null;
      } else {
         Container container = jrootpane.getParent();
         if (container == null) {
            return null;
         } else {
            Container container1;
            while (!(container instanceof Dialog) && (container1 = container.getParent()) != null) {
               container = container1;
            }

            return container instanceof Dialog ? (Dialog)container : null;
         }
      }
   }

   static Window getWindow(JComponent jcomponent) {
      JRootPane jrootpane = jcomponent.getRootPane();
      if (jrootpane == null) {
         return null;
      } else {
         Container container = jrootpane.getParent();
         if (container == null) {
            return null;
         } else {
            Container container1;
            while (!(container instanceof Window) && (container1 = container.getParent()) != null) {
               container = container1;
            }

            return container instanceof Window ? (Window)container : null;
         }
      }
   }

   static Frame findFrame(Component component) {
      Container container = component != null && !(component instanceof Container) ? component.getParent() : (Container)component;

      while (container != null && !(container instanceof Frame)) {
         container = container.getParent();
      }

      return (Frame)container;
   }

   static JFrame findJFrame(Component component) {
      Container container = component != null && !(component instanceof Container) ? component.getParent() : (Container)component;

      while (container != null && !(container instanceof JFrame)) {
         container = container.getParent();
      }

      return (JFrame)container;
   }

   static void setAlwaysOnTop(Window window, boolean flag) {
      window.setAlwaysOnTop(flag);
   }

   static Vector flatten(Vector vector, boolean flag) {
      if (vector == null) {
         return null;
      } else {
         Vector vector1 = new Vector();
         flattenInto(vector, flag, vector1);
         return vector1;
      }
   }

   static void flattenInto(Vector vector, boolean flag, Vector vector1) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (object instanceof Vector) {
            flattenInto((Vector)object, flag, vector1);
         } else if (!flag || !vector1.contains(object)) {
            vector1.addElement(object);
         }
      }
   }

   static void appendAll(Vector vector, Vector vector1, boolean flag) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (!flag || !vector1.contains(object)) {
            vector1.addElement(object);
         }
      }
   }

   static Hashtable mergeTables(Hashtable hashtable, Hashtable hashtable1, boolean flag) {
      if (hashtable != null) {
         if (hashtable1 == null) {
            hashtable1 = new Hashtable();
         }

         Enumeration enumeration = hashtable.keys();

         while (enumeration.hasMoreElements()) {
            Object object = enumeration.nextElement();
            if (flag || hashtable1.get(object) == null) {
               hashtable1.put(object, hashtable.get(object));
            }
         }
      }

      return hashtable1;
   }

   static int indexOf(Object[] aobject, Object object) {
      return indexOf(aobject, object, 0);
   }

   static int indexOf(Object[] aobject, Object object, int i) {
      int j = aobject == null ? 0 : aobject.length;
      if (object == null) {
         for (int k = i; k < j; k++) {
            if (aobject[k] == null) {
               return k;
            }
         }
      } else {
         for (int l = i; l < j; l++) {
            if (object.equals(aobject[l])) {
               return l;
            }
         }
      }

      return -1;
   }

   static void showHeadlines(boolean flag) {
      String s = getLink("word");
      String s1 = getLink("headlines");
      String s2 = getLink("headVers");
      if (s != null && s1 != null && (!flag || s2 != null)) {
         if (s2 != null) {
            if (s2.compareTo(user.getField("headVers", "")) > 0) {
               user.put("headVers", s2);
               user.save();
            } else if (flag) {
               return;
            }
         }

         String[] astring = new String[]{s, "\"" + s1 + "\""};

         try {
            Runtime.getRuntime().exec(astring);
         } catch (IOException ioexception) {
         }
      }
   }

   static String askFeedback() {
      ModuleFrame moduleframe = new ModuleFrame();
      JScrollPane jscrollpane = new JScrollPane();
      FormulaTextPane formulatextpane = new FormulaTextPane(true);
      String[] astring = new String[]{"Submit", "Cancel"};
      jscrollpane.setViewportView(formulatextpane);
      MessageDialog messagedialog = new MessageDialog(moduleframe, "Feedback", jscrollpane, astring);
      Dimension dimension = new Dimension(700, 500);
      messagedialog.setSize(dimension);
      formulatextpane.requestFocus();
      messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
      moduleframe.dispose();
      return messagedialog.selectedButton == 0 ? translateSymbols(formulatextpane.getText(), symbols, maggie) : null;
   }

   static void resetOptions() {
      debug = false;
      printingEnabled = true;
      overheadColors = false;
      remote = false;
      noNetwork = false;
      hiddenMode = false;
      noCoreProblems = false;
      altSymbols = false;
      maxBackups = 1;
      backupName = null;
      restoreName = null;
      soloPort = null;
      repeatAuth = false;
      optionO = null;
      credentials = null;
   }

   static boolean readOptions(Reader reader) {
      if (reader == null) {
         return false;
      } else {
         TaggedRecord taggedrecord = new TaggedRecord(reader, true);

         while (taggedrecord.readNext()) {
            String s = taggedrecord.getName();
            if (s != null && s.trim().equalsIgnoreCase("logic")) {
               int[] aint = taggedrecord.indexesOfAnyTag("+ufcbrpo");
               int i = aint.length;

               for (int j = 0; j < i; j++) {
                  char c0 = taggedrecord.tagAt(aint[j]);
                  String s1 = taggedrecord.valueAt(aint[j]).trim();
                  if (c0 == '+') {
                     if (s1.equalsIgnoreCase("debug")) {
                        debug = true;
                     } else if (s1.equalsIgnoreCase("noprint")) {
                        printingEnabled = false;
                     } else if (s1.equalsIgnoreCase("altsymbols")) {
                        altSymbols = true;
                        symbols = FormulaParser.getSyntax() == 2 ? kaplan1 : kaplan2;
                     } else if (s1.equalsIgnoreCase("overhead")) {
                        overheadColors = true;
                     } else if (s1.equalsIgnoreCase("remote")) {
                        remote = true;
                     } else if (s1.equalsIgnoreCase("nonet")) {
                        noNetwork = true;
                     } else if (s1.equalsIgnoreCase("hidden")) {
                        hiddenMode = true;
                     } else if (s1.equalsIgnoreCase("noCoreProbs")) {
                        noCoreProblems = true;
                     } else if (s1.equalsIgnoreCase("repeatAuth")) {
                        repeatAuth = true;
                     } else if (s1.equalsIgnoreCase("hideSensitive")) {
                        hideSensitive = true;
                     }
                  } else if (c0 == 'u') {
                     Credentials credentialsx = new Credentials(s1);
                     if (credentialsx.password != null) {
                        credentialsx.password = Scrambler.unscramble(new String(new Base64Codec(credentialsx.password).getBytes()));
                     }

                     addCredentials(credentialsx);
                  } else if (c0 == 'f') {
                     fontSize = parseFontSize(s1);
                     ProgressDialog.setFontSize(fontSize);
                     UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
                  } else if (c0 == 'c') {
                     Integer integer = parseInteger(s1);
                     if (integer != null) {
                        maxBackups = integer;
                     }
                  } else if (c0 == 'b') {
                     backupName = s1;
                  } else if (c0 == 'r') {
                     restoreName = s1;
                  } else if (c0 == 'p') {
                     soloPort = parseInteger(s1);
                  } else if (c0 == 'o') {
                     optionO = s1;
                  }
               }
            }
         }

         if (noNetwork) {
            remote = false;
         }

         taggedrecord.close();
         return true;
      }
   }

   static void applyOverrideFontSize() {
      String s = (String)overrides.get("FontSize");
      if (s != null) {
         fontSize = parseFontSize(s);
         ProgressDialog.setFontSize(fontSize);
         UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
      }
   }

   static void applyPrefsFontSize() {
      String s = prefs.getPref("font size");
      if (s != null) {
         fontSize = parseFontSize(s);
         ProgressDialog.setFontSize(fontSize);
         UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
      }
   }

   static void applyColorPrefs() {
      String s = workPrefs.getPref("monochrome");
      monochrome = "true".equalsIgnoreCase(s);
      moduleColors = monochrome ? monochromeColors : bruinColors;
   }

   static boolean selectorMatches(ProblemSelector problemselector, String s) {
      return problemselector != null && (s == null ? problemselector.hasFlag('u') : problemselector.contains(s));
   }

   static void setSyntax(int i) {
      FormulaParser.setSyntax(i);
      i = FormulaParser.getSyntax();
      switch (i) {
         case 1:
            symbols = kaplan1;
            encodedSymbols = kaplan5;
            htmlSymbols = html1;
            sentenceLetters = "PQRSTUVWXYZ";
            predicateLetters = "FGHIJKLMNO";
            operationLetters = "ABCDE";
            variableLetters = "abcdefghijklmnopqrstuvwxyz";
            ruleDir = new File(configDir, "syntax1");
            break;
         case 2:
            symbols = kaplan2;
            encodedSymbols = kaplan6;
            htmlSymbols = html2;
            sentenceLetters = "PQRSTUVWXYZ";
            predicateLetters = "FGHIJKLMNOABCDE";
            operationLetters = "abcdefgh";
            variableLetters = "ijklmnopqrstuvwxyz";
            ruleDir = new File(configDir, "syntax2");
      }
   }

   static ScrambledReader openDataFile(String s, boolean flag) {
      return openDataFile(s, flag, true);
   }

   static ScrambledReader openDataFile(String s, boolean flag, boolean flag1) {
      Object object = null;
      if (flag) {
         try {
            return new PlainRecordReader(new FileReader(new File(workDir, s)));
         } catch (IOException ioexception1) {
         }
      }

      if ((object = getLink(s)) != null) {
         try {
            File file1 = new File((String)object);
            if (!file1.exists()) {
               file1 = ServerConnection.resolvePath(configDir, (String)object);
            }

            return new ScrambledReader(new FileReader(file1), flag1 ? scrambleKey : null);
         } catch (IOException ioexception) {
         }
      }

      return null;
   }

   static ScrambledReader openLocalFile(String s) {
      return openLocalFile(s, false, true);
   }

   static ScrambledReader openLocalFile(String s, boolean flag) {
      return openLocalFile(s, flag, true);
   }

   static ScrambledReader openLocalFile(String s, boolean flag, boolean flag1) {
      String s1 = getLink(s);
      File file1 = flag ? ServerConnection.editDir : ServerConnection.localDir;
      if (s1 != null && file1 != null) {
         String s2 = new File(file1, new File(s1).getName()).getPath();

         try {
            return new ScrambledReader(new FileReader(s2), flag1 ? scrambleKey : null);
         } catch (IOException ioexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   static ScrambledReader openProblemFile(String s, boolean flag, boolean flag1) {
      String s1 = getLink(s);
      if (s1 == null) {
         return null;
      } else {
         File file1 = flag ? new File(configDir, "local") : ruleDir;
         String s2 = new File(file1, new File(s1).getName()).getPath();

         try {
            return new ScrambledReader(new FileReader(s2), flag1 ? scrambleKey : null);
         } catch (IOException ioexception) {
            return null;
         }
      }
   }

   static FileWriter openWriter(String s, boolean flag, boolean flag1) {
      Object object = null;
      if (flag1) {
         try {
            return new FileWriter(new File(workDir, s).getPath(), flag);
         } catch (IOException ioexception1) {
         }
      }

      if ((object = getLink(s)) != null) {
         try {
            return new FileWriter((String)object, flag);
         } catch (IOException ioexception) {
         }
      }

      return null;
   }

   RandomAccessFile openRandomAccessFile(String s, String s1, boolean flag) {
      Object object = null;
      if (flag) {
         try {
            return new RandomAccessFile(new File(workDir, s), s1);
         } catch (IOException ioexception1) {
         }
      }

      if ((object = getLink(s)) != null) {
         try {
            return new RandomAccessFile((String)object, s1);
         } catch (IOException ioexception) {
         }
      }

      return null;
   }

   static void extractToConfigDir(String s) {
      HttpDownloader.unzip(new File(s), configDir);
   }

   static Hashtable readCoreInfo() {
      ScrambledReader scrambledreader = null;
      Hashtable hashtable = null;
      String s = null;

      try {
         File file1 = new File(configDir, "coreinfo.txt");
         if (!file1.exists()) {
            file1 = new File(configDir, "spirit.txt");
            if (!file1.exists()) {
               return null;
            }

            s = "the Logic Program is protected by international copyright law";
         }

         scrambledreader = new ScrambledReader(new FileReader(file1), s);
         hashtable = new Hashtable();

         String s1;
         while ((s1 = scrambledreader.readLine()) != null) {
            int i;
            if (!TaggedRecord.isBlankOrComment(s1) && (i = s1.indexOf(":")) != -1) {
               hashtable.put(s1.substring(0, i).trim().toUpperCase(), s1.substring(i + 1).trim());
            }
         }
      } catch (IOException ioexception1) {
         hashtable = null;
      } finally {
         if (scrambledreader != null) {
            try {
               scrambledreader.close();
            } catch (IOException ioexception) {
            }
         }
      }

      if (coreInfo == null && hashtable != null) {
         scrambleKey = s;
      }

      return hashtable;
   }

   static Hashtable readLinks(File file1, boolean flag) {
      ScrambledReader scrambledreader = null;
      Hashtable hashtable = null;
      if (file1 != null && progDir != null) {
         String s;
         try {
            File file2;
            if (scrambleKey == null) {
               file2 = new File(file1, "links.txt");
            } else {
               file2 = new File(file1, "ghost.txt");
            }

            if (file2.exists()) {
               scrambledreader = new ScrambledReader(new FileReader(file2), scrambleKey);
               hashtable = new Hashtable();

               while ((s = scrambledreader.readLine()) != null) {
                  int i;
                  if (!TaggedRecord.isBlankOrComment(s) && (i = s.indexOf(":")) != -1) {
                     hashtable.put(s.substring(0, i).trim().toUpperCase(), s.substring(i + 1).trim());
                  }
               }

               if (flag) {
                  Integer integer = parseInteger((String)hashtable.get("SYNTAX"));
                  setSyntax(integer != null ? integer : 1);
               }

               rebaseLinks(hashtable, "PROGDIR", progDir + "");
               rebaseLinks(hashtable, "LINKDIR", file1 + "");
               rebaseLinks(hashtable, "RULEDIR", ruleDir + "");
               return hashtable;
            }

            hashtable = null;
         } catch (IOException ioexception1) {
            return null;
         } finally {
            if (scrambledreader != null) {
               try {
                  scrambledreader.close();
               } catch (IOException ioexception) {
               }
            }
         }

         return hashtable;
      } else {
         return null;
      }
   }

   static void rebaseLinks(Hashtable hashtable, String s, String s1) {
      String s2 = (String)hashtable.get(s);
      if (s2 != null) {
         Enumeration enumeration = hashtable.keys();

         while (enumeration.hasMoreElements()) {
            String s3 = (String)enumeration.nextElement();
            if (!s3.equals(s)) {
               String s4 = (String)hashtable.get(s3);
               if (s4.startsWith(s2)) {
                  hashtable.put(s3, s1 + s4.substring(s2.length()));
               }
            }
         }
      }
   }

   static File canonicalFile(String s) {
      return s == null ? null : canonicalFile(new File(s));
   }

   static File canonicalFile(File file1) {
      if (file1 == null) {
         return null;
      } else {
         try {
            return file1.getCanonicalFile();
         } catch (IOException ioexception) {
            return null;
         }
      }
   }

   static String getValue(Hashtable hashtable, String s, String s1) {
      if (hashtable != null && s != null) {
         String s2 = (String)hashtable.get(s.trim().toUpperCase());
         return s2 == null ? s1 : s2;
      } else {
         return s1;
      }
   }

   static String getLink(String s, String s1) {
      return getValue(links, s, s1);
   }

   static String getLink(String s) {
      String s1 = getValue(links, s, null);
      if (s.trim().toLowerCase().equals("feedback")) {
         try {
            s1 = s1 + "?name=" + URLEncoder.encode(user.getFirstName() + " " + user.getLastName(), "UTF-8");
            s1 = s1 + "&email=" + URLEncoder.encode(user.getEmail(), "UTF-8");
            s1 = s1 + "&institution=" + URLEncoder.encode(user.getInstitution(), "UTF-8");
            s1 = s1 + "&term=" + URLEncoder.encode(user.getTerm(), "UTF-8");
            s1 = s1 + "&ident=" + URLEncoder.encode(user.getIdent(), "UTF-8");
            s1 = s1 + "&class=" + URLEncoder.encode(user.getClassName(), "UTF-8");
         } catch (UnsupportedEncodingException unsupportedencodingexception) {
         }
      }

      return s1;
   }

   static String utcTimestamp() {
      SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      simpledateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
      return "UTC: " + simpledateformat.format(new Date());
   }

   static String compactTimestamp() {
      Calendar calendar = Calendar.getInstance();
      String s = "" + calendar.get(1);
      s = s + lastChars("0" + (calendar.get(2) + 1), 2);
      s = s + lastChars("0" + calendar.get(5), 2);
      s = s + ".";
      s = s + lastChars("0" + calendar.get(11), 2);
      s = s + lastChars("0" + calendar.get(12), 2);
      return s + lastChars("0" + calendar.get(13), 2);
   }

   static String lastChars(String s, int i) {
      if (s == null) {
         return null;
      } else {
         int j = s.length();
         return j < i ? s : s.substring(j - i);
      }
   }

   static boolean appendSubmitLog(String s, String s1, String s2) {
      return appendSubmitLog(s, s1, s2, null);
   }

   static boolean appendSubmitLog(String s, String s1, String s2, String s3) {
      boolean flag = true;
      BufferedWriter bufferedwriter = null;
      String s4 = TaggedRecord.nameOf(s2);
      if (s4 == null) {
         return false;
      } else {
         String s5 = compactTimestamp();
         String s6 = Scrambler.md5Base64(s2);
         String s7 = TaggedRecord.formatField(s4, '$');
         s7 = s7 + TaggedRecord.formatField(s1, 'a');
         s7 = s7 + TaggedRecord.formatField(s5, 'd');
         String s8 = s7 + TaggedRecord.formatField(s6, 'w');
         if (s3 != null) {
            s8 = s8 + TaggedRecord.formatField(s3, 'x');
         }

         try {
            bufferedwriter = new BufferedWriter(new FileWriter(new File(workDir, s).getPath(), true));
            bufferedwriter.write(Scrambler.scramble(TaggedRecord.toLine(s8)));
            bufferedwriter.newLine();
         } catch (IOException ioexception1) {
            flag = false;
         }

         if (bufferedwriter != null) {
            try {
               bufferedwriter.close();
            } catch (IOException ioexception) {
            }
         }

         return flag;
      }
   }

   static Hashtable checkSubmitLog(String s, String s1, ProblemSelector problemselector, ProblemRecordEnumeration problemrecordenumeration) {
      if (problemselector != null && !problemselector.isEmpty() && problemrecordenumeration != null) {
         Vector vector = new Vector();

         while (problemrecordenumeration.hasMoreElements()) {
            String s2 = (String)problemrecordenumeration.nextElement();
            if (problemselector.contains(TaggedRecord.nameOf(s2))) {
               vector.addElement(s2);
            }
         }

         if (vector.isEmpty()) {
            return null;
         } else {
            Hashtable hashtable = new Hashtable();

            try {
               TaggedRecord taggedrecord = new TaggedRecord(
                  new ScrambledReader(new FileReader(new File(workDir, s)), "the Logic Program is protected by international copyright law"), true
               );

               while (taggedrecord.readNext()) {
                  String s3 = taggedrecord.getName();
                  String s4 = taggedrecord.valueAt(taggedrecord.indexOfTag('a'));
                  if (problemselector.contains(s3) && s1.equals(s4)) {
                     hashtable.put(s3, taggedrecord.valueAt(taggedrecord.indexOfTag('w')));
                  }
               }

               taggedrecord.close();
            } catch (IOException ioexception) {
            }

            Vector vector1 = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            int i = vector.size();

            for (int j = 0; j < i; j++) {
               String s6 = (String)vector.elementAt(j);
               String s5 = (String)hashtable.get(TaggedRecord.nameOf(s6));
               if (s5 == null) {
                  vector1.addElement(s6);
               } else if (!s5.equals(Scrambler.md5Base64(s6))) {
                  vector2.addElement(s6);
               } else {
                  vector3.addElement(s6);
               }
            }

            Hashtable hashtable1 = new Hashtable();
            if (!vector1.isEmpty()) {
               hashtable1.put("missing", vector1);
            }

            if (!vector2.isEmpty()) {
               hashtable1.put("changed", vector2);
            }

            if (!vector3.isEmpty()) {
               hashtable1.put("handled", vector3);
            }

            return hashtable1;
         }
      } else {
         return null;
      }
   }

   static KeyListener findParentKeyListener(Component object) {
      Component object1 = null;

      while (object != null && !((object1 = object.getParent()) instanceof KeyListener)) {
         object = object1;
      }

      return (KeyListener)object1;
   }

   static void forwardKeyEvent(Container container, KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         if (keyevent.getID() == 401 && keyevent.getKeyCode() == 10 && container instanceof JComponent) {
            JComponent jcomponent = (JComponent)container;
            JButton jbutton = jcomponent.getRootPane().getDefaultButton();
            if (jbutton != null) {
               jbutton.doClick();
               return;
            }
         }

         KeyListener keylistener = findParentKeyListener(container);
         if (keylistener != null) {
            if (keyevent.getID() == 401) {
               keylistener.keyPressed(keyevent);
            } else if (keyevent.getID() == 402) {
               keylistener.keyReleased(keyevent);
            } else if (keyevent.getID() == 400) {
               keylistener.keyTyped(keyevent);
            }
         }
      }
   }

   static {
      ProgressDialog.setFontSize(fontSize);
      UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
   }

   static class ProgramStartup implements Runnable {
      @Override
      public void run() {
         try {
            Class oclass = ClassLoader.getSystemClassLoader().loadClass("org.jdesktop.swinghelper.debug.CheckThreadViolationRepaintManager");
            RepaintManager.setCurrentManager((RepaintManager)oclass.newInstance());
         } catch (ClassNotFoundException classnotfoundexception) {
            System.err.println("Could not load thread violation checker");
         } catch (InstantiationException instantiationexception) {
            System.err.println("Could not create instance of thread violation checker");
         } catch (IllegalAccessException illegalaccessexception) {
            System.err.println("Illegal access involving thread violation checker");
         }

         try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
         } catch (Exception exception) {
            System.out.println(exception.getMessage());
         }

         RepaintManager.currentManager(null).setDoubleBufferingEnabled(true);
         LogicProgram.loadIconsAndFonts();

         while (true) {
            int j;
            do {
               j = LogicProgram.initialize();
               if (j == 1) {
                  LogicProgram.exit();
                  break;
               }
            } while (j == 2);

            int i = LogicProgram.initializeUser();
            if (i != 1 && i != 3) {
               if (i == 0) {
                  if (!LogicProgram.loadRulesAndTheorems()) {
                     LogicProgram.exit();
                  }

                  MessageDialog.disposeHiddenOwner();
                  ServerConnection.cachedLogin = null;
                  LogicProgram.mainMenu = MainMenu.open(null);
                  LogicProgram.showHeadlines(true);
                  return;
               }
            } else {
               LogicProgram.exit();
            }
         }
      }
   }
}
