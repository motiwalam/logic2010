package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class NetworkTask {
   InputStream inputStream = null;
   OutputStream outputStream = null;
   NetworkWorker worker = null;
   String title;
   String message;
   ProgressDialog progressDialog = null;
   long initialWait = 1L;
   long abortWait = 15000L;
   boolean closed;

   NetworkTask(String s, String s1, long i) {
      this.title = s;
      this.message = s1;
      this.initialWait = i;
   }

   NetworkTask(String s, String s1) {
      this(s, s1, 1L);
   }

   void execute(NetworkWorker networkworker) {
      this.worker = networkworker;
      if (Thread.currentThread() == networkworker) {
         networkworker.runWork();
      } else {
         this.closed = false;
         this.progressDialog = new ProgressDialog(this.title, this.message, new String[]{"Abort"}, 0);
         networkworker.start();
         if (this.initialWait == 1L) {
            this.initialWait = 10000L;
         }

         try {
            networkworker.join(this.initialWait);
         } catch (InterruptedException interruptedexception1) {
         }

         if (networkworker.isAlive()) {
            this.progressDialog.showWithMargins(20, 10);
            this.closeStreams();

            try {
               networkworker.join(this.abortWait);
            } catch (InterruptedException interruptedexception) {
            }

            if (networkworker.isAlive()) {
               networkworker.stop();
            }
         } else {
            this.progressDialog.dispose();
            this.closeStreams();
         }
      }
   }

   Object getResult() {
      return this.worker.getResult();
   }

   int getPressedButton() {
      return this.progressDialog == null ? -1 : this.progressDialog.selectedButton;
   }

   void dismissDialog() {
      if (this.progressDialog != null) {
         this.progressDialog.dispose();
      }
   }

   synchronized void setMessage(String s) {
      if (this.progressDialog != null) {
         this.progressDialog.setMessage(s);
      }
   }

   synchronized InputStream getInputStream() {
      return this.inputStream;
   }

   synchronized void setInputStream(InputStream inputstream) throws IOException {
      if (this.closed) {
         throw new IOException("input stream set too late");
      } else {
         this.inputStream = inputstream;
      }
   }

   synchronized OutputStream getOutputStream() {
      return this.outputStream;
   }

   synchronized void setOutputStream(OutputStream outputstream) throws IOException {
      if (this.closed) {
         throw new IOException("output stream set too late");
      } else {
         this.outputStream = outputstream;
      }
   }

   synchronized void closeStreams() {
      if (this.inputStream != null) {
         try {
            this.inputStream.close();
         } catch (IOException ioexception1) {
         }
      }

      this.inputStream = null;
      if (this.outputStream != null) {
         try {
            this.outputStream.close();
         } catch (IOException ioexception) {
         }
      }

      this.outputStream = null;
      this.closed = true;
   }
}
