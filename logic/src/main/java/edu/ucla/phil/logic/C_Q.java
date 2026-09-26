package edu.ucla.phil.logic;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.net.URI;

public class C_Q {
   static void m1184(String s) {
      if (Desktop.isDesktopSupported()) {
         Desktop desktop = Desktop.getDesktop();
         if (desktop.isSupported(Action.BROWSE)) {
            try {
               desktop.browse(new URI(s));
            } catch (Exception exception) {
               exception.printStackTrace(System.out);
            }
         }
      }
   }

   static void m1185(String s) {
      m1186(null, s);
   }

   static void m1186(File file1, String s) {
      if (Desktop.isDesktopSupported()) {
         Desktop desktop = Desktop.getDesktop();
         if (desktop.isSupported(Action.OPEN)) {
            try {
               File file2 = new File(s);
               if (!file2.exists() && file1 != null) {
                  file2 = new File(file1, s);
               }

               desktop.open(file2);
            } catch (Exception exception) {
               exception.printStackTrace(System.out);
            }
         }
      }
   }
}
