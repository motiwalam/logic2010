package edu.ucla.phil.logic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

class ScramblingWriter extends BufferedWriter {
   String key;
   String pendingLine;

   ScramblingWriter(Writer writer, String s) {
      super(writer);
      this.key = s;
      this.pendingLine = "";
   }

   @Override
   public void write(int i) {
      this.pendingLine = this.pendingLine + (char)i;
   }

   @Override
   public void write(char[] achar, int i, int j) {
      this.pendingLine = this.pendingLine + new String(achar, i, j);
   }

   @Override
   public void write(String s, int i, int j) {
      this.pendingLine = this.pendingLine + s.substring(i, i + j);
   }

   void scramblePendingLine() {
      if (this.pendingLine.length() != 0) {
         this.pendingLine = Scrambler.scramble(this.pendingLine, this.key);
         this.write(this.pendingLine, 0, this.pendingLine.length());
         this.pendingLine = "";
      }
   }

   @Override
   public void newLine() throws IOException {
      this.scramblePendingLine();
      super.newLine();
   }

   @Override
   public void close() throws IOException {
      this.scramblePendingLine();
      super.close();
   }
}
