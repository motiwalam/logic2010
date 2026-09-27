package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class AuthURL {
   private URL url = null;

   public AuthURL(String spec) throws MalformedURLException {
      if (spec == null) {
         throw new MalformedURLException("null URL specification");
      } else {
         String[] parsed = this.parse(spec, true);
         this.url = new URL(parsed[0] + parsed[3]);
      }
   }

   String[] parse(String spec, boolean parseUser) {
      String[] parsed = new String[]{"", null, null, ""};
      int index = spec.indexOf("://");
      if (index != -1) {
         parsed[0] = spec.substring(0, index + 3);
         spec = spec.substring(index + 3);
      }

      if (parseUser) {
         index = spec.indexOf(47);
         if (index != -1) {
            parsed[3] = spec.substring(index);
            spec = spec.substring(0, index);
         }

         index = spec.indexOf(64);
         if (index != -1) {
            parsed[1] = spec.substring(0, index);
            spec = spec.substring(index + 1);
            index = parsed[1].indexOf(58);
            if (index != -1) {
               parsed[2] = parsed[1].substring(index + 1);
               parsed[1] = parsed[1].substring(index);
            }
         }
      }

      parsed[3] = spec + parsed[3];
      return parsed;
   }

   public static AuthURL makeURL(String spec) {
      try {
         return new AuthURL(spec);
      } catch (MalformedURLException var2) {
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

   public URL getURL() {
      return this.url;
   }

   @Override
   public String toString() {
      return this.url.toString();
   }
}
