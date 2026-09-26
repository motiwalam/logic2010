package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

class C_XB extends BufferedReader {
   String f873;

   C_XB(Reader reader, String s) {
      super(reader);
      this.f873 = s;
   }

   @Override
   public String readLine() throws IOException {
      return C_z_D.m2220(super.readLine(), this.f873);
   }
}
