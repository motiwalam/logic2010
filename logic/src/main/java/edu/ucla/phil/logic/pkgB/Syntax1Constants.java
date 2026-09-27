package edu.ucla.phil.logic.pkgB;

public interface Syntax1Constants {
   int EOF = 0;
   int EOL = 4;
   int VAR = 5;
   int PRED = 6;
   int SEN = 7;
   int OP = 8;
   int UNK = 9;
   int DEFAULT = 0;
   String[] tokenImage = new String[]{
      "<EOF>",
      "\" \"",
      "\"\\r\"",
      "\"\\t\"",
      "\"\\n\"",
      "<VAR>",
      "<PRED>",
      "<SEN>",
      "<OP>",
      "<UNK>",
      "\"<->\"",
      "\"->\"",
      "\"&\"",
      "\"|\"",
      "\"=\"",
      "\"<>\"",
      "\"[m]\"",
      "\"~\"",
      "\"@\"",
      "\"!\"",
      "\"(\"",
      "\")\"",
      "\"%\""
   };
}
