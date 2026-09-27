package edu.ucla.phil.logic;

import java.io.Reader;

class PlainRecordReader extends ScrambledReader {
   PlainRecordReader(Reader reader) {
      super(reader, null);
   }
}
