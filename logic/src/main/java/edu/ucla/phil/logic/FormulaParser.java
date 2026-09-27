package edu.ucla.phil.logic;

import edu.ucla.phil.logic.syntax1.Syntax1Parser;
import edu.ucla.phil.logic.syntax2.Syntax2Parser;
import java.io.Reader;

public class FormulaParser {
   static int syntaxCount = 2;
   static int syntax = 2;

   static void setSyntax(int i) {
      if (i >= 1 && i <= syntaxCount) {
         syntax = i;
      }
   }

   static int getSyntax() {
      return syntax;
   }

   static Expression parse() throws FormulaParseException {
      switch (syntax) {
         case 1:
            return Syntax1Parser.parse();
         case 2:
            return Syntax2Parser.parse();
         default:
            return null;
      }
   }

   static void reinit(Reader reader) {
      switch (syntax) {
         case 1:
            Syntax1Parser.reinit(reader);
            break;
         case 2:
            Syntax2Parser.reinit(reader);
      }
   }

   static void disableTracing() {
      Syntax1Parser.disableTracing();
      Syntax2Parser.disableTracing();
   }
}
