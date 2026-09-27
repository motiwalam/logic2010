package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

class ErrorLogStream extends PrintStream {
   PrintStream originalErr;
   OutputStream out;

   ErrorLogStream(OutputStream outputstream, boolean flag) {
      super(outputstream, flag);
      this.out = outputstream;
      this.originalErr = System.err;
      System.setErr(this);
   }

   @Override
   public void println(String s) {
      if (s == null || s.length() == 0 || !s.substring(0, 1).equals("\t")) {
         this.originalErr.println(s);
      }

      super.println(s);
   }

   @Override
   public void println(Object object) {
      this.originalErr.println(object);
      super.println(object);
      super.println(LogicProgram.utcTimestamp());
   }

   @Override
   public void close() {
      System.setErr(this.originalErr);
      this.originalErr = null;

      try {
         this.out.close();
      } catch (IOException ioexception) {
      }
   }
}
