package edu.ucla.phil.logic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class C_RA extends ClassLoader {
   ZipFile f714;
   Hashtable f715;

   public C_RA(ZipFile zipfile) {
      this.f714 = zipfile;
      this.f715 = new Hashtable();
   }

   public C_RA(File file1) {
      this(m1200(file1));
   }

   static ZipFile m1200(File file1) {
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
         Class oclass = (Class)this.f715.get(s);
         if (oclass != null) {
            return oclass;
         } else {
            ZipEntry zipentry = this.f714.getEntry(s.replace('.', '/') + ".class");
            if (zipentry == null) {
               throw new ClassNotFoundException("Could not find class " + s);
            } else {
               long i = 0L;
               long j = zipentry.getSize();
               byte[] abyte = new byte[(int)j];

               try {
                  InputStream inputstream = this.f714.getInputStream(zipentry);

                  while (i < j) {
                     long k = inputstream.read(abyte, (int)i, (int)(j - i));
                     if (k == -1L) {
                        break;
                     }

                     i += k;
                  }

                  inputstream.close();
                  this.f714.close();
               } catch (IOException ioexception) {
                  return null;
               }

               oclass = this.defineClass(s, abyte, 0, (int)i);
               this.f715.put(s, oclass);
               if (flag) {
                  this.resolveClass(oclass);
               }

               return oclass;
            }
         }
      }
   }
}
