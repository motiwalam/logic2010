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
   static C_V f529 = null;
   static OverrideSettings overrides = null;
   static PreferencesFile prefs = null;
   static PreferencesFile workPrefs = null;
   static UserInfo user;
   static RuleTable f534 = null;
   static Hashtable links = null;
   static Hashtable coreInfo = null;
   static String scrambleKey = null;
   static final int f538 = 16;
   static int fontSize = ProgressDialog.m1288();
   static Font[] f540;
   static Dimension f541 = Toolkit.getDefaultToolkit().getScreenSize();
   static final int f542 = 0;
   static final int f543 = 1;
   static final int f544 = 2;
   static final int f545 = 3;
   static final int f546 = 4;
   static final int f547 = 5;
   static final int f548 = 0;
   static final int f549 = 2;
   static File configDir;
   static File f551;
   static File linkDir;
   static File progDir;
   static File f554;
   static File workDir;
   static File f556;
   static File f557;
   static File f558;
   static File userFile;
   static File f560;
   static File ruleDir;
   static File f562;
   static File f563;
   static String f564;
   static String f565;
   static int f566 = 1;
   static String f567 = null;
   static String f568 = null;
   static String codeVersion = null;
   static String arch = null;
   static boolean f571 = true;
   static boolean debug = false;
   static boolean printingEnabled = true;
   static boolean overheadColors = false;
   static boolean remote = false;
   static boolean noNetwork = false;
   static boolean f577 = false;
   static boolean f578 = false;
   static boolean f579 = true;
   static boolean f580 = false;
   static boolean f581 = false;
   static boolean f582 = false;
   static boolean f583 = true;
   static boolean f584 = false;
   static boolean f585 = false;
   static MainMenu mainMenu = null;
   static SingleInstanceGuard instanceGuard = null;
   static Integer f588 = null;
   static boolean f589 = false;
   static boolean f590 = false;
   static boolean f591 = false;
   static UserInfo f592;
   static String f593 = null;
   static ArrayList<Image> f594 = null;
   static Hashtable credentials = null;
   static String[] symbols = kaplan1;
   static String[] f597 = kaplan5;
   static String[] f598 = html1;
   static String f599 = "PQRSTUVWXYZ";
   static String f600 = "FGHIJKLMNO";
   static String f601 = "ABCDE";
   static String f602 = "abcdefghijklmnopqrstuvwxyz";
   static boolean f603 = false;
   static Color[] f604 = new Color[]{
      bruinGold, bruinBlue, bruinBluf, bruinRed, bruinWhite, bruinNavy, bruinWhite, bruinBlue, bruinGray, bruinBlack, bruinMaize
   };
   static Color[] f605 = new Color[]{
      bruinBlack, bruinWhite, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinWhite
   };
   static Color[] f606 = new Color[]{
      bruinBlack, bruinWhite, bruinAsh, bruinRed, bruinWhite, bruinBlack, bruinWhite, bruinBlack, bruinGray, bruinBlack, bruinWhite
   };
   static Color[] f607 = f603 ? f606 : f604;

   public static void main(String[] astring) {
      SwingUtilities.invokeLater(new LogicProgram.C__A());
   }

   static void loadIconsAndFonts() {
      int[] aint = new int[]{256, 128, 48, 32, 24, 16};
      f594 = new ArrayList<>(aint.length);
      Toolkit toolkit = Toolkit.getDefaultToolkit();

      for (int i = 0; i < aint.length; i++) {
         URL url = ClassLoader.getSystemResource("images/Logic2010_" + aint[i] + ".png");
         ImageIcon imageicon = new ImageIcon(url);
         f594.add(imageicon.getImage());
      }

      f540 = new Font[2];
      GraphicsEnvironment graphicsenvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

      try {
         InputStream inputstream;
         if ((inputstream = ClassLoader.getSystemResourceAsStream("fonts/mplus-1p-lsp-medium.ttf")) != null) {
            graphicsenvironment.registerFont(f540[0] = Font.createFont(0, inputstream));
            inputstream.close();
         }

         if ((inputstream = ClassLoader.getSystemResourceAsStream("fonts/mplus-1p-lsp-bold.ttf")) != null) {
            graphicsenvironment.registerFont(f540[1] = Font.createFont(0, inputstream));
            inputstream.close();
         }
      } catch (Exception exception) {
         System.out.println("Font load failure");
      }
   }

   static int initialize() {
      if (!f591) {
         String s = System.getProperty("config.dir");
         if (s == null) {
            s = System.getProperty("user.dir");
         }

         configDir = canonicalFile(s);
         f556 = new File(configDir, "override.txt");
         f557 = new File(configDir, "prefs.txt");
         workDir = new File(configDir, "work");
         f558 = new File(workDir, "prefs.txt");
         if (!m965(configDir)) {
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
         f554 = canonicalFile(System.getProperty("root.dir"));
         String s1 = System.getProperty("from.ide");
         if ("1".equals(s1)) {
            f580 = true;
         }

         f563 = new File(progDir, "trash");
         f560 = progDir == null ? null : new File(progDir, "loadinfo.txt");
         f551 = canonicalFile(System.getProperty("copy.dir"));
         f564 = System.getProperty("com.ms.windir");
         f565 = System.getProperty("fail.dir");
         if (f560.exists()) {
            Hashtable hashtable = m966();
            String s2 = (String)hashtable.get("linkDir");
            if (s2 != null) {
               linkDir = canonicalFile((String)hashtable.get("linkDir"));
            }
         }

         overrides = new OverrideSettings();
         if (f556.exists()) {
            try {
               overrides.m2083(new FileReader(f556));
            } catch (Exception exception) {
               System.out.println(exception.getMessage());
            }
         }

         prefs = new PreferencesFile();
         prefs.load(f557);
         workPrefs = new PreferencesFile();
         workPrefs.load(f558);
      }

      ProgressDialog progressdialog = new ProgressDialog(LPInfo.programName, "Starting the program...", false);
      progressdialog.m1284(30, 20);
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
               f562 = new File(ruleDir, "text");
               if (!f562.exists()) {
                  f562 = null;
               } else {
                  File[] afile = f562.listFiles();
                  if (afile == null || afile.length == 0) {
                     f562 = null;
                  }
               }

               if (!Message.loadMessages()) {
                  progressdialog.dispose();
                  JOptionPane.showMessageDialog(null, "Could not open the program messages file.", "No Messages", 0);
                  return 1;
               }

               if (file3 == null && (!f591 || ServerConnection.f481) && !f560.exists()) {
                  String s3 = getLink("adminDir");
                  File file1 = s3 == null ? null : ServerConnection.resolvePath(configDir, s3);
                  s3 = getLink("nonetDir");
                  File file2 = s3 == null ? null : ServerConnection.resolvePath(configDir, s3);
                  if ((file3 = UserSetup.m2108(file1, file2)) != null) {
                     linkDir = file3;
                  }

                  ServerConnection.f481 = false;
               } else {
                  file3 = null;
               }

               if (file3 == null) {
                  if (f565 != null) {
                     progressdialog.dispose();
                     JOptionPane.showMessageDialog(null, "Could not set directory to " + DelimitedTokenizer.m1139(f565, "\\") + ".", "No Work Directory", 0);
                     return 1;
                  }

                  m969();
                  if (!workDir.exists()) {
                     workDir.mkdir();
                  } else if (workDir.isFile()) {
                     progressdialog.dispose();
                     JOptionPane.showMessageDialog(null, DelimitedTokenizer.m1139(workDir + "", "\\") + " is not a directory.", "No User Directory", 0);
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
                  readOptions(m1065("options", false));
                  if (overheadColors) {
                     f607 = f606;
                  }

                  if (debug) {
                     m978();
                  }

                  m1058();
                  m1059();
                  m1057();
                  if (!arch.equalsIgnoreCase("macos") && !overrides.m2082("SoloCheck", "yes").equalsIgnoreCase("no")) {
                     if (instanceGuard != null && instanceGuard.f1264 != f588) {
                        instanceGuard.m1937();
                        instanceGuard = null;
                     }

                     if (instanceGuard == null && f588 != null) {
                        instanceGuard = new SingleInstanceGuard(f588, "cogito");
                     }

                     if (instanceGuard != null && !instanceGuard.isAlive() && !instanceGuard.m1933()) {
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
                     f567 = null;
                     f568 = null;
                  }

                  progressdialog.dispose();
                  ServerConnection.m880();
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
                  if (UserSetup.m2111()) {
                     continue;
                  }

                  return 1;
               }

               if (remote) {
                  if (!f591) {
                     if (!f560.exists()) {
                        Boolean obool = UserSetup.m2110(user);
                        if (obool == null) {
                           continue;
                        }

                        if (!obool) {
                           return 1;
                        }

                        obool = UserSetup.m2107(user, false, false);
                        if (obool == null) {
                           continue;
                        }

                        if (!obool) {
                           return 1;
                        }

                        if (user.dirty) {
                           user.save();
                        }

                        obool = AccountManager.m1872(user);
                        if (obool == null) {
                           continue;
                        }

                        if (!obool) {
                           return 1;
                        }
                     } else if (m966() == null) {
                        return 1;
                     }
                  }

                  int i;
                  if ((i = ServerConnection.m862(user)) != 0) {
                     return i;
                  }
               } else if (getCredentials("exam") != null) {
                  Boolean obool2 = UserSetup.m2107(user, true, false);
                  if (obool2 == null) {
                     continue;
                  }

                  if (!obool2) {
                     return 1;
                  }

                  if (user.dirty) {
                     user.save();
                  }
               } else if (ServerConnection.f480) {
                  Boolean obool3 = UserSetup.m2107(user, false, true);
                  if (obool3 == null) {
                     continue;
                  }

                  if (!obool3) {
                     return 1;
                  }
               }
            } else {
               f583 = false;
               if (remote) {
                  Object object;
                  if (f591) {
                     object = f592;
                  } else {
                     object = new NewUserInfo();
                     if ((!f560.exists() || !m967((UserInfo)object)) && AccountManager.m1863((UserInfo)object, "not027", true, false) == null) {
                        return 1;
                     }
                  }

                  int j;
                  if ((j = ServerConnection.m862((UserInfo)object)) != 0) {
                     return j;
                  }
               }

               if (ServerConnection.demoMode) {
                  user = UserInfo.createDemoUser();
                  if (user.dirty) {
                     user.save();
                  }

                  Boolean obool4 = UserSetup.m2107(user, false, true);
                  if (obool4 == null) {
                     continue;
                  }

                  if (!obool4) {
                     return 1;
                  }
               } else {
                  if (!UserSetup.m2114(f568, f567, getCredentials("exam") == null)) {
                     return 1;
                  }

                  if (userFile.exists()) {
                     if ((user = UserInfo.load(false)) == null) {
                        if (UserSetup.m2111()) {
                           continue;
                        }

                        return 1;
                     }

                     if (remote) {
                        if (!m968(user)) {
                           return 1;
                        }

                        int k;
                        if ((k = ServerConnection.m862(user)) != 0) {
                           return k;
                        }
                     }
                  } else {
                     boolean flag = false;
                     if (remote) {
                        NewUserInfo newuserinfo = AccountManager.m1869();
                        if (newuserinfo == null) {
                           return 1;
                        }

                        Boolean obool1 = ServerConnection.m897(f568, f567, newuserinfo);
                        if (obool1 == null) {
                           return 1;
                        }

                        if (obool1) {
                           if (UserSetup.m2112(newuserinfo)) {
                              return 1;
                           }

                           ServerConnection.m903(false);
                           if (!ServerConnection.m899(f568, f567, newuserinfo)) {
                              return 1;
                           }

                           if ((user = UserInfo.load(false)) == null) {
                              if (UserSetup.m2111()) {
                                 continue;
                              }

                              return 1;
                           }

                           flag = true;
                        } else {
                           Boolean obool8 = UserSetup.m2113(newuserinfo);
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

                     while (!user.m683()) {
                        if (!user.m1170()) {
                           return 1;
                        }
                     }

                     if (user.dirty) {
                        user.save();
                     }

                     if (remote) {
                        if (flag) {
                           if (!m968(user)) {
                              return 1;
                           }

                           int l;
                           if ((l = ServerConnection.m862(user)) != 0) {
                              return l;
                           }
                        } else {
                           Boolean obool6 = AccountManager.m1872(user);
                           if (obool6 == null) {
                              continue;
                           }

                           if (!obool6) {
                              return 1;
                           }

                           int i1;
                           if ((i1 = ServerConnection.m862(user)) != 0) {
                              return i1;
                           }
                        }
                     }
                  }

                  Boolean obool5 = UserSetup.m2107(user, false, true);
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
                  if (user.m1160()) {
                     if (ServerConnection.m849()) {
                        if (UserSetup.m2117("not097")) {
                           continue;
                        }

                        return 1;
                     }

                     user.m1162(f567);
                  } else if (!user.m1161().equalsIgnoreCase(f567)) {
                     if (UserSetup.m2117("not098")) {
                        continue;
                     }

                     return 1;
                  }
               } else if (!user.m1160()) {
                  if (UserSetup.m2117("not096")) {
                     continue;
                  }

                  return 1;
               }

               if ("work".equalsIgnoreCase(f567)) {
                  NetworkTask networktask = new NetworkTask(LPInfo.programName, "Checking database for user...");
                  Boolean obool7 = ServerConnection.m888(user, f567, networktask);
                  if (obool7 != null) {
                     if (!obool7) {
                        MessageDialog.showMessage(Message.get("not059"), null, null, null);
                        return 1;
                     }

                     MessageDialog.showMessage(Message.get("not058"), null, null, null);
                  }
               } else if (!ServerConnection.demoMode) {
                  NetworkTask networktask1 = new NetworkTask(LPInfo.programName, "Checking database for user...");
                  if (!ServerConnection.m885(user, "student", networktask1)) {
                     MessageDialog.showMessage(Message.get("not059"), null, null, null);
                     return 1;
                  }
               }
            }

            return 0;
         }
      } finally {
         if (f571 && f560.exists()) {
            f560.delete();
         }
      }
   }

   static boolean loadRulesAndTheorems() {
      ProgressDialog progressdialog = new ProgressDialog(LPInfo.programName, "Reading rules and theorems...", false);
      progressdialog.m1284(20, 10);
      ScrambledReader scrambledreader = openDataFile("theorems", false);
      if (scrambledreader == null) {
         progressdialog.dispose();
         m971("not001", "the theorems file");
         return false;
      } else {
         TheoremTable theoremtable = TheoremTable.m2198(scrambledreader);
         scrambledreader = openDataFile("rules", false);
         if (scrambledreader == null) {
            progressdialog.dispose();
            m971("not001", "the rules file");
            return false;
         } else {
            f534 = RuleTable.m2201(scrambledreader, theoremtable);
            if (theoremtable != null && f534 != null) {
               progressdialog.dispose();
               if (getCredentials("instructor") != null) {
                  ErrorRef errorref = getCredentials("exam") == null ? UserSetup.m2102("instructor", null) : UserSetup.m2102(null, "Instructor");
                  if (errorref != null) {
                     String s = errorref.m716();
                     if (s != null) {
                        MessageDialog.showMessage(Message.get(s), errorref.f428, null, null);
                     }

                     return false;
                  }
               } else if (getCredentials("exam") != null) {
                  ErrorRef errorref1 = UserSetup.m2102("exam", null);
                  if (errorref1 != null) {
                     String s1 = errorref1.m716();
                     if (s1 != null) {
                        MessageDialog.showMessage(Message.get(s1), errorref1.f428, null, null);
                     }

                     return false;
                  }

                  if (!noNetwork && !m964()) {
                     return false;
                  }
               }

               return true;
            } else {
               progressdialog.dispose();
               m971("not002", "theorems and/or rules file");
               return false;
            }
         }
      }
   }

   static boolean m964() {
      ServerSession serversession = ServerConnection.openSession(null);
      if (user.f664 == null) {
         Boolean obool = ServerConnection.m906(serversession, user, null, null);
         if (obool == null || !obool) {
            ServerConnection.m832(serversession, null);
            return false;
         }
      }

      int i = user.f665 == null ? 40 : user.f665;
      Submission submission = new Submission(i, user.f664, serversession, "password");
      submission.problemMd5 = Scrambler.md5Base64("");
      submission.evaluation = "X";
      submission.work = "none";
      submission.problemName = "exam start";
      submission.module = "nul";
      submission.helpCount = 0;
      submission.duration = 0L;
      boolean flag = ServerConnection.submit(submission, null);
      ServerConnection.m838(submission, null);
      return flag;
   }

   static boolean m965(File file1) {
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

   static Hashtable m966() {
      Hashtable hashtable = new Hashtable();
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = new BufferedReader(new FileReader(f560));

         String s;
         while ((s = bufferedreader.readLine()) != null) {
            int i = s.indexOf(58);
            if (i != -1) {
               hashtable.put(s.substring(0, i), s.substring(i + 1));
            }
         }

         if (hashtable.get("workDeleted") != null) {
            f583 = false;
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

   static boolean m967(UserInfo userinfo) {
      Hashtable hashtable = m966();
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

   static boolean m968(UserInfo userinfo) {
      CourseInfo courseinfo = userinfo.m1163();
      CourseInfo courseinfo1 = ServerConnection.m861();
      if (courseinfo == null) {
         if (AccountManager.m1863(userinfo, "not045", true, false) == null) {
            return false;
         }

         if (userinfo.dirty) {
            userinfo.save();
         }
      } else if (courseinfo1 != null && !courseinfo.f623.equals(courseinfo1.f623)) {
         if (AccountManager.m1863(userinfo, "not080", true, true) == null) {
            return false;
         }

         if (userinfo.dirty) {
            userinfo.save();
         }
      }

      return true;
   }

   static void m969() {
      File file1 = new File(configDir, "logic");
      if (file1.isDirectory()) {
         file1.renameTo(workDir);
      }
   }

   static boolean m970(String s) {
      return s.equalsIgnoreCase("Demo") || s.equalsIgnoreCase("Test");
   }

   static void m971(String s, String s1) {
      Hashtable hashtable = Message.params("file name", s1, "user name", user.getFullName());
      MessageDialog.showMessage(Message.get(s), hashtable, null, null);
   }

   static void m972(String s, String s1) {
      Hashtable hashtable = Message.params("problem name", s1);
      MessageDialog.showMessage(Message.get(s), hashtable, null, null);
   }

   static String m973() {
      StringWriter stringwriter = new StringWriter();
      new Throwable().fillInStackTrace().printStackTrace(new PrintWriter(stringwriter));
      return stringwriter.toString();
   }

   static void m974(String s) {
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

   static boolean m975() {
      if (workDir.exists() && !workDir.isFile()) {
         return true;
      } else {
         Hashtable hashtable = Message.params("user dir", DelimitedTokenizer.m1139(workDir + "", "\\"));
         MessageDialog.showMessage(Message.get("not009"), hashtable, null, null);
         return false;
      }
   }

   static boolean m976() {
      if (!m975()) {
         return false;
      } else {
         UserInfo userinfo = UserInfo.load(false);
         String s = userinfo == null ? null : userinfo.m1146();
         String s1 = user == null ? null : user.m1146();
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
         instanceGuard.m1937();
      }

      if (f571 && f560 != null && f560.exists()) {
         f560.delete();
      }

      m979();
      System.exit(0);
   }

   static void m978() {
      if (debug) {
         try {
            f529 = new C_V(new FileOutputStream(new File(workDir, "errors.txt")), true);
         } catch (IOException ioexception1) {
            f529 = null;
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

   static void m979() {
      if (debug) {
         if (f529 != null) {
            try {
               f529.close();
               f529 = null;
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

   static String[] m980(String[] astring) {
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
            return i == 0 ? null : vector.toArray(new String[i]);
         }
      }
   }

   static EditableTextPane m981(String s) {
      return m984(s, 0, 2147483647, false);
   }

   static EditableTextPane m982(String s, boolean flag) {
      return m984(s, 0, 2147483647, flag);
   }

   static EditableTextPane m983(String s, int i, boolean flag) {
      return m984(s, i, i, flag);
   }

   static EditableTextPane m984(String s, int i, int j, boolean flag) {
      EditableTextPane editabletextpane = new EditableTextPane(m995(s, maggie, symbols), i, j, flag);
      editabletextpane.setEditable(false);
      return editabletextpane;
   }

   static C_M m985(String s, int i) {
      return m991(s, i, 0, 2147483647, null);
   }

   static C_M m986(C_e_B c_e_b, int i) {
      return m990(c_e_b, i, 0, 2147483647);
   }

   static C_M m987(String s, int i, int j) {
      return m991(s, i, 0, j, null);
   }

   static C_M m988(C_e_B c_e_b, int i, int j) {
      return m990(c_e_b, i, 0, j);
   }

   static C_M m989(String s, int i, int j, int k) {
      return m991(s, i, j, k, null);
   }

   static C_M m990(C_e_B c_e_b, int i, int j, int k) {
      return m991(c_e_b.f1069, i, j, k, C_CB.m420(c_e_b.f1070));
   }

   static C_M m991(String s, int i, int j, int k, C_CB c_cb) {
      C_M c_m = new C_M();
      c_m.setLayout(new FlowLayout(0, 0, 0));
      StyledDocument styleddocument = m998(s, maggie, symbols, c_cb == null ? null : c_cb.m415());
      EditableTextPane editabletextpane = new EditableTextPane(styleddocument, j, k, false);
      editabletextpane.setEditable(false);
      c_m.add(new C_M(i));
      c_m.add(editabletextpane);
      c_m.add(new C_M(i));
      return c_m;
   }

   static C_ZE m992(String s) {
      s = m995(s, maggie, symbols);
      return new C_ZE(s);
   }

   static String m993(String s) {
      return m995(s, maggie, symbols);
   }

   static StyledDocument m994(String s, C_CB c_cb) {
      return m998(s, maggie, symbols, c_cb);
   }

   static String m995(String s, String[] astring, String[] astring1) {
      return m996(s, astring, astring1, (int[])null);
   }

   static String m996(String s, String[] astring, String[] astring1, int[] aint) {
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

            String s2 = m1015(s);
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
               m1002(aint, s1.length() + i, astring1[j].length() - astring[j].length());
               s1 = s1 + s.substring(0, i) + astring1[j];
               s = s.substring(i + astring[j].length());
            }
         }
      }
   }

   static String m997(String s, String[] astring, String[] astring1, C_n_F c_n_f) {
      return m996(s, astring, astring1, c_n_f == null ? null : c_n_f.f1316);
   }

   static StyledDocument m998(String s, String[] astring, String[] astring1, C_CB c_cb) {
      if (c_cb != null) {
         int i = c_cb.m416();

         for (int j = 0; j < i; j++) {
            m997(s, astring, astring1, (C_n_F)c_cb.f255.elementAt(j));
         }
      }

      s = m995(s, astring, astring1);
      return C_CB.m419(new C_e_B(s, c_cb != null ? c_cb.f255 : null));
   }

   static C_e_B m999(C_e_B c_e_b, String[] astring, String[] astring1) {
      if (c_e_b.f1070 != null) {
         int i = c_e_b.f1070.size();

         for (int j = 0; j < i; j++) {
            m997(c_e_b.f1069, astring, astring1, (C_n_F)c_e_b.f1070.elementAt(j));
         }
      }

      c_e_b.f1069 = m995(c_e_b.f1069, astring, astring1);
      return c_e_b;
   }

   static String m1000(String s) {
      if (s == null) {
         return null;
      } else {
         s = s.trim();
         int i = s.indexOf(32);
         if (i == -1) {
            return s;
         } else {
            int j = s.substring(0, i).lastIndexOf(46);
            return j == -1 ? s : s.substring(j + 1).trim();
         }
      }
   }

   static int[] m1001(String s, int i, String[] astring) {
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

   static void m1002(int[] aint, int i, int j) {
      if (aint != null && j != 0) {
         int k = aint.length;

         for (int l = 0; l < k; l++) {
            if (aint[l] > i && (aint[l] += j) < i) {
               aint[l] = i;
            }
         }
      }
   }

   static String[] m1003(String s) {
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

   static String m1004(String s) {
      boolean flag = false;
      String s1 = "";

      int i;
      while ((i = s.indexOf("\\")) != -1) {
         if (flag) {
            s1 = s1 + m995(s.substring(0, i), maggie, symbols);
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
         s1 = s1 + m995(s, maggie, symbols);
      } else {
         s1 = s1 + s;
      }

      return s1;
   }

   static String m1005(String s) {
      String s1 = "";

      int i;
      while ((i = s.indexOf("\\")) != -1) {
         s1 = s1 + s.substring(0, i) + "\\\\";
         s = s.substring(i + 1);
      }

      return s1 + s;
   }

   static Expression m1006(String s) throws FormulaParseException {
      return m1009(s, false, false, false);
   }

   static Expression m1007(String s, boolean flag) throws FormulaParseException {
      return m1009(s, flag, false, false);
   }

   static Expression m1008(String s, boolean flag, boolean flag1) throws FormulaParseException {
      return m1009(s, flag, flag1, false);
   }

   static Expression m1009(String s, boolean flag, boolean flag1, boolean flag2) throws FormulaParseException {
      try {
         if (s != null && !s.trim().equals("")) {
            FormulaParser.reinit(new StringReader(s + "\n"));
            Expression expression = FormulaParser.parse();
            if (expression != null) {
               expression.m1257();
            }

            String s1;
            if (!flag1 && (s1 = m1015(s)) != null) {
               throw new FormulaParseException("Parse error, column " + (s.indexOf(s1) + 1) + ".");
            } else {
               String s4;
               if (!flag2 && (s4 = m1023(s)) != null) {
                  throw new FormulaParseException("Parse error, column " + (s.indexOf(s4) + 1) + ".");
               } else {
                  C_DD c_dd = new C_DD(expression);
                  c_dd.f284 = s;
                  if (!c_dd.m490()) {
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
         int j = m1024(s3);
         if (j != -1) {
            int[] aint1 = new int[]{j};
            m996(s, maggie, symbols, aint1);
            throw new FormulaParseException("Parse error at position " + aint1[0] + ".");
         } else {
            throw formulaparseexception;
         }
      } catch (FormulaLexerError formulalexererror) {
         String s2 = formulalexererror.getMessage();
         int i = m1024(s2);
         if (i != -1) {
            int[] aint = new int[]{i};
            m996(s, maggie, symbols, aint);
            throw new FormulaParseException("Lexical error at position " + aint[0] + ".");
         } else {
            throw new FormulaParseException(s2);
         }
      }
   }

   static Integer parseInteger(String s) {
      return m1011(s, 10);
   }

   static Integer m1011(String s, int i) {
      try {
         return Integer.valueOf(s, i);
      } catch (NumberFormatException numberformatexception) {
         return null;
      }
   }

   static Long m1012(String s) {
      return m1013(s, 10);
   }

   static Long m1013(String s, int i) {
      try {
         return Long.valueOf(s, i);
      } catch (NumberFormatException numberformatexception) {
         return null;
      }
   }

   static String m1014(int i) {
      String s = "00000000" + Integer.toString(i, 16);
      return s.substring(s.length() - 8);
   }

   static String m1015(String s) {
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

   static String m1016(int i) {
      int j = f599.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return f599.substring(i, i + 1);
   }

   static String m1017(int i) {
      int j = f600.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return f600.substring(i, i + 1);
   }

   static String m1018(int i) {
      int j = f601.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return f601.substring(i, i + 1);
   }

   static String m1019(int i) {
      int j = f602.length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return f602.substring(i, i + 1);
   }

   static String m1020(int i) {
      int j = "xyzuvw".length();

      while (i < 0) {
         i += j;
      }

      while (i >= j) {
         i -= j;
      }

      return "xyzuvw".substring(i, i + 1);
   }

   static String m1021(String s) {
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

   static String[] m1022(String s) {
      int i = s == null ? 0 : s.length();
      String[] astring = new String[i];

      for (int j = 0; j < i; j++) {
         astring[j] = s.substring(j, j + 1);
      }

      return astring;
   }

   static String m1023(String s) {
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

   static int m1024(String s) {
      String s1 = ", column ";
      String s2 = ".";
      int i;
      if (s != null && (i = s.indexOf(s1)) != -1) {
         i += s1.length();
         int j;
         if ((j = s.indexOf(s2, i)) != -1) {
            try {
               return Integer.parseInt(s.substring(i, j));
            } catch (NumberFormatException numberformatexception) {
            }
         }
      }

      return -1;
   }

   static Theorem m1025(Integer integer) {
      return f534 == null ? null : f534.m2204(integer);
   }

   static Rule m1026(String s) {
      return f534 == null ? null : f534.m2205(s);
   }

   static void m1027(String s, Vector vector, C_n_F c_n_f, boolean flag) {
      s = s.trim();

      while (!s.equals("")) {
         int i = s.indexOf(".");
         String s1;
         if (i == -1) {
            s1 = s;
            s = "";
         } else {
            s1 = s.substring(0, i).trim();
            s = s.substring(i + 1).trim();
         }

         if ("~{".indexOf(s1.charAt(0)) != -1) {
            c_n_f.m1975(new C_n_F(s1));
         } else {
            Integer integer;
            if ((integer = SchematicRule.m1366(s1)) != null) {
               c_n_f.m1975(C_n_F.m1970(integer));
            } else {
               Rule rule;
               if ((rule = m1026(s1)) != null) {
                  if (flag) {
                     SchematicRule[] aschematicrule = rule.m1374();
                     int j = aschematicrule.length;

                     for (int k = 0; k < j; k++) {
                        if (!vector.contains(aschematicrule[k].f820)) {
                           vector.addElement(aschematicrule[k].f820);
                        }
                     }
                  } else if (!vector.contains(rule.f820)) {
                     vector.addElement(rule.f820);
                  }
               } else {
                  System.out.println("unknown rule: " + s1);
               }
            }
         }
      }
   }

   static int m1028(String s) {
      int i = s.indexOf("/");
      if (i >= 0) {
         Integer integer = parseInteger(s.substring(0, i));
         Integer integer1 = parseInteger(s.substring(i + 1));
         if (integer != null && integer1 != null) {
            int j = integer;
            int k = integer1;
            if (j != 0 && k != 0) {
               return ProgressDialog.m1288() * j / k;
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
      return f540 != null && j <= 1 && (font = f540[j]) != null ? font.deriveFont((float)i) : new Font("SanSerif", j, i);
   }

   static String m1031(ModuleFrame moduleframe, String s, String s1) {
      FileDialog filedialog = new FileDialog(moduleframe, s);
      Dimension dimension = new Dimension(400, 250);
      filedialog.setLocation(MessageDialog.m1321(dimension));
      if (s1 != null) {
         filedialog.setDirectory(s1);
      }

      filedialog.setVisible(true);
      String s2 = filedialog.getFile();
      String s3 = filedialog.getDirectory();
      filedialog.dispose();
      return s2 == null ? null : s3 + s2;
   }

   static String m1032(ModuleFrame moduleframe, String s, String s1) {
      FileDialog filedialog = new FileDialog(moduleframe, s, 1);
      Dimension dimension = new Dimension(400, 250);
      filedialog.setLocation(MessageDialog.m1321(dimension));
      if (s1 != null) {
         filedialog.setDirectory(s1);
      }

      filedialog.setVisible(true);
      String s2 = filedialog.getFile();
      String s3 = filedialog.getDirectory();
      filedialog.dispose();
      return s2 == null ? null : s3 + s2;
   }

   static boolean m1033(Container container, Component component) {
      if (container == null) {
         return false;
      } else {
         Object object = component;

         while (object != null && object != container) {
            object = object.getParent();
         }

         return object != null;
      }
   }

   static Container m1034(Component component, Component component1) {
      if (component1 == null) {
         return null;
      } else {
         Container container = component != null && !(component instanceof Container) ? component.getParent() : (Container)component;

         while (container != null && !m1033(container, component1)) {
            container = container.getParent();
         }

         return container;
      }
   }

   static Rectangle m1035(Component component, Component component1) {
      Container container = m1034(component, component1);
      Rectangle rectangle = m1036(component, container);
      if (component1 != null) {
         Rectangle rectangle1 = m1036(component1, container);
         rectangle.x = rectangle.x - rectangle1.x;
         rectangle.y = rectangle.y - rectangle1.y;
      }

      return rectangle;
   }

   static Rectangle m1036(Component component, Container container) {
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
               Container container2 = m1037(container1);
               Rectangle rectangle1 = container1.getBounds();
               rectangle.x = rectangle.x + rectangle1.x;
               rectangle.y = rectangle.y + rectangle1.y;
               container1 = container2;
            }

            return container1 == container ? rectangle : null;
         }
      }
   }

   static Container m1037(Component component) {
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

   static Rectangle m1038(Container container) {
      Insets insets = container.getInsets();
      Dimension dimension = container.getSize();
      return new Rectangle(insets.left, insets.top, dimension.width - insets.left - insets.right, dimension.height - insets.top - insets.bottom);
   }

   static void addCredentials(Credentials credentials) {
      if (credentials == null) {
         credentials = new Hashtable();
      }

      credentials.put(credentialsx.user.toLowerCase(), credentialsx);
   }

   static Credentials getCredentials(String s) {
      return credentials != null && s != null ? (Credentials)credentials.get(s.toLowerCase()) : null;
   }

   static Dialog m1041(Component object) {
      while (object != null) {
         if (object instanceof Dialog) {
            return (Dialog)object;
         }

         object = object.getParent();
      }

      return null;
   }

   static Dialog m1042(JComponent jcomponent) {
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

   static Window m1043(JComponent jcomponent) {
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

   static Frame m1044(Component component) {
      Container container = component != null && !(component instanceof Container) ? component.getParent() : (Container)component;

      while (container != null && !(container instanceof Frame)) {
         container = container.getParent();
      }

      return (Frame)container;
   }

   static JFrame m1045(Component component) {
      Container container = component != null && !(component instanceof Container) ? component.getParent() : (Container)component;

      while (container != null && !(container instanceof JFrame)) {
         container = container.getParent();
      }

      return (JFrame)container;
   }

   static void m1046(Window window, boolean flag) {
      window.setAlwaysOnTop(flag);
   }

   static Vector m1047(Vector vector, boolean flag) {
      if (vector == null) {
         return null;
      } else {
         Vector vector1 = new Vector();
         m1048(vector, flag, vector1);
         return vector1;
      }
   }

   static void m1048(Vector vector, boolean flag, Vector vector1) {
      int i = vector == null ? 0 : vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (object instanceof Vector) {
            m1048((Vector)object, flag, vector1);
         } else if (!flag || !vector1.contains(object)) {
            vector1.addElement(object);
         }
      }
   }

   static void m1049(Vector vector, Vector vector1, boolean flag) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (!flag || !vector1.contains(object)) {
            vector1.addElement(object);
         }
      }
   }

   static Hashtable m1050(Hashtable hashtable, Hashtable hashtable1, boolean flag) {
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

   static int m1051(Object[] aobject, Object object) {
      return m1052(aobject, object, 0);
   }

   static int m1052(Object[] aobject, Object object, int i) {
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

   static void m1053(boolean flag) {
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

   static String m1054() {
      ModuleFrame moduleframe = new ModuleFrame();
      JScrollPane jscrollpane = new JScrollPane();
      FormulaTextPane formulatextpane = new FormulaTextPane(true);
      String[] astring = new String[]{"Submit", "Cancel"};
      jscrollpane.setViewportView(formulatextpane);
      MessageDialog messagedialog = new MessageDialog(moduleframe, "Feedback", jscrollpane, astring);
      Dimension dimension = new Dimension(700, 500);
      messagedialog.setSize(dimension);
      formulatextpane.requestFocus();
      messagedialog.m1323(MessageDialog.m1321(dimension), true);
      moduleframe.dispose();
      return messagedialog.f790 == 0 ? m995(formulatextpane.getText(), symbols, maggie) : null;
   }

   static void resetOptions() {
      debug = false;
      printingEnabled = true;
      overheadColors = false;
      remote = false;
      noNetwork = false;
      f577 = false;
      f584 = false;
      f585 = false;
      f566 = 1;
      f567 = null;
      f568 = null;
      f588 = null;
      f589 = false;
      f593 = null;
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
               int[] aint = taggedrecord.m1481("+ufcbrpo");
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
                        f585 = true;
                        symbols = FormulaParser.getSyntax() == 2 ? kaplan1 : kaplan2;
                     } else if (s1.equalsIgnoreCase("overhead")) {
                        overheadColors = true;
                     } else if (s1.equalsIgnoreCase("remote")) {
                        remote = true;
                     } else if (s1.equalsIgnoreCase("nonet")) {
                        noNetwork = true;
                     } else if (s1.equalsIgnoreCase("hidden")) {
                        f577 = true;
                     } else if (s1.equalsIgnoreCase("noCoreProbs")) {
                        f584 = true;
                     } else if (s1.equalsIgnoreCase("repeatAuth")) {
                        f589 = true;
                     } else if (s1.equalsIgnoreCase("hideSensitive")) {
                        f590 = true;
                     }
                  } else if (c0 == 'u') {
                     Credentials credentialsx = new Credentials(s1);
                     if (credentialsx.password != null) {
                        credentialsx.password = Scrambler.unscramble(new String(new Base64Codec(credentialsx.password).m2000()));
                     }

                     addCredentials(credentialsx);
                  } else if (c0 == 'f') {
                     fontSize = m1028(s1);
                     ProgressDialog.m1285(fontSize);
                     UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
                  } else if (c0 == 'c') {
                     Integer integer = parseInteger(s1);
                     if (integer != null) {
                        f566 = integer;
                     }
                  } else if (c0 == 'b') {
                     f567 = s1;
                  } else if (c0 == 'r') {
                     f568 = s1;
                  } else if (c0 == 'p') {
                     f588 = parseInteger(s1);
                  } else if (c0 == 'o') {
                     f593 = s1;
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

   static void m1057() {
      String s = (String)overrides.get("FontSize");
      if (s != null) {
         fontSize = m1028(s);
         ProgressDialog.m1285(fontSize);
         UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
      }
   }

   static void m1058() {
      String s = prefs.get("font size");
      if (s != null) {
         fontSize = m1028(s);
         ProgressDialog.m1285(fontSize);
         UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
      }
   }

   static void m1059() {
      String s = workPrefs.get("monochrome");
      f603 = "true".equalsIgnoreCase(s);
      f607 = f603 ? f606 : f604;
   }

   static boolean m1060(ProblemSelector problemselector, String s) {
      return problemselector != null && (s == null ? problemselector.m405('u') : problemselector.m404(s));
   }

   static void setSyntax(int i) {
      FormulaParser.setSyntax(i);
      i = FormulaParser.getSyntax();
      switch (i) {
         case 1:
            symbols = kaplan1;
            f597 = kaplan5;
            f598 = html1;
            f599 = "PQRSTUVWXYZ";
            f600 = "FGHIJKLMNO";
            f601 = "ABCDE";
            f602 = "abcdefghijklmnopqrstuvwxyz";
            ruleDir = new File(configDir, "syntax1");
            break;
         case 2:
            symbols = kaplan2;
            f597 = kaplan6;
            f598 = html2;
            f599 = "PQRSTUVWXYZ";
            f600 = "FGHIJKLMNOABCDE";
            f601 = "abcdefgh";
            f602 = "ijklmnopqrstuvwxyz";
            ruleDir = new File(configDir, "syntax2");
      }
   }

   static ScrambledReader openDataFile(String s, boolean flag) {
      return m1063(s, flag, true);
   }

   static ScrambledReader m1063(String s, boolean flag, boolean flag1) {
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

   static ScrambledReader m1064(String s) {
      return m1066(s, false, true);
   }

   static ScrambledReader m1065(String s, boolean flag) {
      return m1066(s, flag, true);
   }

   static ScrambledReader m1066(String s, boolean flag, boolean flag1) {
      String s1 = getLink(s);
      File file1 = flag ? ServerConnection.f466 : ServerConnection.f467;
      if (s1 != null && file1 != null) {
         s1 = new File(file1, new File(s1).getName()).getPath();

         try {
            return new ScrambledReader(new FileReader(s1), flag1 ? scrambleKey : null);
         } catch (IOException ioexception) {
            return null;
         }
      } else {
         return null;
      }
   }

   static ScrambledReader m1067(String s, boolean flag, boolean flag1) {
      String s1 = getLink(s);
      if (s1 == null) {
         return null;
      } else {
         File file1 = flag ? new File(configDir, "local") : ruleDir;
         s1 = new File(file1, new File(s1).getName()).getPath();

         try {
            return new ScrambledReader(new FileReader(s1), flag1 ? scrambleKey : null);
         } catch (IOException ioexception) {
            return null;
         }
      }
   }

   static FileWriter m1068(String s, boolean flag, boolean flag1) {
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

   RandomAccessFile m1069(String s, String s1, boolean flag) {
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

   static void m1070(String s) {
      HttpDownloader.m1861(new File(s), configDir);
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
         Object object;
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

               m1073(hashtable, "PROGDIR", progDir + "");
               m1073(hashtable, "LINKDIR", file1 + "");
               m1073(hashtable, "RULEDIR", ruleDir + "");
               return hashtable;
            }

            object = null;
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

         return (Hashtable)object;
      } else {
         return null;
      }
   }

   static void m1073(Hashtable hashtable, String s, String s1) {
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
      return s == null ? null : m1075(new File(s));
   }

   static File m1075(File file1) {
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

   static String m1080() {
      Calendar calendar = Calendar.getInstance();
      String s = "" + calendar.get(1);
      s = s + m1081("0" + (calendar.get(2) + 1), 2);
      s = s + m1081("0" + calendar.get(5), 2);
      s = s + ".";
      s = s + m1081("0" + calendar.get(11), 2);
      s = s + m1081("0" + calendar.get(12), 2);
      return s + m1081("0" + calendar.get(13), 2);
   }

   static String m1081(String s, int i) {
      if (s == null) {
         return null;
      } else {
         int j = s.length();
         return j < i ? s : s.substring(j - i);
      }
   }

   static boolean m1082(String s, String s1, String s2) {
      return m1083(s, s1, s2, null);
   }

   static boolean m1083(String s, String s1, String s2, String s3) {
      boolean flag = true;
      BufferedWriter bufferedwriter = null;
      String s4 = TaggedRecord.m1493(s2);
      if (s4 == null) {
         return false;
      } else {
         String s5 = m1080();
         String s6 = Scrambler.md5Base64(s2);
         String s7 = TaggedRecord.m1508(s4, '$');
         s7 = s7 + TaggedRecord.m1508(s1, 'a');
         s7 = s7 + TaggedRecord.m1508(s5, 'd');
         s7 = s7 + TaggedRecord.m1508(s6, 'w');
         if (s3 != null) {
            s7 = s7 + TaggedRecord.m1508(s3, 'x');
         }

         try {
            bufferedwriter = new BufferedWriter(new FileWriter(new File(workDir, s).getPath(), true));
            bufferedwriter.write(Scrambler.scramble(TaggedRecord.m1509(s7)));
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

   static Hashtable m1084(String s, String s1, ProblemSelector problemselector, C_a_A c_a_a) {
      if (problemselector != null && !problemselector.m402() && c_a_a != null) {
         Vector vector = new Vector();

         while (c_a_a.hasMoreElements()) {
            String s2 = (String)c_a_a.nextElement();
            if (problemselector.m404(TaggedRecord.m1493(s2))) {
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
                  if (problemselector.m404(s3) && s1.equals(s4)) {
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
               String s5 = (String)hashtable.get(TaggedRecord.m1493(s6));
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

   static KeyListener m1085(Component object) {
      Object object1 = null;

      while (object != null && !((object1 = object.getParent()) instanceof KeyListener)) {
         object = object1;
      }

      return (KeyListener)object1;
   }

   static void m1086(Container container, KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         if (keyevent.getID() == 401 && keyevent.getKeyCode() == 10 && container instanceof JComponent) {
            JComponent jcomponent = (JComponent)container;
            JButton jbutton = jcomponent.getRootPane().getDefaultButton();
            if (jbutton != null) {
               jbutton.doClick();
               return;
            }
         }

         KeyListener keylistener = m1085(container);
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
      ProgressDialog.m1285(fontSize);
      UIManager.put("ToolTip.font", getFont(fontSize * 3 / 4));
   }

   static class C__A implements Runnable {
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

                  MessageDialog.m1312();
                  ServerConnection.f489 = null;
                  LogicProgram.mainMenu = MainMenu.m2207(null);
                  LogicProgram.m1053(true);
                  return;
               }
            } else {
               LogicProgram.exit();
            }
         }
      }
   }
}
