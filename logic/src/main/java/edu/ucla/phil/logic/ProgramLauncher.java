package edu.ucla.phil.logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ProgramLauncher {
   static File progDir = new File(System.getProperty("user.dir"));
   static ProgramLauncher.PathFileSpec pathSpec = new ProgramLauncher.PathFileSpec(null, true);
   static boolean progDirGiven = false;

   public static void launch(String[] astring) {
      int i = astring.length;

      for (int j = 0; j < i; j++) {
         if (astring[j] != null && !astring[j].equals("-debug")) {
            if (astring[j].equals("-plain")) {
               pathSpec.scrambled = false;
            } else if (astring[j].equals("-cd")) {
               if (++j >= i) {
                  break;
               }

               progDir = new File(astring[j]);
               progDirGiven = true;
            } else if (!astring[j].startsWith("-") && pathSpec.pathFile == null) {
               pathSpec.pathFile = new File(astring[j]);
            }
         }
      }

      ProgramLauncher.LaunchPaths programlauncher$launchpaths;
      if ((programlauncher$launchpaths = resolvePaths(pathSpec)) != null) {
         if (!progDirGiven) {
            new File(programlauncher$launchpaths.linkDir, "loadinfo.txt").delete();
         }

         if (programlauncher$launchpaths.workDir == null) {
            programlauncher$launchpaths.workDir = progDir;
         }

         Process process;
         if (programlauncher$launchpaths.copyDir != null) {
            process = exec(
               new String[]{
                  "java",
                  "-Dlink.dir=" + programlauncher$launchpaths.linkDir,
                  "-Dprog.dir=" + progDir,
                  "-Dcopy.dir=" + programlauncher$launchpaths.copyDir,
                  "-cp",
                  programlauncher$launchpaths.javaDir.getPath(),
                  "edu.ucla.phil.logic.LogicProgram"
               },
               programlauncher$launchpaths.workDir
            );
         } else {
            process = exec(
               new String[]{
                  "java",
                  "-Dlink.dir=" + programlauncher$launchpaths.linkDir,
                  "-Dprog.dir=" + progDir,
                  "-cp",
                  programlauncher$launchpaths.javaDir.getPath(),
                  "edu.ucla.phil.logic.LogicProgram"
               },
               programlauncher$launchpaths.workDir
            );
         }

         if (process == null) {
            System.out.println("could not execute command line");
         }
      }
   }

   static ProgramLauncher.LaunchPaths resolvePaths(ProgramLauncher.PathFileSpec programlauncher$pathfilespec) {
      File file1;
      boolean flag;
      if (programlauncher$pathfilespec != null && programlauncher$pathfilespec.pathFile != null) {
         flag = programlauncher$pathfilespec.scrambled;
         file1 = programlauncher$pathfilespec.pathFile;
         if (!file1.exists() || !file1.canRead()) {
            System.out.println("Could not open path file.");
            return null;
         }
      } else {
         flag = false;
         file1 = new File(progDir, "paths.txt");
         if (!file1.exists() || !file1.canRead()) {
            flag = true;
            file1 = new File(progDir, "maps.txt");
            if (!file1.exists() || !file1.canRead()) {
               System.out.println("Could not open path file.");
               return null;
            }
         }
      }

      ProgramLauncher.LaunchPaths programlauncher$launchpaths = readPathFile(file1, flag);
      if (programlauncher$launchpaths.linkDir == null) {
         programlauncher$launchpaths.linkDir = progDir;
      }

      if (programlauncher$launchpaths.workDir == null || programlauncher$launchpaths.javaDir == null || programlauncher$launchpaths.copyDir == null) {
         ProgramLauncher.LaunchPaths programlauncher$launchpaths1 = readLinkFile(programlauncher$launchpaths.linkDir);
         if (programlauncher$launchpaths1 != null) {
            if (programlauncher$launchpaths.workDir == null && programlauncher$launchpaths1.workDir != null) {
               programlauncher$launchpaths.workDir = programlauncher$launchpaths1.workDir;
            }

            if (programlauncher$launchpaths.javaDir == null && programlauncher$launchpaths1.javaDir != null) {
               programlauncher$launchpaths.javaDir = programlauncher$launchpaths1.javaDir;
            }

            if (programlauncher$launchpaths.copyDir == null && programlauncher$launchpaths1.copyDir != null) {
               programlauncher$launchpaths.copyDir = programlauncher$launchpaths1.copyDir;
            }

            if (programlauncher$launchpaths1.linkDir != null) {
               String s = programlauncher$launchpaths1.linkDir.getPath();
               String s1 = programlauncher$launchpaths.linkDir.getPath();
               programlauncher$launchpaths.workDir = rebase(programlauncher$launchpaths.workDir, s, s1);
               programlauncher$launchpaths.javaDir = rebase(programlauncher$launchpaths.javaDir, s, s1);
               programlauncher$launchpaths.copyDir = rebase(programlauncher$launchpaths.copyDir, s, s1);
            }
         }
      }

      if (programlauncher$launchpaths.workDir != null && programlauncher$launchpaths.javaDir != null) {
         return programlauncher$launchpaths;
      } else {
         if (programlauncher$launchpaths.workDir == null) {
            System.out.println("Could not find work directory.");
         }

         if (programlauncher$launchpaths.javaDir == null) {
            System.out.println("Could not find java directory.");
         }

         return null;
      }
   }

   static ProgramLauncher.LaunchPaths readLinkFile(File file1) {
      boolean flag = false;
      File file2 = new File(file1, "links.txt");
      if (!file2.exists() || !file2.canRead()) {
         file2 = new File(file1, "ghost.txt");
         flag = true;
         if (!file2.exists() || !file2.canRead()) {
            System.out.println("Could not open link file.");
            return null;
         }
      }

      return readPathFile(file2, flag);
   }

   static ProgramLauncher.LaunchPaths readPathFile(File file1, boolean flag) {
      String s1 = null;
      String s2 = flag ? "the Logic Program is protected by international copyright law" : null;
      ProgramLauncher.LaunchPaths programlauncher$launchpaths = new ProgramLauncher.LaunchPaths();

      try {
         ScrambledReader scrambledreader = new ScrambledReader(new FileReader(file1), s2);

         String s;
         while ((s = scrambledreader.readLine()) != null) {
            int i;
            if (!s.startsWith("#") && (i = s.indexOf(":")) != -1) {
               String s3 = s.substring(0, i);
               if (s3.equalsIgnoreCase("linkDir") && programlauncher$launchpaths.linkDir == null) {
                  programlauncher$launchpaths.linkDir = toDirectory(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("workDir") && programlauncher$launchpaths.workDir == null) {
                  programlauncher$launchpaths.workDir = toDirectory(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("javaDir") && programlauncher$launchpaths.javaDir == null) {
                  programlauncher$launchpaths.javaDir = toDirectory(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("copyDir") && programlauncher$launchpaths.copyDir == null) {
                  programlauncher$launchpaths.copyDir = toDirectory(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("progDir") && s1 == null) {
                  s1 = s.substring(i + 1);
               }
            }
         }
      } catch (IOException ioexception) {
         return null;
      }

      if (s1 != null) {
         programlauncher$launchpaths.linkDir = rebase(programlauncher$launchpaths.linkDir, s1, progDir.getPath());
         programlauncher$launchpaths.workDir = rebase(programlauncher$launchpaths.workDir, s1, progDir.getPath());
         programlauncher$launchpaths.javaDir = rebase(programlauncher$launchpaths.javaDir, s1, progDir.getPath());
         programlauncher$launchpaths.copyDir = rebase(programlauncher$launchpaths.copyDir, s1, progDir.getPath());
      }

      return programlauncher$launchpaths;
   }

   static File toDirectory(String s) {
      return s.trim().equals("") ? progDir : new File(s);
   }

   static File rebase(File file1, String s, String s1) {
      if (file1 == null) {
         return null;
      } else {
         String s2 = file1.getPath();
         if (!s2.startsWith(s + File.separator) && !s2.equals(s)) {
            return file1;
         } else {
            if (s1.endsWith(File.separator)) {
               s1 = s1.substring(0, s1.length() - 1);
            }

            String s3 = s1 + s2.substring(s.length());
            if (s3.equals("")) {
               s3 = new File(File.separator).getPath();
            }

            return new File(s3);
         }
      }
   }

   static Process exec(String[] astring) {
      try {
         return Runtime.getRuntime().exec(astring);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static Process exec(String s) {
      try {
         return Runtime.getRuntime().exec(s);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static Process exec(String[] astring, File file1) {
      try {
         return Runtime.getRuntime().exec(astring, null, file1);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static Process exec(String s, File file1) {
      try {
         return Runtime.getRuntime().exec(s, null, file1);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static int waitFor(Process process) {
      if (process == null) {
         return -1;
      } else {
         try {
            process.waitFor();
            return process.exitValue();
         } catch (InterruptedException interruptedexception) {
            return -1;
         } catch (IllegalThreadStateException illegalthreadstateexception) {
            return -1;
         }
      }
   }

   static boolean changeDirectory(File file1) {
      return waitFor(exec(new String[]{"cd", file1.getPath()})) == 0;
   }

   static class LaunchPaths {
      File linkDir;
      File workDir;
      File javaDir;
      File copyDir;
   }

   static class PathFileSpec {
      File pathFile;
      boolean scrambled;

      PathFileSpec(File file1, boolean flag) {
         this.pathFile = file1;
         this.scrambled = flag;
      }
   }
}
