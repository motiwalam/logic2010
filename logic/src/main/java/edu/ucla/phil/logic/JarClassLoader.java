package edu.ucla.phil.logic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class JarClassLoader extends ClassLoader {
   ZipFile zipFile;
   Hashtable loadedClasses;

   public JarClassLoader(ZipFile zipfile) {
      this.zipFile = zipfile;
      this.loadedClasses = new Hashtable();
   }

   public JarClassLoader(File file1) {
      this(openZip(file1));
   }

   static ZipFile openZip(File file1) {
      try {
         return new ZipFile(file1);
      } catch (IOException ioexception) {
         return null;
      }
   }

   @Override
   public Class loadClass(String s, boolean flag) throws ClassNotFoundException {
      try {
         return this.findSystemClass(s);
      } catch (ClassNotFoundException classnotfoundexception) {
         Class oclass = (Class)this.loadedClasses.get(s);
         if (oclass != null) {
            return oclass;
         } else {
            ZipEntry zipentry = this.zipFile.getEntry(s.replace('.', '/') + ".class");
            if (zipentry == null) {
               throw new ClassNotFoundException("Could not find class " + s);
            } else {
               long i = 0L;
               long j = zipentry.getSize();
               byte[] abyte = new byte[(int)j];

               try {
                  InputStream inputstream = this.zipFile.getInputStream(zipentry);

                  while (i < j) {
                     long k = inputstream.read(abyte, (int)i, (int)(j - i));
                     if (k == -1L) {
                        break;
                     }

                     i += k;
                  }

                  inputstream.close();
                  this.zipFile.close();
               } catch (IOException ioexception) {
                  return null;
               }

               oclass = this.defineClass(s, abyte, 0, (int)i);
               this.loadedClasses.put(s, oclass);
               if (flag) {
                  this.resolveClass(oclass);
               }

               return oclass;
            }
         }
      }
   }
}
