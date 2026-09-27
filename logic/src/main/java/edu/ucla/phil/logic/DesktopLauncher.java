package edu.ucla.phil.logic;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.net.URI;

public class DesktopLauncher {
   static void browse(String s) {
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

   static void open(String s) {
      open(null, s);
   }

   static void open(File file1, String s) {
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
