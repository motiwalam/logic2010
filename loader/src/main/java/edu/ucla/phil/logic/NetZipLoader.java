package edu.ucla.phil.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class NetZipLoader {
   public boolean load(AuthURL source, File destDir, Valve valve, boolean unzip) {
      File dest = unzip ? destDir : new File(destDir, leafName(source.getFile()));
      return this.load(source, dest, valve);
   }

   public boolean load(AuthURL source, File dest, Valve valve) {
      if (valve == null) {
         return this._load(source, dest, valve);
      } else {
         valve.start(new NetZipLoadThread(source, dest, this, valve));
         return (Boolean)valve.getResult();
      }
   }

   public boolean _load(AuthURL source, File dest, Valve valve) {
      return "http".equalsIgnoreCase(source.getProtocol()) ? this.loadHTTP(source, dest, valve) : false;
   }

   public boolean loadHTTP(AuthURL source, File dest, Valve valve) {
      boolean result = false;

      try {
         InputStream httpStream = source.openStream();
         if (valve != null) {
            valve.setInput(httpStream);
         }

         result = true;
         result = receiveData(httpStream, dest);
         if (valve != null) {
            valve.setInput(null);
         }
      } catch (IOException var7) {
      }

      return result;
   }

   static boolean receiveData(InputStream dataStream, File dest) throws IOException {
      ZipInputStream zipStream = null;
      FileOutputStream fileStream = null;
      byte[] buffer = new byte[16384];
      boolean result = false;

      try {
         if (dest.isDirectory()) {
            zipStream = new ZipInputStream(dataStream);

            while (true) {
               ZipEntry entry = zipStream.getNextEntry();
               if (entry == null) {
                  result = true;
                  zipStream.close();
                  zipStream = null;
                  dataStream = null;
                  break;
               }

               String name = entry.getName();
               if (entry.isDirectory()) {
                  File newDir = new File(dest, name);
                  if (!newDir.exists()) {
                     newDir.mkdirs();
                  }
               } else {
                  fileStream = new FileOutputStream(new File(dest, name));

                  int size;
                  while ((size = zipStream.read(buffer)) != -1) {
                     fileStream.write(buffer, 0, size);
                  }

                  fileStream.close();
                  fileStream = null;
               }
            }
         } else {
            fileStream = new FileOutputStream(dest);

            int size;
            while ((size = dataStream.read(buffer)) != -1) {
               fileStream.write(buffer, 0, size);
            }

            fileStream.close();
            fileStream = null;
            result = true;
            dataStream.close();
            dataStream = null;
         }
      } catch (IOException var13) {
         if (fileStream != null) {
            try {
               fileStream.close();
            } catch (IOException var12) {
            }
         }

         if (zipStream != null) {
            try {
               zipStream.close();
               dataStream = null;
            } catch (IOException var11) {
            }
         }

         if (dataStream != null) {
            try {
               dataStream.close();
               dataStream = null;
            } catch (IOException var10) {
            }
         }

         if (dataStream != null) {
            throw new IOException("could not close input stream");
         }
      }

      return result;
   }

   public static String leafName(String name) {
      return name.substring(name.lastIndexOf("/") + 1);
   }

   public static boolean unzipArchive(File archive, File destDir) {
      FileOutputStream fileStream = null;
      ZipInputStream zipStream = null;
      boolean result = true;

      try {
         zipStream = new ZipInputStream(new FileInputStream(archive));
         byte[] buffer = new byte[16384];

         while (true) {
            ZipEntry entry = zipStream.getNextEntry();
            if (entry == null) {
               break;
            }

            String name = entry.getName();
            if (entry.isDirectory()) {
               File newDir = new File(destDir, name);
               if (!newDir.exists()) {
                  newDir.mkdirs();
               }
            } else {
               fileStream = new FileOutputStream(new File(destDir, name));

               int size;
               while ((size = zipStream.read(buffer)) != -1) {
                  fileStream.write(buffer, 0, size);
               }

               fileStream.close();
               fileStream = null;
            }
         }
      } catch (IOException var11) {
         result = false;
      }

      if (fileStream != null) {
         try {
            fileStream.close();
         } catch (IOException var10) {
         }
      }

      if (zipStream != null) {
         try {
            zipStream.close();
         } catch (IOException var9) {
         }
      }

      return result;
   }
}
