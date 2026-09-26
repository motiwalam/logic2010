package edu.ucla.phil.logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ProgramLauncher {
   static File f800 = new File(System.getProperty("user.dir"));
   static ProgramLauncher.C__A f801 = new ProgramLauncher.C__A(null, true);
   static boolean f802 = false;

   public static void m1339(String[] astring) {
      int i = astring.length;

      for (int j = 0; j < i; j++) {
         if (astring[j] != null && !astring[j].equals("-debug")) {
            if (astring[j].equals("-plain")) {
               f801.f804 = false;
            } else if (astring[j].equals("-cd")) {
               if (++j >= i) {
                  break;
               }

               f800 = new File(astring[j]);
               f802 = true;
            } else if (!astring[j].startsWith("-") && f801.f803 == null) {
               f801.f803 = new File(astring[j]);
            }
         }
      }

      ProgramLauncher.C__B programlauncher$c__b;
      if ((programlauncher$c__b = m1340(f801)) != null) {
         if (!f802) {
            new File(programlauncher$c__b.f805, "loadinfo.txt").delete();
         }

         if (programlauncher$c__b.f806 == null) {
            programlauncher$c__b.f806 = f800;
         }

         Process process;
         if (programlauncher$c__b.f808 != null) {
            process = m1347(
               new String[]{
                  "java",
                  "-Dlink.dir=" + programlauncher$c__b.f805,
                  "-Dprog.dir=" + f800,
                  "-Dcopy.dir=" + programlauncher$c__b.f808,
                  "-cp",
                  programlauncher$c__b.f807.getPath(),
                  "edu.ucla.phil.logic.LogicProgram"
               },
               programlauncher$c__b.f806
            );
         } else {
            process = m1347(
               new String[]{
                  "java",
                  "-Dlink.dir=" + programlauncher$c__b.f805,
                  "-Dprog.dir=" + f800,
                  "-cp",
                  programlauncher$c__b.f807.getPath(),
                  "edu.ucla.phil.logic.LogicProgram"
               },
               programlauncher$c__b.f806
            );
         }

         if (process == null) {
            System.out.println("could not execute command line");
         }
      }
   }

   static ProgramLauncher.C__B m1340(ProgramLauncher.C__A programlauncher$c__a) {
      File file1;
      boolean flag;
      if (programlauncher$c__a != null && programlauncher$c__a.f803 != null) {
         flag = programlauncher$c__a.f804;
         file1 = programlauncher$c__a.f803;
         if (!file1.exists() || !file1.canRead()) {
            System.out.println("Could not open path file.");
            return null;
         }
      } else {
         flag = false;
         file1 = new File(f800, "paths.txt");
         if (!file1.exists() || !file1.canRead()) {
            flag = true;
            file1 = new File(f800, "maps.txt");
            if (!file1.exists() || !file1.canRead()) {
               System.out.println("Could not open path file.");
               return null;
            }
         }
      }

      ProgramLauncher.C__B programlauncher$c__b = m1342(file1, flag);
      if (programlauncher$c__b.f805 == null) {
         programlauncher$c__b.f805 = f800;
      }

      if (programlauncher$c__b.f806 == null || programlauncher$c__b.f807 == null || programlauncher$c__b.f808 == null) {
         ProgramLauncher.C__B programlauncher$c__b1 = m1341(programlauncher$c__b.f805);
         if (programlauncher$c__b1 != null) {
            if (programlauncher$c__b.f806 == null && programlauncher$c__b1.f806 != null) {
               programlauncher$c__b.f806 = programlauncher$c__b1.f806;
            }

            if (programlauncher$c__b.f807 == null && programlauncher$c__b1.f807 != null) {
               programlauncher$c__b.f807 = programlauncher$c__b1.f807;
            }

            if (programlauncher$c__b.f808 == null && programlauncher$c__b1.f808 != null) {
               programlauncher$c__b.f808 = programlauncher$c__b1.f808;
            }

            if (programlauncher$c__b1.f805 != null) {
               String s = programlauncher$c__b1.f805.getPath();
               String s1 = programlauncher$c__b.f805.getPath();
               programlauncher$c__b.f806 = m1344(programlauncher$c__b.f806, s, s1);
               programlauncher$c__b.f807 = m1344(programlauncher$c__b.f807, s, s1);
               programlauncher$c__b.f808 = m1344(programlauncher$c__b.f808, s, s1);
            }
         }
      }

      if (programlauncher$c__b.f806 != null && programlauncher$c__b.f807 != null) {
         return programlauncher$c__b;
      } else {
         if (programlauncher$c__b.f806 == null) {
            System.out.println("Could not find work directory.");
         }

         if (programlauncher$c__b.f807 == null) {
            System.out.println("Could not find java directory.");
         }

         return null;
      }
   }

   static ProgramLauncher.C__B m1341(File file1) {
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

      return m1342(file2, flag);
   }

   static ProgramLauncher.C__B m1342(File file1, boolean flag) {
      String s1 = null;
      String s2 = flag ? "the Logic Program is protected by international copyright law" : null;
      ProgramLauncher.C__B programlauncher$c__b = new ProgramLauncher.C__B();

      try {
         ScrambledReader scrambledreader = new ScrambledReader(new FileReader(file1), s2);

         String s;
         while ((s = scrambledreader.readLine()) != null) {
            int i;
            if (!s.startsWith("#") && (i = s.indexOf(":")) != -1) {
               String s3 = s.substring(0, i);
               if (s3.equalsIgnoreCase("linkDir") && programlauncher$c__b.f805 == null) {
                  programlauncher$c__b.f805 = m1343(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("workDir") && programlauncher$c__b.f806 == null) {
                  programlauncher$c__b.f806 = m1343(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("javaDir") && programlauncher$c__b.f807 == null) {
                  programlauncher$c__b.f807 = m1343(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("copyDir") && programlauncher$c__b.f808 == null) {
                  programlauncher$c__b.f808 = m1343(s.substring(i + 1));
               } else if (s3.equalsIgnoreCase("progDir") && s1 == null) {
                  s1 = s.substring(i + 1);
               }
            }
         }
      } catch (IOException ioexception) {
         return null;
      }

      if (s1 != null) {
         programlauncher$c__b.f805 = m1344(programlauncher$c__b.f805, s1, f800.getPath());
         programlauncher$c__b.f806 = m1344(programlauncher$c__b.f806, s1, f800.getPath());
         programlauncher$c__b.f807 = m1344(programlauncher$c__b.f807, s1, f800.getPath());
         programlauncher$c__b.f808 = m1344(programlauncher$c__b.f808, s1, f800.getPath());
      }

      return programlauncher$c__b;
   }

   static File m1343(String s) {
      return s.trim().equals("") ? f800 : new File(s);
   }

   static File m1344(File file1, String s, String s1) {
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

            s2 = s1 + s2.substring(s.length());
            if (s2.equals("")) {
               s2 = new File(File.separator).getPath();
            }

            return new File(s2);
         }
      }
   }

   static Process m1345(String[] astring) {
      try {
         return Runtime.getRuntime().exec(astring);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static Process m1346(String s) {
      try {
         return Runtime.getRuntime().exec(s);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static Process m1347(String[] astring, File file1) {
      try {
         return Runtime.getRuntime().exec(astring, null, file1);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static Process m1348(String s, File file1) {
      try {
         return Runtime.getRuntime().exec(s, null, file1);
      } catch (IOException ioexception) {
         return null;
      }
   }

   static int m1349(Process process) {
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

   static boolean m1350(File file1) {
      return m1349(m1345(new String[]{"cd", file1.getPath()})) == 0;
   }

   static class C__A {
      File f803;
      boolean f804;

      C__A(File file1, boolean flag) {
         this.f803 = file1;
         this.f804 = flag;
      }
   }

   static class C__B {
      File f805;
      File f806;
      File f807;
      File f808;
   }
}
