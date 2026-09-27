package edu.ucla.phil.logic;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.net.URI;

public class LPDesktop {
   static void openURL(String url) {
      if (Desktop.isDesktopSupported()) {
         Desktop d = Desktop.getDesktop();
         if (d.isSupported(Action.BROWSE)) {
            try {
               d.browse(new URI(url));
            } catch (Exception var3) {
               var3.printStackTrace(System.out);
            }
         }
      }
   }

   static void openFile(String file) {
      openFile(null, file);
   }

   static void openFile(File dirRoot, String file) {
      if (Desktop.isDesktopSupported()) {
         Desktop d = Desktop.getDesktop();
         if (d.isSupported(Action.OPEN)) {
            try {
               File f = new File(file);
               if (!f.exists() && dirRoot != null) {
                  f = new File(dirRoot, file);
               }

               d.open(f);
            } catch (Exception var4) {
               var4.printStackTrace(System.out);
            }
         }
      }
   }
}
