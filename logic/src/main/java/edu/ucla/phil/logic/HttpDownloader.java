package edu.ucla.phil.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HttpDownloader {
   public boolean m1855(ServerUrl serverurl, File file1, NetworkTask networktask, boolean flag) {
      File file2 = flag ? file1 : new File(file1, m1860(serverurl.m2012()));
      return this.m1856(serverurl, file2, networktask);
   }

   public boolean m1856(ServerUrl serverurl, File file1, NetworkTask networktask) {
      if (networktask == null) {
         return this.m1857(serverurl, file1, networktask);
      } else {
         networktask.m2051(new C_s_E(serverurl, file1, this, networktask));
         return (Boolean)networktask.m2052();
      }
   }

   public boolean m1857(ServerUrl serverurl, File file1, NetworkTask networktask) {
      return "http".equalsIgnoreCase(serverurl.m2015()) ? this.m1858(serverurl, file1, networktask) : false;
   }

   public boolean m1858(ServerUrl serverurl, File file1, NetworkTask networktask) {
      boolean flag = false;

      try {
         InputStream inputstream = serverurl.m2017();
         if (networktask != null) {
            networktask.m2057(inputstream);
         }

         flag = true;
         flag = m1859(inputstream, file1);
         if (networktask != null) {
            networktask.m2057(null);
         }
      } catch (IOException ioexception) {
      }

      return flag;
   }

   static boolean m1859(InputStream inputstream, File file1) throws IOException {
      ZipInputStream zipinputstream = null;
      FileOutputStream fileoutputstream = null;
      byte[] abyte = new byte[16384];
      boolean flag = false;

      try {
         if (file1.isDirectory()) {
            zipinputstream = new ZipInputStream(inputstream);

            while (true) {
               ZipEntry zipentry = zipinputstream.getNextEntry();
               if (zipentry == null) {
                  flag = true;
                  zipinputstream.close();
                  zipinputstream = null;
                  inputstream = null;
                  break;
               }

               String s = zipentry.getName();
               if (zipentry.isDirectory()) {
                  File file2 = new File(file1, s);
                  if (!file2.exists()) {
                     file2.mkdirs();
                  }
               } else {
                  fileoutputstream = new FileOutputStream(new File(file1, s));

                  int i;
                  while ((i = zipinputstream.read(abyte)) != -1) {
                     fileoutputstream.write(abyte, 0, i);
                  }

                  fileoutputstream.close();
                  fileoutputstream = null;
               }
            }
         } else {
            fileoutputstream = new FileOutputStream(file1);

            int j;
            while ((j = inputstream.read(abyte)) != -1) {
               fileoutputstream.write(abyte, 0, j);
            }

            fileoutputstream.close();
            fileoutputstream = null;
            flag = true;
            inputstream.close();
            inputstream = null;
         }
      } catch (IOException ioexception3) {
         if (fileoutputstream != null) {
            try {
               fileoutputstream.close();
            } catch (IOException ioexception2) {
            }
         }

         if (zipinputstream != null) {
            try {
               zipinputstream.close();
               inputstream = null;
            } catch (IOException ioexception1) {
            }
         }

         if (inputstream != null) {
            try {
               inputstream.close();
               inputstream = null;
            } catch (IOException ioexception) {
            }
         }

         if (inputstream != null) {
            throw new IOException("could not close input stream");
         }
      }

      return flag;
   }

   public static String m1860(String s) {
      return s.substring(s.lastIndexOf("/") + 1);
   }

   public static boolean m1861(File file1, File file2) {
      FileOutputStream fileoutputstream = null;
      ZipInputStream zipinputstream = null;
      boolean flag = true;

      try {
         zipinputstream = new ZipInputStream(new FileInputStream(file1));
         byte[] abyte = new byte[16384];

         while (true) {
            ZipEntry zipentry = zipinputstream.getNextEntry();
            if (zipentry == null) {
               break;
            }

            String s = zipentry.getName();
            if (zipentry.isDirectory()) {
               File file3 = new File(file2, s);
               if (!file3.exists()) {
                  file3.mkdirs();
               }
            } else {
               fileoutputstream = new FileOutputStream(new File(file2, s));

               int i;
               while ((i = zipinputstream.read(abyte)) != -1) {
                  fileoutputstream.write(abyte, 0, i);
               }

               fileoutputstream.close();
               fileoutputstream = null;
            }
         }
      } catch (IOException ioexception2) {
         flag = false;
      }

      if (fileoutputstream != null) {
         try {
            fileoutputstream.close();
         } catch (IOException ioexception1) {
         }
      }

      if (zipinputstream != null) {
         try {
            zipinputstream.close();
         } catch (IOException ioexception) {
         }
      }

      return flag;
   }
}
