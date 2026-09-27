package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class ServerUrl {
   private URL url = null;

   public ServerUrl(String s) throws MalformedURLException {
      if (s == null) {
         throw new MalformedURLException("null URL specification");
      } else {
         String[] astring = this.splitUrl(s, true);
         this.url = new URL(astring[0] + astring[3]);
      }
   }

   String[] splitUrl(String s, boolean flag) {
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

   public static ServerUrl create(String s) {
      try {
         return new ServerUrl(s);
      } catch (MalformedURLException malformedurlexception) {
         return null;
      }
   }

   public String getFile() {
      return this.url.getFile();
   }

   public String getHost() {
      return this.url.getHost();
   }

   public int getPort() {
      return this.url.getPort();
   }

   public String getProtocol() {
      return this.url.getProtocol();
   }

   public URLConnection openConnection() throws IOException {
      return this.url.openConnection();
   }

   public InputStream openStream() throws IOException {
      return this.url.openStream();
   }

   public URL getUrl() {
      return this.url;
   }

   @Override
   public String toString() {
      return this.url.toString();
   }
}
