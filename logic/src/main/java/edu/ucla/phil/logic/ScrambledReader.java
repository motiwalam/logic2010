package edu.ucla.phil.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

class ScrambledReader extends BufferedReader {
   String key;

   ScrambledReader(Reader reader, String s) {
      super(reader);
      this.key = s;
   }

   @Override
   public String readLine() throws IOException {
      return Scrambler.unscramble(super.readLine(), this.key);
   }
}
