package edu.ucla.phil.logic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

class C_V extends PrintStream {
   PrintStream f816;
   OutputStream out;

   C_V(OutputStream outputstream, boolean flag) {
      super(outputstream, flag);
      this.out = outputstream;
      this.f816 = System.err;
      System.setErr(this);
   }

   @Override
   public void println(String s) {
      if (s == null || s.length() == 0 || !s.substring(0, 1).equals("\t")) {
         this.f816.println(s);
      }

      super.println(s);
   }

   @Override
   public void println(Object object) {
      this.f816.println(object);
      super.println(object);
      super.println(LogicProgram.m1079());
   }

   @Override
   public void close() {
      System.setErr(this.f816);
      this.f816 = null;

      try {
         this.out.close();
      } catch (IOException ioexception) {
      }
   }
}
