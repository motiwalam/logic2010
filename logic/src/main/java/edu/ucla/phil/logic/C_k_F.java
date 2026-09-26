package edu.ucla.phil.logic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

class C_k_F extends BufferedWriter {
   String f1221;
   String f1222;

   C_k_F(Writer writer, String s) {
      super(writer);
      this.f1221 = s;
      this.f1222 = "";
   }

   @Override
   public void write(int i) {
      this.f1222 = this.f1222 + (char)i;
   }

   @Override
   public void write(char[] achar, int i, int j) {
      this.f1222 = this.f1222 + new String(achar, i, j);
   }

   @Override
   public void write(String s, int i, int j) {
      this.f1222 = this.f1222 + s.substring(i, i + j);
   }

   void m1914() {
      if (this.f1222.length() != 0) {
         this.f1222 = Scrambler.scramble(this.f1222, this.f1221);
         this.write(this.f1222, 0, this.f1222.length());
         this.f1222 = "";
      }
   }

   @Override
   public void newLine() throws IOException {
      this.m1914();
      super.newLine();
   }

   @Override
   public void close() throws IOException {
      this.m1914();
      super.close();
   }
}
