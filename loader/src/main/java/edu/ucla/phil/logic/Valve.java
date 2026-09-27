package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class Valve {
   InputStream in = null;
   OutputStream out = null;
   ResultThread thread = null;
   String title;
   String message;
   Notifier notifier = null;
   long waitForShow = 1L;
   long waitForDeath = 15000L;
   boolean streamsClosed;

   Valve(String title, String message, long waitForShow) {
      this.title = title;
      this.message = message;
      this.waitForShow = waitForShow;
   }

   Valve(String title, String message) {
      this(title, message, 1L);
   }

   void start(ResultThread thread) {
      this.thread = thread;
      if (Thread.currentThread() == thread) {
         thread.safeSpin();
      } else {
         this.streamsClosed = false;
         this.notifier = new Notifier(this.title, this.message, new String[]{"Abort"}, 0);
         thread.start();
         if (this.waitForShow == 1L) {
            this.waitForShow = 10000L;
         }

         try {
            thread.join(this.waitForShow);
         } catch (InterruptedException var4) {
         }

         if (thread.isAlive()) {
            this.notifier.show(20, 10);
            this.closeStreams();

            try {
               thread.join(this.waitForDeath);
            } catch (InterruptedException var3) {
            }

            if (thread.isAlive()) {
               thread.stop();
            }
         } else {
            this.notifier.dispose();
            this.closeStreams();
         }
      }
   }

   Object getResult() {
      return this.thread.getResult();
   }

   int getButtonIndex() {
      return this.notifier == null ? -1 : this.notifier.buttonIndex;
   }

   void dispose() {
      if (this.notifier != null) {
         this.notifier.dispose();
      }
   }

   synchronized void setMessage(String message) {
      if (this.notifier != null) {
         this.notifier.setMessage(message);
      }
   }

   synchronized InputStream getInput() {
      return this.in;
   }

   synchronized void setInput(InputStream in) throws IOException {
      if (this.streamsClosed) {
         throw new IOException("input stream set too late");
      } else {
         this.in = in;
      }
   }

   synchronized OutputStream getOutput() {
      return this.out;
   }

   synchronized void setOutput(OutputStream out) throws IOException {
      if (this.streamsClosed) {
         throw new IOException("output stream set too late");
      } else {
         this.out = out;
      }
   }

   synchronized void closeStreams() {
      if (this.in != null) {
         try {
            this.in.close();
         } catch (IOException var3) {
         }
      }

      this.in = null;
      if (this.out != null) {
         try {
            this.out.close();
         } catch (IOException var2) {
         }
      }

      this.out = null;
      this.streamsClosed = true;
   }
}
