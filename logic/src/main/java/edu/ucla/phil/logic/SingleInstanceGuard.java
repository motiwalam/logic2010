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

public class SingleInstanceGuard extends Thread {
   int port;
   ServerSocket serverSocket;
   InetAddress localHost;
   String signature;

   public SingleInstanceGuard(int i, String s) {
      this.port = i;
      this.signature = s;
      this.serverSocket = null;

      try {
         this.localHost = InetAddress.getLocalHost();
         DiagnosticsLog.log("TCPSolo: got local host @ " + this.localHost);
      } catch (UnknownHostException unknownhostexception) {
         this.localHost = null;
         DiagnosticsLog.log("TCPSolo: failed to get local host");
         DiagnosticsLog.logThrowable(unknownhostexception);
      }
   }

   public boolean startServer() {
      if (this.localHost == null) {
         return false;
      } else {
         for (; this.port < 65536; this.port++) {
            try {
               this.serverSocket = new ServerSocket(this.port);
               DiagnosticsLog.log("TCPSolo: opened server on port " + this.port);
               break;
            } catch (IOException ioexception) {
               int i = this.probePort();
               if (i == 1) {
                  DiagnosticsLog.log("TCPSolo: found Logic Program on port " + this.port);
                  DiagnosticsLog.logThrowable(ioexception);
                  return false;
               }

               if (i == 0) {
                  DiagnosticsLog.log("TCPSolo: another program is using port " + this.port);
                  DiagnosticsLog.logThrowable(ioexception);
               } else {
                  DiagnosticsLog.log("TCPSolo: unable to open server on port " + this.port);
                  DiagnosticsLog.logThrowable(ioexception);
               }
            }
         }

         if (this.port >= 65536) {
            DiagnosticsLog.log("TCPSolo: could not open server on any port");
            return false;
         } else {
            this.start();
            return true;
         }
      }
   }

   public boolean waitWhileOtherRunning(int i) {
      if (this.localHost == null) {
         return false;
      } else {
         long j = System.currentTimeMillis() + i;
         boolean flag = true;

         while (System.currentTimeMillis() < j && (flag = this.probePort() == 1)) {
         }

         return flag;
      }
   }

   public Integer getPort() {
      return this.localHost == null ? null : new Integer(this.port);
   }

   int probePort() {
      try {
         Socket socket = new Socket(this.localHost, this.port);
         socket.setSoTimeout(50);
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String s = bufferedreader.readLine();
         socket.close();
         return this.signature.equals(s) ? 1 : 0;
      } catch (IOException ioexception) {
         return -1;
      }
   }

   public void stopServer() {
      try {
         if (this.serverSocket != null) {
            this.serverSocket.close();
            this.serverSocket = null;
            DiagnosticsLog.log("TCPSolo: closed server on port " + this.port);
         }
      } catch (IOException ioexception) {
         DiagnosticsLog.log("TCPSolo: failed to close server on port " + this.port);
         DiagnosticsLog.logThrowable(ioexception);
      }
   }

   @Override
   public void run() {
      if (this.serverSocket != null) {
         while (true) {
            try {
               Socket socket = this.serverSocket.accept();
               BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
               bufferedwriter.write(this.signature, 0, this.signature.length());
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
