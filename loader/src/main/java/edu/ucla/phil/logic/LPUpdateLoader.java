package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import javax.swing.SwingUtilities;

public class LPUpdateLoader {
   public static String version = "20141219";
   static Hashtable table = null;

   private LPUpdateLoader() {
   }

   public static void main(final String[] args) {
      boolean upgradeFromOld = false;
      if (!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               LPUpdateLoader.main(args);
            }
         });
      } else {
         loadInfo();
         if (table == null) {
            notify("Update Failure", "The loader was not able to access\nthe information it needed.");
         } else {
            AuthURL source = makeURL(makeZip((String)table.get("source")));
            if (source == null) {
               notify("Update Failure", "Information supplied to the loader\nwas improper.");
            } else {
               File rootDir = getDirFromTable("rootDir", true);
               if (rootDir != null) {
                  File progDir = getDirFromTable("progDir", true);
                  if (rootDir != null) {
                     File linkDir = getDirFromTable("linkDir", true);
                     if (rootDir != null) {
                        File configDir = getDirFromTable("configDir", false);
                        if (configDir == null) {
                           upgradeFromOld = true;
                           configDir = getDirFromTable("userDir", true);
                        }

                        if (configDir != null) {
                           boolean needBackup = false;
                           if ("true".equals((String)table.get("needBackup"))) {
                              needBackup = true;
                           }

                           String tempStr;
                           String arch = (tempStr = (String)table.get("arch")) == null ? "noarch" : tempStr;
                           String type = (tempStr = (String)table.get("type")) == null ? "core" : tempStr;
                           String soloString = (String)table.get("soloPort");
                           if (soloString != null && !arch.equalsIgnoreCase("macos")) {
                              int soloPort = 0;

                              try {
                                 soloPort = Integer.parseInt(soloString);
                              } catch (NumberFormatException var16) {
                                 notify("Update Failure", "Information supplied to the loader\nwas improper.");
                                 System.exit(0);
                              }

                              Notifier informant = inform("Please Wait", "Waiting for Logic 2010 to shut down.");
                              TCPSolo solo = new TCPSolo(soloPort, "cogito");
                              if (solo.running(1000)) {
                                 informant.dispose();
                                 notify("Update Failure", "Logic 2010 would not shut down.");
                                 System.exit(0);
                              }

                              informant.dispose();
                           }

                           if (upgradeFromOld && "Mac OS X".equals(System.getProperty("os.name"))) {
                              reinstallNotice(needBackup, arch);
                              System.exit(0);
                           }

                           boolean local = type.equals("local");

                           boolean updated;
                           do {
                              updated = getUpdate(source, local ? configDir : rootDir);
                           } while (!updated && retry());

                           if (!updated) {
                              System.exit(0);
                           }

                           boolean started;
                           do {
                              started = startUpdate(progDir, linkDir, rootDir, configDir, arch);
                           } while (!started && retry());

                           System.exit(0);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   static void loadInfo() {
      table = new Hashtable();
      File info = new File(System.getProperty("load.info"));
      BufferedReader reader = null;

      try {
         reader = new BufferedReader(new FileReader(info));

         String line;
         while ((line = reader.readLine()) != null) {
            int colon = line.indexOf(58);
            if (colon != -1) {
               table.put(line.substring(0, colon), line.substring(colon + 1));
            }
         }
      } catch (IOException var5) {
         table = null;
      }

      if (reader != null) {
         try {
            reader.close();
         } catch (IOException var4) {
         }
      }
   }

   static AuthURL makeURL(String spec) {
      if (spec == null) {
         return null;
      } else {
         try {
            return new AuthURL(spec);
         } catch (MalformedURLException var2) {
            return null;
         }
      }
   }

   static boolean getUpdate(AuthURL source, File destDir) {
      Valve valve = new Valve("Please Wait", "Downloading and installing the update.");
      NetZipLoader loader = new NetZipLoader();
      if (!loader.load(source, destDir, valve)) {
         notify("Update Failure", "Could not load the update.");
         return false;
      } else {
         return true;
      }
   }

   static boolean startUpdate(File progDir, File linkDir, File rootDir, File configDir, String arch) {
      String jarName = "logic.jar";
      String javaCmd = arch.equals("windows") ? "javaw" : "java";
      String[] commands = new String[]{
         javaCmd,
         "-Dconfig.dir=" + configDir,
         "-Dlink.dir=" + linkDir,
         "-Dprog.dir=" + progDir,
         "-Droot.dir=" + rootDir,
         "-jar",
         new File(progDir, jarName).getPath()
      };
      if ("Mac OS X".equals(System.getProperty("os.name"))) {
         commands = new String[]{"open", rootDir + ""};
      }

      try {
         Runtime.getRuntime().exec(commands, null);
         return true;
      } catch (IOException var9) {
         notify("Update Failure", "Could not start the program.");
         return false;
      }
   }

   static boolean retry() {
      Notifier notifier = new Notifier("Network Error", "Retry the network transaction?", new String[]{"Retry", "Abort"}, 0);
      notifier.show(20, 10);
      return notifier.buttonIndex == 0;
   }

   static String makeZip(String name) {
      if (name != null && !name.toLowerCase().endsWith(".zip")) {
         String[] extensions = new String[]{".exe"};
         int count = extensions.length;

         for (int i = 0; i < count; i++) {
            if (name.toLowerCase().endsWith(extensions[i])) {
               return name.substring(0, name.length() - extensions[i].length()) + ".zip";
            }
         }

         return name + ".zip";
      } else {
         return name;
      }
   }

   static File getDirFromTable(String key, boolean notifyError) {
      String dirStr = (String)table.get(key);
      if (dirStr == null) {
         if (notifyError) {
            notify("Update Failure", "The loader was not able to access\nthe information it needed ('" + key + "').");
         }

         return null;
      } else {
         File abstractFile = new File(dirStr);
         if (abstractFile == null) {
            return null;
         } else {
            try {
               return abstractFile.getCanonicalFile();
            } catch (IOException var5) {
               return null;
            }
         }
      }
   }

   static void reinstallNotice(boolean needBackup, String arch) {
      String downloadPage = "https://logiclx.humnet.ucla.edu/Logic/Download";
      String message = "Your version of Logic 2010 cannot be automatically\nupgraded to the new version.  You will need to\ndownload and install a fresh copy from our\ndownload page:\n\n";
      message = message + downloadPage;
      if (needBackup) {
         message = message
            + "\n\nBefore installing, back up your work and then delete\nyour current version(s) of Logic 2010 from the dock\nand/or elsewhere. After installing, you will be able \nto restore your backed up work from our server.";
      } else {
         message = message
            + "\n\nBefore installing, delete your current version(s) of\nLogic 2010 from the dock and/or elsewhere. After\ninstalling, you will be able to restored your backed\nup work from our server.";
      }

      Notifier notice = new Notifier("Manual upgrade", message, new String[]{"Open Download Page", "Exit"}, 1);
      notice.show(20, 10);
      if (notice.buttonIndex == 0) {
         LPDesktop.openURL(downloadPage + "?arch=" + arch);
         reinstallNotice(needBackup, arch);
      }
   }

   static Notifier inform(String title, String message) {
      Notifier notifier = new Notifier(title, message, false);
      notifier.show(20, 10);
      return notifier;
   }

   static void notify(String title, String message) {
      Notifier notifier = new Notifier(title, message, true);
      notifier.show(20, 10);
      notifier.dispose();
   }
}
