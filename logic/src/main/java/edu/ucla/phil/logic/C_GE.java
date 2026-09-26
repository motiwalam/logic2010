package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

class C_GE {
   C_w_ f363;
   PrintWriter f364;
   PrintWriter f365;
   InetAddress f366;

   public C_GE() {
      this(null);
   }

   public C_GE(PrintWriter printwriter) {
      this.f365 = printwriter;
      this.f366 = m643();
      this.f363 = null;
      this.f364 = null;
   }

   static InetAddress m643() {
      try {
         return InetAddress.getLocalHost();
      } catch (UnknownHostException unknownhostexception) {
         return null;
      }
   }

   static String m644(String s) {
      InetAddress inetaddress = m643();
      if (inetaddress == null) {
         return null;
      } else {
         byte[] abyte = inetaddress.getAddress();
         return abyte != null && abyte.length == 4 ? m648(m646(abyte), s) : null;
      }
   }

   static String m645(String s, int i) {
      String s1 = m644(s);
      return s1 == null ? null : s1 + s + m648(new int[]{i / 256 & 0xFF, i & 0xFF}, s);
   }

   static int[] m646(byte[] abyte) {
      int i = abyte.length;
      int[] aint = new int[i];

      for (int j = 0; j < i; j++) {
         aint[j] = abyte[j] & 255;
      }

      return aint;
   }

   static byte[] m647(int[] aint) {
      int i = aint.length;
      byte[] abyte = new byte[i];

      for (int j = 0; j < i; j++) {
         abyte[j] = (byte)aint[j];
      }

      return abyte;
   }

   static String m648(int[] aint, String s) {
      int i = aint.length;
      String s1 = "";

      for (int j = 0; j < i; j++) {
         s1 = s1 + (j == 0 ? "" : s) + aint[j];
      }

      return s1;
   }

   static int[] m649(String s) {
      ExpressionPath expressionpath = new ExpressionPath();
      int i = s.length();
      int j = 0;

      for (int k = 0; k < i; k++) {
         char c0 = s.charAt(k);
         if (!Character.isDigit(c0)) {
            if (k > j) {
               m650(expressionpath, s.substring(j, k));
            }

            j = k + 1;
         }
      }

      if (i > j) {
         m650(expressionpath, s.substring(j, i));
      }

      return expressionpath.m1752();
   }

   private static void m650(ExpressionPath expressionpath, String s) {
      int i = Integer.parseInt(s);
      if (i >= 0 && i <= 255) {
         expressionpath.m1749(i);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void m651(Socket socket) {
      try {
         if (this.f363 == null) {
            this.f363 = new C_w_(socket.getInputStream());
         }

         if (this.f364 == null) {
            this.f364 = new PrintWriter(socket.getOutputStream(), true);
         }
      } catch (IOException ioexception) {
         this.m652();
      }
   }

   public final void m652() {
      if (this.f363 != null) {
         try {
            this.f363.m2132();
         } catch (IOException ioexception) {
         }
      }

      this.f363 = null;
      if (this.f364 != null) {
         this.f364.close();
      }

      this.f364 = null;
   }

   public final void m653(String s) {
      this.f364.print(s + "\r\n");
      this.f364.flush();
      if (this.f365 != null) {
         this.f365.println(s);
      }
   }

   public final String[] m654() throws IOException {
      Vector vector = new Vector();

      String s;
      while ((s = this.f363.m2131()) != null) {
         if (this.f365 != null) {
            this.f365.println(s);
         }

         int i = s.length();
         if (i > 0 && s.charAt(0) == '.') {
            if (i == 1) {
               break;
            }

            s = s.substring(1);
         }

         vector.addElement(s);
      }

      String[] astring = new String[vector.size()];
      vector.copyInto(astring);
      return astring;
   }

   public String m655() throws IOException {
      String s;
      while ((s = this.f363.m2131()) != null) {
         if (this.f365 != null) {
            this.f365.println(s);
         }

         if (C_YB.m1538(s)) {
            break;
         }
      }

      return s;
   }
}
