package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class ServerUrl {
   private URL f1328 = null;

   public ServerUrl(String s) throws MalformedURLException {
      if (s == null) {
         throw new MalformedURLException("null URL specification");
      } else {
         String[] astring = this.m2010(s, true);
         this.f1328 = new URL(astring[0] + astring[3]);
      }
   }

   String[] m2010(String s, boolean flag) {
      String[] astring = new String[]{"", null, null, ""};
      int i = s.indexOf("://");
      if (i != -1) {
         astring[0] = s.substring(0, i + 3);
         s = s.substring(i + 3);
      }

      if (flag) {
         i = s.indexOf(47);
         if (i != -1) {
            astring[3] = s.substring(i);
            s = s.substring(0, i);
         }

         i = s.indexOf(64);
         if (i != -1) {
            astring[1] = s.substring(0, i);
            s = s.substring(i + 1);
            i = astring[1].indexOf(58);
            if (i != -1) {
               astring[2] = astring[1].substring(i + 1);
               astring[1] = astring[1].substring(i);
            }
         }
      }

      astring[3] = s + astring[3];
      return astring;
   }

   public static ServerUrl m2011(String s) {
      try {
         return new ServerUrl(s);
      } catch (MalformedURLException malformedurlexception) {
         return null;
      }
   }

   public String m2012() {
      return this.f1328.getFile();
   }

   public String m2013() {
      return this.f1328.getHost();
   }

   public int m2014() {
      return this.f1328.getPort();
   }

   public String m2015() {
      return this.f1328.getProtocol();
   }

   public URLConnection m2016() throws IOException {
      return this.f1328.openConnection();
   }

   public InputStream m2017() throws IOException {
      return this.f1328.openStream();
   }

   public URL m2018() {
      return this.f1328;
   }

   @Override
   public String toString() {
      return this.f1328.toString();
   }
}
