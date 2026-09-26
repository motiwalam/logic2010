package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class C_m_ extends Thread {
   int f1264;
   ServerSocket f1265;
   InetAddress f1266;
   String f1267;

   public C_m_(int i, String s) {
      this.f1264 = i;
      this.f1267 = s;
      this.f1265 = null;

      try {
         this.f1266 = InetAddress.getLocalHost();
         C_k_C.m1905("TCPSolo: got local host @ " + this.f1266);
      } catch (UnknownHostException unknownhostexception) {
         this.f1266 = null;
         C_k_C.m1905("TCPSolo: failed to get local host");
         C_k_C.m1906(unknownhostexception);
      }
   }

   public boolean m1933() {
      if (this.f1266 == null) {
         return false;
      } else {
         for (; this.f1264 < 65536; this.f1264++) {
            try {
               this.f1265 = new ServerSocket(this.f1264);
               C_k_C.m1905("TCPSolo: opened server on port " + this.f1264);
               break;
            } catch (IOException ioexception) {
               int i = this.m1936();
               if (i == 1) {
                  C_k_C.m1905("TCPSolo: found Logic Program on port " + this.f1264);
                  C_k_C.m1906(ioexception);
                  return false;
               }

               if (i == 0) {
                  C_k_C.m1905("TCPSolo: another program is using port " + this.f1264);
                  C_k_C.m1906(ioexception);
               } else {
                  C_k_C.m1905("TCPSolo: unable to open server on port " + this.f1264);
                  C_k_C.m1906(ioexception);
               }
            }
         }

         if (this.f1264 >= 65536) {
            C_k_C.m1905("TCPSolo: could not open server on any port");
            return false;
         } else {
            this.start();
            return true;
         }
      }
   }

   public boolean m1934(int i) {
      if (this.f1266 == null) {
         return false;
      } else {
         long j = System.currentTimeMillis() + i;
         boolean flag = true;

         while (System.currentTimeMillis() < j && (flag = this.m1936() == 1)) {
         }

         return flag;
      }
   }

   public Integer m1935() {
      return this.f1266 == null ? null : new Integer(this.f1264);
   }

   int m1936() {
      try {
         Socket socket = new Socket(this.f1266, this.f1264);
         socket.setSoTimeout(50);
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String s = bufferedreader.readLine();
         socket.close();
         return this.f1267.equals(s) ? 1 : 0;
      } catch (IOException ioexception) {
         return -1;
      }
   }

   public void m1937() {
      try {
         if (this.f1265 != null) {
            this.f1265.close();
            this.f1265 = null;
            C_k_C.m1905("TCPSolo: closed server on port " + this.f1264);
         }
      } catch (IOException ioexception) {
         C_k_C.m1905("TCPSolo: failed to close server on port " + this.f1264);
         C_k_C.m1906(ioexception);
      }
   }

   @Override
   public void run() {
      if (this.f1265 != null) {
         while (true) {
            try {
               Socket socket = this.f1265.accept();
               BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
               bufferedwriter.write(this.f1267, 0, this.f1267.length());
               bufferedwriter.newLine();
               bufferedwriter.flush();
               socket.close();
            } catch (IOException ioexception) {
               return;
            }
         }
      }
   }
}
