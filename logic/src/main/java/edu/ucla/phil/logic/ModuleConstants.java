package edu.ucla.phil.logic;

interface ModuleConstants extends LogicConstants {
   int derModule = 0;
   int invModule = 1;
   int parModule = 2;
   int recModule = 3;
   int symModule = 4;
   int truModule = 5;
   String[] moduleAbbrs = new String[]{"Deriv", "Inval", "Pars", "Recog", "Symb", "TruTb"};
   String[] moduleNames = new String[]{"Derivation", "Invalidity", "Parsing", "Recognizing Rules", "Symbolization", "Truth Tables"};
   String[] moduleWorks = new String[]{"derwork.txt", "invwork.txt", "parwork.txt", "recwork.txt", "symwork.txt", "truwork.txt"};
   Class[] moduleClasses = new Class[]{
      LPDerivation.class, LPInvalidation.class, LPParsing.class, LPRecognition.class, LPSymbolizer.class, LPTruthAnalysis.class
   };
}
