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

public class TCPSolo extends Thread {
   int port;
   ServerSocket server;
   InetAddress local;
   String message;

   public TCPSolo(int port, String message) {
      this.port = port;
      this.message = message;
      this.server = null;

      try {
         this.local = InetAddress.getLocalHost();
         LPDiagnostics.printDiagnostic("TCPSolo: got local host @ " + this.local);
      } catch (UnknownHostException var4) {
         this.local = null;
         LPDiagnostics.printDiagnostic("TCPSolo: failed to get local host");
         LPDiagnostics.printThrowableDiagnostic(var4);
      }
   }

   public boolean alone() {
      if (this.local == null) {
         return false;
      } else {
         for (; this.port < 65536; this.port++) {
            try {
               this.server = new ServerSocket(this.port);
               LPDiagnostics.printDiagnostic("TCPSolo: opened server on port " + this.port);
               break;
            } catch (IOException var3) {
               int test = this.testPort();
               if (test == 1) {
                  LPDiagnostics.printDiagnostic("TCPSolo: found Logic Program on port " + this.port);
                  LPDiagnostics.printThrowableDiagnostic(var3);
                  return false;
               }

               if (test == 0) {
                  LPDiagnostics.printDiagnostic("TCPSolo: another program is using port " + this.port);
                  LPDiagnostics.printThrowableDiagnostic(var3);
               } else {
                  LPDiagnostics.printDiagnostic("TCPSolo: unable to open server on port " + this.port);
                  LPDiagnostics.printThrowableDiagnostic(var3);
               }
            }
         }

         if (this.port >= 65536) {
            LPDiagnostics.printDiagnostic("TCPSolo: could not open server on any port");
            return false;
         } else {
            this.start();
            return true;
         }
      }
   }

   public boolean running(int waitMillis) {
      if (this.local == null) {
         return false;
      } else {
         long limit = System.currentTimeMillis() + waitMillis;
         boolean running = true;

         while (System.currentTimeMillis() < limit && (running = this.testPort() == 1)) {
         }

         return running;
      }
   }

   public Integer getPort() {
      return this.local == null ? null : new Integer(this.port);
   }

   int testPort() {
      try {
         Socket socket = new Socket(this.local, this.port);
         socket.setSoTimeout(50);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String check = in.readLine();
         socket.close();
         return this.message.equals(check) ? 1 : 0;
      } catch (IOException var4) {
         return -1;
      }
   }

   public void close() {
      try {
         if (this.server != null) {
            this.server.close();
            this.server = null;
            LPDiagnostics.printDiagnostic("TCPSolo: closed server on port " + this.port);
         }
      } catch (IOException var2) {
         LPDiagnostics.printDiagnostic("TCPSolo: failed to close server on port " + this.port);
         LPDiagnostics.printThrowableDiagnostic(var2);
      }
   }

   @Override
   public void run() {
      if (this.server != null) {
         while (true) {
            try {
               Socket socket = this.server.accept();
               BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
               out.write(this.message, 0, this.message.length());
               out.newLine();
               out.flush();
               socket.close();
            } catch (IOException var3) {
               return;
            }
         }
      }
   }
}
