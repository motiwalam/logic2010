package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class MailSocketClient extends SocketLineClient {
   Socket popSocket = null;
   Socket smtpSocket = null;

   public MailSocketClient() {
      this(null);
   }

   public MailSocketClient(PrintWriter printwriter) {
      super(printwriter);
   }

   boolean connectSmtp(String s) {
      this.closePop();
      if (this.smtpSocket == null) {
         try {
            this.attachSocket(this.smtpSocket = new Socket(s, 25));
         } catch (IOException ioexception) {
            this.smtpSocket = null;
            return false;
         }
      }

      return true;
   }

   boolean connectPop(String s) {
      this.closeSmtp();
      if (this.popSocket == null) {
         try {
            this.attachSocket(this.popSocket = new Socket(s, 110));
         } catch (IOException ioexception) {
            this.popSocket = null;
            return false;
         }
      }

      return true;
   }

   public void closeSmtp() {
      if (this.smtpSocket != null) {
         try {
            this.smtpSocket.close();
         } catch (IOException ioexception) {
         }

         this.smtpSocket = null;
      }
   }

   public void closePop() {
      if (this.popSocket != null) {
         try {
            this.popSocket.close();
         } catch (IOException ioexception) {
         }

         this.popSocket = null;
      }
   }

   public void closeAll() {
      this.closeSmtp();
      this.closePop();
   }
}
