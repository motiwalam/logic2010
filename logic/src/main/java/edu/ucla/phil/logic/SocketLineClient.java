package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

class SocketLineClient {
   SocketLineReader reader;
   PrintWriter writer;
   PrintWriter transcript;
   InetAddress localAddress;

   public SocketLineClient() {
      this(null);
   }

   public SocketLineClient(PrintWriter printwriter) {
      this.transcript = printwriter;
      this.localAddress = getLocalHost();
      this.reader = null;
      this.writer = null;
   }

   static InetAddress getLocalHost() {
      try {
         return InetAddress.getLocalHost();
      } catch (UnknownHostException unknownhostexception) {
         return null;
      }
   }

   static String getLocalIpString(String s) {
      InetAddress inetaddress = getLocalHost();
      if (inetaddress == null) {
         return null;
      } else {
         byte[] abyte = inetaddress.getAddress();
         return abyte != null && abyte.length == 4 ? joinInts(toUnsignedInts(abyte), s) : null;
      }
   }

   static String getLocalIpWithPort(String s, int i) {
      String s1 = getLocalIpString(s);
      return s1 == null ? null : s1 + s + joinInts(new int[]{i / 256 & 0xFF, i & 0xFF}, s);
   }

   static int[] toUnsignedInts(byte[] abyte) {
      int i = abyte.length;
      int[] aint = new int[i];

      for (int j = 0; j < i; j++) {
         aint[j] = abyte[j] & 255;
      }

      return aint;
   }

   static byte[] toBytes(int[] aint) {
      int i = aint.length;
      byte[] abyte = new byte[i];

      for (int j = 0; j < i; j++) {
         abyte[j] = (byte)aint[j];
      }

      return abyte;
   }

   static String joinInts(int[] aint, String s) {
      int i = aint.length;
      String s1 = "";

      for (int j = 0; j < i; j++) {
         s1 = s1 + (j == 0 ? "" : s) + aint[j];
      }

      return s1;
   }

   static int[] parseDottedAddress(String s) {
      ExpressionPath expressionpath = new ExpressionPath();
      int i = s.length();
      int j = 0;

      for (int k = 0; k < i; k++) {
         char c0 = s.charAt(k);
         if (!Character.isDigit(c0)) {
            if (k > j) {
               addOctet(expressionpath, s.substring(j, k));
            }

            j = k + 1;
         }
      }

      if (i > j) {
         addOctet(expressionpath, s.substring(j, i));
      }

      return expressionpath.toArray();
   }

   private static void addOctet(ExpressionPath expressionpath, String s) {
      int i = Integer.parseInt(s);
      if (i >= 0 && i <= 255) {
         expressionpath.push(i);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void attachSocket(Socket socket) {
      try {
         if (this.reader == null) {
            this.reader = new SocketLineReader(socket.getInputStream());
         }

         if (this.writer == null) {
            this.writer = new PrintWriter(socket.getOutputStream(), true);
         }
      } catch (IOException ioexception) {
         this.closeStreams();
      }
   }

   public final void closeStreams() {
      if (this.reader != null) {
         try {
            this.reader.close();
         } catch (IOException ioexception) {
         }
      }

      this.reader = null;
      if (this.writer != null) {
         this.writer.close();
      }

      this.writer = null;
   }

   public final void sendLine(String s) {
      this.writer.print(s + "\r\n");
      this.writer.flush();
      if (this.transcript != null) {
         this.transcript.println(s);
      }
   }

   public final String[] readDotTerminatedLines() throws IOException {
      Vector vector = new Vector();

      String s;
      while ((s = this.reader.readLine()) != null) {
         if (this.transcript != null) {
            this.transcript.println(s);
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

   public String readStatusLine() throws IOException {
      String s;
      while ((s = this.reader.readLine()) != null) {
         if (this.transcript != null) {
            this.transcript.println(s);
         }

         if (ProtocolReply.isStatusLine(s)) {
            break;
         }
      }

      return s;
   }
}
